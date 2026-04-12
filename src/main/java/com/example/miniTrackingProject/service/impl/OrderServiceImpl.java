package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.*;
import com.example.miniTrackingProject.dto.request.OrderItemRequest;
import com.example.miniTrackingProject.dto.request.OrderRequest;
import com.example.miniTrackingProject.dto.request.PreviewOrderRequest;
import com.example.miniTrackingProject.dto.response.AddressResponse;
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

    @Override
    public PreviewOrderResponse previewOrder(PreviewOrderRequest request) {

        VouchersEntity voucher = voucherRepository.findVoucher(request.getVoucherId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.VOUCHER_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();

        if (voucher.getStartDate().isAfter(now)) {
            throw new JavaBuilderException(ErrorCode.VOUCHER_HAS_NOT_STARTED_YET);
        }

        if (voucher.getEndDate().isBefore(now)) {
            throw new JavaBuilderException(ErrorCode.VOUCHER_EXPIRES);
        }

        BigDecimal subtotal = request.getSubtotal();
        // DECIMAL
        // cộng: add(), trừ: subtract(), nhân: multiply(), chia: divide()
        if (subtotal.compareTo(voucher.getMinOrderAmount()) < 0) {
            throw new RuntimeException("Đơn hàng chưa đạt giá trị tối thiểu để dùng voucher");
        }

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

        PreviewOrderResponse response = new PreviewOrderResponse();
        response.setSubtotal(subtotal);
        response.setDiscount(discount);
        response.setTotal(subtotal.subtract(discount));

        return response;
    }

    @Override
    @Transactional
    public String createOrder(OrderRequest request) {

        UserEntity user = securityHelper.getCurrentUser();

        AddresesEntity address = addressRepository.findById(request.getAddress())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.ADDRESS_NOT_FOUND));

        VouchersEntity voucher = voucherRepository.findVoucher(request.getVoucher())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.VOUCHER_NOT_FOUND));

        AddressSnapshotResponse snapshotDTO = addressMapper.toSnapshot(address);

        String addressSnapshot = objectMapper.writeValueAsString(snapshotDTO);


        Map<Long, List<OrderItemsEntity>> itemsBySeller = new HashMap<>();
        Map<Long, BigDecimal> subtotalBySeller = new HashMap<>();

        BigDecimal totalProductAmount = BigDecimal.ZERO;

        // 1️⃣ Chuẩn bị orderItems + group theo seller
        for (OrderItemRequest item : request.getItems()) {

            ProductsEntity product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            totalProductAmount = totalProductAmount.add(itemTotal);

            OrderItemsEntity orderItem = new OrderItemsEntity();
            orderItem.setProduct(product);
            orderItem.setSeller(product.getSeller());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setTotalAmount(itemTotal);

            orderItem.setProductNameSnapshot(product.getProductName());

            if (!product.getImages().isEmpty()) {
                orderItem.setProductImageSnapshot(
                        product.getImages().get(0).getImageUrl()
                );
            }

            Long sellerId = product.getSeller().getId();

            itemsBySeller.computeIfAbsent(sellerId, k -> new ArrayList<>()).add(orderItem);

            subtotalBySeller.put(
                    sellerId,
                    subtotalBySeller.getOrDefault(sellerId, BigDecimal.ZERO).add(itemTotal)
            );
        }

        // 2️⃣ Tính voucher tổng
        BigDecimal voucherDiscount = BigDecimal.ZERO;

        if (request.getVoucher() != null) {

            LocalDateTime now = LocalDateTime.now();

            if (voucher.getStartDate().isAfter(now)) {
                throw new JavaBuilderException(ErrorCode.VOUCHER_HAS_NOT_STARTED_YET);
            }

            if (voucher.getEndDate().isBefore(now)) {
                throw new JavaBuilderException(ErrorCode.VOUCHER_EXPIRES);
            }

            if (voucher.getDiscountType() == DiscountType.PERCENT) {

                voucherDiscount = totalProductAmount
                        .multiply(voucher.getDiscountValue())
                        .divide(BigDecimal.valueOf(100));

                if (voucher.getMaxDiscount() != null &&
                        voucherDiscount.compareTo(voucher.getMaxDiscount()) > 0) {
                    voucherDiscount = voucher.getMaxDiscount();
                }

            } else if (voucher.getDiscountType() == DiscountType.FIXED) {

                voucherDiscount = voucher.getDiscountValue();

                if (voucherDiscount.compareTo(totalProductAmount) > 0) {
                    voucherDiscount = totalProductAmount;
                }
            }
        }

        // 3️⃣ Tạo order cho từng seller
        // keyset: lấy toàn bộ key trong Map
        for (Long sellerId : itemsBySeller.keySet()) {

            List<OrderItemsEntity> sellerItems = itemsBySeller.get(sellerId);

            BigDecimal sellerSubtotal = subtotalBySeller.get(sellerId);

            // chia voucher theo tỷ lệ
            BigDecimal sellerDiscount = BigDecimal.ZERO;

            if (voucherDiscount.compareTo(BigDecimal.ZERO) > 0) {

                sellerDiscount = sellerSubtotal
                        .divide(totalProductAmount, 4, RoundingMode.HALF_UP)
                        .multiply(voucherDiscount);
            }

            BigDecimal shippingFee = BigDecimal.valueOf(30000);

            BigDecimal finalAmount = sellerSubtotal
                    .subtract(sellerDiscount)
                    .add(shippingFee);

            OrdersEntity order = new OrdersEntity();

            order.setBuyer(user);
            order.setTotalProductAmount(sellerSubtotal);
            order.setVoucherDiscount(sellerDiscount);
            order.setShippingFee(shippingFee);
            order.setFinalAmount(finalAmount);
            order.setPaymentMethod(PaymentStatus.PENDING);
            order.setOrderStatus(OrderStatus.PENDING);
            order.setPaymentStatus(request.getPaymentStatus());
            order.setAddress(address);
            order.setVoucher(voucher);
            order.setShippingAddressSnapshot(addressSnapshot);

            orderRepository.save(order);

            for (OrderItemsEntity item : sellerItems) {
                item.setOrder(order);
                orderItemRepository.save(item);
            }
        }

        return "Create order success";
    }
}
