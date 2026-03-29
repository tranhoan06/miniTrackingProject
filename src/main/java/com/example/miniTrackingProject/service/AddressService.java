package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.AddressRequest;
import com.example.miniTrackingProject.dto.response.AddressResponse;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);
}
