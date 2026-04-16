package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.*;
import com.example.miniTrackingProject.dto.request.*;
import com.example.miniTrackingProject.dto.response.*;
import com.example.miniTrackingProject.entity.*;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.AddressMapper;
import com.example.miniTrackingProject.mapper.OrderMapper;
import com.example.miniTrackingProject.repository.*;
import com.example.miniTrackingProject.repository.projection.OrderOverviewProjection;
import com.example.miniTrackingProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final BigDecimal DEFAULT_SHIPPING_FEE = BigDecimal.valueOf(30_000);
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;
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
    private final OrderMapper orderMapper;
    private final ShippingProviderRepository shippingProviderRepository;
    private final OrderStatusLogRepository orderStatusLogRepository;

    private void logOrderStatusChange(OrdersEntity order, OrderStatus from, OrderStatus to, String note, UserEntity changedBy) {
        if (order == null || order.getId() == null) return;
        if (from == to) return;

        OrderStatusLog log = new OrderStatusLog();
        log.setOrderId(order);
        log.setStatus(to);
        log.setNote(note);

        String changer = (changedBy != null && changedBy.getFullname() != null && !changedBy.getFullname().isBlank())
                ? changedBy.getFullname()
                : (changedBy != null ? changedBy.getUsername() : null);
        log.setChangedBy(changer);

        orderStatusLogRepository.save(log);
    }

    @Override
    public PreviewOrderResponse previewOrder(PreviewOrderRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        LocalDateTime today = LocalDateTime.now();
        VouchersEntity voucher = voucherRepository.findVoucher(request.getVoucherId(), today)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.VOUCHER_NOT_FOUND));

        validateVoucher(voucher);

        List<CartItemsEntity> orderItems = new ArrayList<>();
        for (Long productId : request.getProductId()) {
            CartItemsEntity line = cartItemRepository
                    .findActiveByUserAndProduct(user.getId(), productId)
                    .orElseThrow(() -> new JavaBuilderException(ErrorCode.CART_NOT_FOUND));
            orderItems.add(line);
        }

        BigDecimal subtotal = calculateSubtotal(orderItems);

        if (subtotal.compareTo(voucher.getMinOrderAmount()) < 0) {
            throw new JavaBuilderException(ErrorCode.ORDER_BELOW_MIN_AMOUNT);
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

            BigDecimal shippingFee = DEFAULT_SHIPPING_FEE;

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
            logOrderStatusChange(order, null, OrderStatus.PENDING, "CREATE_ORDER", user);

            for (OrderItemsEntity item : itemsBySeller.get(sellerId)) {
                item.setOrder(order);
            }

            orderItemRepository.saveAll(itemsBySeller.get(sellerId));
        }
        voucher.setUsedCount(voucher.getUsedCount() + 1);
        return "Create order success";
    }

    @Override
    public Page<OrderResponse> getBySeller(Integer pageSize, Integer pageNumber) {
        UserEntity seller = securityHelper.getCurrentUser();
        int page = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        int size = (pageSize == null || pageSize < 1) ? DEFAULT_PAGE_SIZE : Math.min(pageSize, MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());

        Page<OrdersEntity> entityPage = orderRepository.findBySeller(seller, pageable);
        return entityPage.map(orderMapper::toOrderResponse);
    }

    @Override
    @Transactional
    public OrderStatusResponse confirmOrder(ConfirmStatusRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        List<OrdersEntity> ordersList = orderRepository.findAllById(request.getOrderId());

        if (ordersList.size() != request.getOrderId().size()) {
            throw new JavaBuilderException(ErrorCode.SOME_ORDERS_NOT_FOUND_OR_INVALID);
        }

        validateOrdersForSellerAction(ordersList, user);

        ordersList.forEach(order -> {
            OrderStatus from = order.getOrderStatus();
            order.setOrderStatus(OrderStatus.CONFIRMED);
            order.setUpdatedAt(LocalDateTime.now());
            logOrderStatusChange(order, from, OrderStatus.CONFIRMED, "CONFIRM", user);
        });
        orderRepository.saveAll(ordersList);

        return mapToConfirmOrderResponse("CONFIRM", ordersList, request.getOrderId().size());
    }

    @Override
    @Transactional
    public OrderStatusResponse cancelOrder(CancelOrderRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        OrdersEntity order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.NOT_FOUND));

        boolean isSeller = order.getSeller() != null && order.getSeller().getId().equals(user.getId());
        boolean isBuyer = order.getBuyer() != null && order.getBuyer().getId().equals(user.getId());
        if (!isSeller && !isBuyer) {
            throw new JavaBuilderException(ErrorCode.ACCESS_DENIED);
        }

        if (!order.getOrderStatus().equals(OrderStatus.PENDING)) {
            throw new JavaBuilderException(ErrorCode.INVALID_STATUS);
        }

        if (order.getItems() != null) {
            for (OrderItemsEntity item : order.getItems()) {
                if (item.getProduct() == null || item.getProduct().getId() == null) continue;
                long qty = item.getQuantity() == null ? 0L : item.getQuantity().longValue();
                InventoryEntity inv = inventoryRepository.findByProductIdForUpdate(item.getProduct().getId())
                        .orElseThrow(() -> new JavaBuilderException(ErrorCode.NOT_FOUND));
                long currentReserved = inv.getReservedQuantity() == null ? 0L : inv.getReservedQuantity();
                long newReserved = currentReserved - qty;
                if (newReserved < 0) {
                    newReserved = 0;
                }
                inv.setReservedQuantity(newReserved);
                inv.setUpdatedAt(LocalDateTime.now());
                inventoryRepository.save(inv);
            }
        }

        OrderStatus from = order.getOrderStatus();
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        logOrderStatusChange(order, from, OrderStatus.CANCELLED, request.getReason(), user);

        return mapToConfirmOrderResponse("CANCELLED", List.of(order), 1);
    }

    @Override
    @Transactional
    public OrderStatusResponse packedOrder(OrderStatusRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        OrdersEntity order = requireOrderOwnedBySeller(request.getOrderId(), user);

        if (!order.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
            throw new JavaBuilderException(ErrorCode.INVALID_STATUS);
        }

        OrderStatus from = order.getOrderStatus();
        order.setOrderStatus(OrderStatus.PACKED);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        logOrderStatusChange(order, from, OrderStatus.PACKED, "PACKED", user);

        return mapToConfirmOrderResponse("PACKED", List.of(order), 1);
    }

    @Override
    @Transactional
    public OrderStatusResponse assignProviderOrder(OrderStatusRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        OrdersEntity order = requireOrderOwnedBySeller(request.getOrderId(), user);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String ts = LocalDateTime.now().format(fmt);
        if (!order.getOrderStatus().equals(OrderStatus.PACKED)) {
            throw new JavaBuilderException(ErrorCode.INVALID_STATUS);
        }

        List<ShippingProviderEntity> shippingProvider = shippingProviderRepository.findAll();
        if (shippingProvider.isEmpty()) {
            throw new JavaBuilderException(ErrorCode.SHIPPING_PROVIDER_NOT_FOUND);
        }
        ShippingProviderEntity randomProvider = shippingProvider.get(
                ThreadLocalRandom.current().nextInt(shippingProvider.size()));

        String trackingCode = randomProvider.getCode() + "-" + ts + "-" + order.getId();
        order.setShippingProvider(randomProvider);
        OrderStatus from = order.getOrderStatus();
        order.setOrderStatus(OrderStatus.SHIPPED);
        order.setUpdatedAt(LocalDateTime.now());
        order.setTrackingCode(trackingCode);
        orderRepository.save(order);
        logOrderStatusChange(order, from, OrderStatus.SHIPPED, trackingCode, user);
        return mapToConfirmOrderResponse("SHIPPED", List.of(order), 1);
    }

    @Override
    public OverviewOrderResponse overviewOrder(String type) {
        UserEntity seller = securityHelper.getCurrentUser();
        OrderOverviewProjection projection = orderRepository.getOverviewBySeller(seller);

        OverviewOrderResponse response = new OverviewOrderResponse();
        response.setTotalAmount(projection.getTotalAmount());
        response.setTotalOrder(projection.getTotalOrder());
        response.setTotalPending(projection.getTotalPending());
        response.setTotalIntransit(projection.getTotalIntransit());
        response.setTotalCancel(projection.getTotalCancel());
        response.setTotalFailed(projection.getTotalFailed());
        response.setTotalReturn(projection.getTotalReturn());
        response.setAwaitingInspection(projection.getAwaitingInspection());
        response.setTotalRefunds(projection.getTotalRefunds());
        return response;
    }

    @Override
    public OrderStatusResponse returnPendingOrder(CancelOrderRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        OrdersEntity order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.NOT_FOUND));
        boolean isNotBuyer = order.getBuyer() == null || !order.getBuyer().getId().equals(user.getId());
        if (isNotBuyer) {
            throw new JavaBuilderException(ErrorCode.ACCESS_DENIED);
        }

        if (!order.getOrderStatus().equals(OrderStatus.DELIVERED)) {
            throw new JavaBuilderException(ErrorCode.INVALID_STATUS);
        }

        OrderStatus from = order.getOrderStatus();
        order.setOrderStatus(OrderStatus.RETURN_PENDING);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        logOrderStatusChange(order, from, OrderStatus.RETURN_PENDING, request.getReason(), user);
        return mapToConfirmOrderResponse("RETURN_PENDING", List.of(order), 1);
    }

    private OrdersEntity requireOrderOwnedBySeller(Long orderId, UserEntity user) {
        OrdersEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.NOT_FOUND));
        boolean isNotSeller = order.getSeller() == null || !order.getSeller().getId().equals(user.getId());
        if (isNotSeller) {
            throw new JavaBuilderException(ErrorCode.ACCESS_DENIED);
        }
        return order;
    }

    private void validateOrdersForSellerAction(List<OrdersEntity> ordersList, UserEntity user) {
        ordersList.forEach(order -> {
            boolean isNotSeller = order.getSeller() == null || !order.getSeller().getId().equals(user.getId());
            if (isNotSeller) {
                throw new JavaBuilderException(ErrorCode.ACCESS_DENIED);
            }

            if (!order.getOrderStatus().equals(OrderStatus.PENDING)) {
                throw new JavaBuilderException(ErrorCode.INVALID_STATUS);
            }
        });
    }

    private OrderStatusResponse mapToConfirmOrderResponse(String action, List<OrdersEntity> ordersList, int totalRequested) {
        List<OrderResultDetailResponse> details = ordersList.stream()
                .map(order -> {
                    OrderResultDetailResponse detail = new OrderResultDetailResponse();
                    detail.setOrderId(order.getId());
                    detail.setStatus(order.getOrderStatus().name());
                    detail.setMessage(action + " thành công");
                    detail.setIsSuccess(true);
                    return detail;
                })
                .collect(Collectors.toList());

        OrderStatusResponse response = new OrderStatusResponse();
        response.setAction(action);
        response.setTotalRequested(totalRequested);
        response.setTotalSuccess(ordersList.size());
        response.setDetails(details);
        return response;
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

        long qty = quantity == null ? 0L : quantity.longValue();
        long inStock = Objects.requireNonNullElse(inventory.getQuantityInStock(), 0L);
        long reserved = Objects.requireNonNullElse(inventory.getReservedQuantity(), 0L);

        long available = inStock - reserved;

        if (available < qty) {
            throw new JavaBuilderException(ErrorCode.PRODUCT_NOT_ENOUGH_STOCK);
        }

        inventory.setReservedQuantity(reserved + qty);

        inventory.setUpdatedAt(LocalDateTime.now());

        inventoryRepository.save(inventory);
    }
}
