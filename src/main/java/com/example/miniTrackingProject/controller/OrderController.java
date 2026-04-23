package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.dto.request.*;
import com.example.miniTrackingProject.dto.response.*;
import com.example.miniTrackingProject.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/v1/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // api preview trc khi tạo đơn
    @PostMapping("/preview")
    public ResponseEntity<BaseResponse<PreviewOrderResponse>> previewOrder(@RequestBody PreviewOrderRequest request) {
        PreviewOrderResponse response = orderService.previewOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api tạo đơn hàng
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOrder(@Valid @RequestBody OrderRequest request) {
        String response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api danh sách đơn hàng
    @GetMapping("/seller")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<List<?>>> getBySeller(Integer pageSize, Integer pageNumber, @RequestParam(required = false) Boolean isReturn, OrderStatus status) {
        Page<OrderResponse> response = orderService.getBySeller(pageSize, pageNumber, isReturn, status);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response.getContent()));
    }

    // api xác nhận
    @PostMapping("/confirm")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<OrderStatusResponse>> confirmOrder(@Valid @RequestBody ConfirmStatusRequest request) {
        OrderStatusResponse response = orderService.confirmOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api hủy đơn
    @PostMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_BUYER')")
    public ResponseEntity<BaseResponse<OrderStatusResponse>> cancelOrder(@Valid @RequestBody CancelOrderRequest request) {
        OrderStatusResponse response = orderService.cancelOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api đóng gói hàng
    @PostMapping("/packed")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<OrderStatusResponse>> packedOrder(@Valid @RequestBody OrderStatusRequest request) {
        OrderStatusResponse response = orderService.packedOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api giao dvvc
    @PostMapping("/assign-provider")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<OrderStatusResponse>> assignProviderOrder(@Valid @RequestBody OrderStatusRequest request) {
        OrderStatusResponse response = orderService.assignProviderOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api overview đơn hàng
    @GetMapping("/overview")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<OverviewOrderResponse>> overviewOrder(@RequestParam("type") String type) {
        OverviewOrderResponse response = orderService.overviewOrder(type);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api user trả hàng
    @PostMapping("/return-pending")
    @PreAuthorize("hasAnyRole('ROLE_BUYER')")
    public ResponseEntity<BaseResponse<OrderStatusResponse>> returnPendingOrder(@Valid @RequestBody CancelOrderRequest request) {
        OrderStatusResponse response = orderService.returnPendingOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api shipper nhận hàng trả về từ user
    @PostMapping("/return")
    @PreAuthorize("hasAnyRole('ROLE_SHIPPER')")
    public ResponseEntity<BaseResponse<OrderStatusResponse>> returnOrder(@Valid @RequestBody OrderStatusRequest request) {
        OrderStatusResponse response = orderService.returnOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api nhận hàng trả về
    @PostMapping("/warehouse-received")
    @PreAuthorize("hasAnyRole('ROLE_SHIPPER')")
    public ResponseEntity<BaseResponse<OrderStatusResponse>> warehouseReceivedOrder(@Valid @RequestBody OrderStatusRequest request) {
        OrderStatusResponse response = orderService.warehouseReceivedOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api trả hàng về kho
    @PostMapping("/restocked")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<OrderStatusResponse>> restockedOrder(@Valid @RequestBody OrderStatusRequest request) {
        OrderStatusResponse response = orderService.restockedOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api hoàn tiền
    @PostMapping("/refunded")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<OrderStatusResponse>> refundedOrder(@Valid @RequestBody OrderStatusRequest request) {
        OrderStatusResponse response = orderService.refundedOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api chi tiết order
    @GetMapping("order-detail/{id}")
    public ResponseEntity<BaseResponse<OrderResponse>> orderDetail(@PathVariable Long id) {
        OrderResponse response = orderService.orderDetail(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api thống kê user
    @GetMapping("overview-user")
    @PreAuthorize("hasAnyRole('ROLE_BUYER')")
    public ResponseEntity<BaseResponse<OverviewOrderResponse>> overviewUser() {
        OverviewOrderResponse response = orderService.getOverviewUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    // api quản lý danh sách đơn hàng
    @GetMapping("order-buyer")
    @PreAuthorize("hasAnyRole('ROLE_BUYER')")
    public ResponseEntity<BaseResponse<List<?>>> orderBuyer(Integer pageSize, Integer pageNumber, OrderStatus status) {
        Page<OrderResponse> response = orderService.getByBuyer(pageSize, pageNumber, status);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response.getContent()));
    }


}
