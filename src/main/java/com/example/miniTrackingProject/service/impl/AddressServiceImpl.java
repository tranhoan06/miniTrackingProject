package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.dto.request.AddressRequest;
import com.example.miniTrackingProject.dto.response.AddressResponse;
import com.example.miniTrackingProject.entity.AddresesEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
//import com.example.miniTrackingProject.mapper.AddressMapper;
import com.example.miniTrackingProject.repository.AddressRepository;
import com.example.miniTrackingProject.repository.UserRepository;
import com.example.miniTrackingProject.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
//    private final AddressMapper addressMapper;

    @Override
    public AddressResponse createAddress(AddressRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUser())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

        AddresesEntity addresesEntity = new AddresesEntity();
        addresesEntity.setUser(userEntity);
        addresesEntity.setReceiverName(request.getReceiverName());
        addresesEntity.setPhone(request.getPhone());
        addresesEntity.setProvinceId(request.getProvinceId());
        addresesEntity.setProvinceName(request.getProvinceName());
        addresesEntity.setDistrictId(request.getDistrictId());
        addresesEntity.setDistrictName(request.getDistrictName());
        addresesEntity.setWardId(request.getWardId());
        addresesEntity.setWardName(request.getWardName());
        addresesEntity.setDetailAddress(request.getDetailAddress());
        addresesEntity.setIsDefault(request.getIsDefault());
        addresesEntity.setCreatedAt(LocalDateTime.now());
        addressRepository.save(addresesEntity);

        modelMapper.typeMap(AddresesEntity.class, AddressResponse.class)
                .addMappings(mapper -> mapper.skip(AddressResponse::setUser));

        AddressResponse addressResponse = modelMapper.map(addresesEntity, AddressResponse.class);
        addressResponse.setUser(addresesEntity.getUser().getId());
        return addressResponse;
//        return addressMapper.toResponse(addresesEntity);
    }
}
