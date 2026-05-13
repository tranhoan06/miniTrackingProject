package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.AddressRequest;
import com.example.miniTrackingProject.dto.response.AddressResponse;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/v1/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_BUYER')")
    public ResponseEntity<BaseResponse<AddressResponse>> createAddress(@Valid @RequestBody AddressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.success(
                        addressService.createAddress(request)
                ));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_BUYER')")
    public ResponseEntity<BaseResponse<AddressResponse>> updateAddress(@Valid @PathVariable Long id,
                                                                       @RequestBody AddressRequest request){
        AddressResponse response = addressService.updateAddress(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        response
                ));
    }

    @GetMapping("/getByUser/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_BUYER')")
    public ResponseEntity<BaseResponse<List<AddressResponse>>> getLstAddressByUser(@PathVariable Long id) {
        List<AddressResponse> addressResponseList = addressService.getLstAddressByUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        addressResponseList
                ));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_BUYER')")
    public ResponseEntity<?> deleteAddress (@PathVariable Long id) {
         addressService.deleteAddress(id);
        return ResponseEntity.ok("Xóa thành công");
    }
}
