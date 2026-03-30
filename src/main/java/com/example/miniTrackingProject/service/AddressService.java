package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.AddressRequest;
import com.example.miniTrackingProject.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);

    AddressResponse updateAddress(Long id, AddressRequest request);

    List<AddressResponse> getLstAddressByUser(Long id);

    void deleteAddress(Long id);
}
