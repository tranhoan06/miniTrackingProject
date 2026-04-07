package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.FilterProductRequest;
import com.example.miniTrackingProject.dto.request.ProductRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.dto.response.ProductOverviewResponse;
import com.example.miniTrackingProject.dto.response.ProductResponse;
import com.example.miniTrackingProject.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/api/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<List<?>>> getAll(@RequestParam(value = "page_size", required = true) Integer pageSize,
                                                                     @RequestParam(value = "page_number", required = true) Integer pageNumber,
                                                                      FilterProductRequest request
                                                                      ) {
        Page<ProductResponse> responses = productService.getAll(pageSize, pageNumber, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(responses.getContent()));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.success(productResponse));
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<ProductResponse>> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.updateProduct(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(productResponse));
    }

    @GetMapping("product-detail/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<ProductResponse>> getProductDetail(@PathVariable Long id) {
        ProductResponse productResponse = productService.getProductDetail(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(productResponse));
    }

    @GetMapping("/overview")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<ProductOverviewResponse>> getProductOverview() {
        ProductOverviewResponse productOverviewResponse = productService.getProductOverview();
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(productOverviewResponse));
    }

    @DeleteMapping("/delete-product/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success("Xóa sản phẩm thành công"));
    }
}
