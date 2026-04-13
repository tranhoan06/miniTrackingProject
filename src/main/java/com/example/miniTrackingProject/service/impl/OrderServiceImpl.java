package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.*;
import com.example.miniTrackingProject.dto.request.OrderItemRequest;
import com.example.miniTrackingProject.dto.request.OrderRequest;
import com.example.miniTrackingProject.dto.request.PreviewOrderRequest;
import com.example.miniTrackingProject.dto.response.AddressSnapshotResponse;
import com.example.miniTrackingProject.dto.response.OrderResponse;
import com.example.miniTrackingProject.dto.response.PreviewOrderResponse;
import com.example.miniTrackingProject.entity.*;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.AddressMapper;
import com.example.miniTrackingProject.repository.*;
import com.example.miniTrackingProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final VoucherRepository voucherRepository;
    private final AddressRepository addressRepository;
    private final SecurityHelper securityHelper;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressMapper addressMapper;
    private final ObjectMapper objectMapper;
    private final CartItemRepository cartItemRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    @Override
    public PreviewOrderResponse previewOrder(PreviewOrderRequest request) {
        LocalDateTime today = LocalDateTime.now();
        VouchersEntity voucher = voucherRepository.findVoucher(request.getVoucherId(), today)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.VOUCHER_NOT_FOUND));

        validateVoucher(voucher);

        List<CartItemsEntity> orderItems = request.getProductId().stream()
                .map(cartItemRepository::findByProductId)
                .flatMap(List::stream)
                .toList();

        if (orderItems.isEmpty()) {
            throw new JavaBuilderException(ErrorCode.NOT_FOUND);
        }

        BigDecimal subtotal = calculateSubtotal(orderItems);

        if (subtotal.compareTo(voucher.getMinOrderAmount()) < 0) {
            throw new RuntimeException("Đơn hàng chưa đạt giá trị tối thiểu");
        }

        BigDecimal discount = calculateDiscount(subtotal, voucher);

        return new PreviewOrderResponse(subtotal, discount, subtotal.subtract(discount));
    }

    @Override
    @Transactional
    public String createOrder(OrderRequest request) {

        UserEntity user = securityHelper.getCurrentUser();
        LocalDateTime today = LocalDateTime.now();
        AddresesEntity address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.ADDRESS_NOT_FOUND));

        VouchersEntity voucher = voucherRepository.findVoucher(request.getVoucherId(), today)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.VOUCHER_NOT_FOUND));

        validateVoucher(voucher);

        String addressSnapshot = objectMapper.writeValueAsString(
                addressMapper.toSnapshot(address)
        );

        Map<Long, List<OrderItemsEntity>> itemsBySeller = new HashMap<>();
        Map<Long, BigDecimal> subtotalBySeller = new HashMap<>();

        BigDecimal totalProductAmount = BigDecimal.ZERO;

        // build items
        for (OrderItemRequest item : request.getItems()) {

            ProductsEntity product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));

            reserveInventory(product.getId(), item.getQuantity());

            OrderItemsEntity orderItem = buildOrderItem(product, item.getQuantity());

            Long sellerId = product.getSeller().getId();

            itemsBySeller.computeIfAbsent(sellerId, k -> new ArrayList<>()).add(orderItem);

            subtotalBySeller.merge(
                    sellerId,
                    orderItem.getTotalAmount(),
                    BigDecimal::add
            );

            totalProductAmount = totalProductAmount.add(orderItem.getTotalAmount());
        }

        BigDecimal totalDiscount = calculateDiscount(totalProductAmount, voucher);

        // create orders
        for (Long sellerId : itemsBySeller.keySet()) {

            BigDecimal sellerSubtotal = subtotalBySeller.get(sellerId);
            UserEntity seller = userRepository.findById(sellerId)
                    .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

            BigDecimal sellerDiscount = totalProductAmount.compareTo(BigDecimal.ZERO) == 0
                    ? BigDecimal.ZERO
                    : sellerSubtotal
                    .divide(totalProductAmount, 4, RoundingMode.HALF_UP)
                    .multiply(totalDiscount);

            BigDecimal shippingFee = BigDecimal.valueOf(30000);

            OrdersEntity order = new OrdersEntity();
            order.setBuyer(user);
            order.setSeller(seller);
            order.setTotalProductAmount(sellerSubtotal);
            order.setVoucherDiscount(sellerDiscount);
            order.setShippingFee(shippingFee);
            order.setFinalAmount(sellerSubtotal.subtract(sellerDiscount).add(shippingFee));
            order.setPaymentMethod(request.getPaymentMethod());
            order.setOrderStatus(OrderStatus.PENDING);
            order.setPaymentStatus(PaymentStatus.PENDING);
            order.setAddress(address);
            order.setVoucher(voucher);
            order.setShippingAddressSnapshot(addressSnapshot);

            orderRepository.save(order);

            for (OrderItemsEntity item : itemsBySeller.get(sellerId)) {
                item.setOrder(order);
            }

            orderItemRepository.saveAll(itemsBySeller.get(sellerId));
        }
        voucher.setUsedCount(voucher.getUsedCount() + 1);
        return "Create order success";
    }

    @Override
    public List<OrderResponse> getOrderBySeller(Long sellerId) {
        return List.of();
    }

    private OrderItemsEntity buildOrderItem(ProductsEntity product, Integer quantity) {

        BigDecimal total = product.getPrice()
                .multiply(BigDecimal.valueOf(quantity));

        OrderItemsEntity item = new OrderItemsEntity();
        item.setProduct(product);
        item.setSeller(product.getSeller());
        item.setQuantity(quantity);
        item.setPrice(product.getPrice());
        item.setTotalAmount(total);
        item.setProductNameSnapshot(product.getProductName());

        if (!product.getImages().isEmpty()) {
            item.setProductImageSnapshot(product.getImages().get(0).getImageUrl());
        }

        return item;
    }

    private void validateVoucher(VouchersEntity voucher) {
        LocalDateTime now = LocalDateTime.now();

        if (voucher.getStartDate().isAfter(now)) {
            throw new JavaBuilderException(ErrorCode.VOUCHER_HAS_NOT_STARTED_YET);
        }

        if (voucher.getEndDate().isBefore(now)) {
            throw new JavaBuilderException(ErrorCode.VOUCHER_EXPIRES);
        }

        if(voucher.getUsedCount() >= voucher.getQuantity()) {
            throw new JavaBuilderException(ErrorCode.VOUCHER_EXPIRES);
        }
    }

    private BigDecimal calculateDiscount(BigDecimal subtotal, VouchersEntity voucher) {
        if (voucher == null) return BigDecimal.ZERO;

        BigDecimal discount = BigDecimal.ZERO;

        if (voucher.getDiscountType() == DiscountType.PERCENT) {
            discount = subtotal
                    .multiply(voucher.getDiscountValue())
                    .divide(BigDecimal.valueOf(100));

            if (voucher.getMaxDiscount() != null &&
                    discount.compareTo(voucher.getMaxDiscount()) > 0) {
                discount = voucher.getMaxDiscount();
            }

        } else if (voucher.getDiscountType() == DiscountType.FIXED) {
            discount = voucher.getDiscountValue();

            if (discount.compareTo(subtotal) > 0) {
                discount = subtotal;
            }
        }

        return discount;
    }

    private BigDecimal calculateSubtotal(List<CartItemsEntity> items) {
        return items.stream()
                .map(i -> i.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void reserveInventory(Long productId, Integer quantity) {

        InventoryEntity inventory = inventoryRepository
                .findByProductIdForUpdate(productId)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.NOT_FOUND));

        long available = inventory.getQuantityInStock() - inventory.getReservedQuantity();

        if (available < quantity) {
            throw new JavaBuilderException(ErrorCode.PRODUCT_NOT_ENOUGH_STOCK);
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + quantity
        );

        inventory.setUpdatedAt(LocalDateTime.now());

        inventoryRepository.save(inventory);
    }
}
