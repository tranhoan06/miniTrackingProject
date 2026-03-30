package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.dto.request.AddressRequest;
import com.example.miniTrackingProject.dto.response.AddressResponse;
import com.example.miniTrackingProject.entity.AddresesEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.AddressMapper;
import com.example.miniTrackingProject.repository.AddressRepository;
import com.example.miniTrackingProject.repository.UserRepository;
import com.example.miniTrackingProject.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

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
        addresesEntity.setIsDelete(false);
        addresesEntity.setCreatedAt(LocalDateTime.now());
        addressRepository.save(addresesEntity);

        return addressMapper.toResponse(addresesEntity);
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(Long id, AddressRequest request) {
        AddresesEntity addresesEntity = addressRepository.findById(id).orElseThrow(() -> new JavaBuilderException(ErrorCode.ADDRESS_NOT_FOUND));

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
        addresesEntity.setUpdatedAt(LocalDateTime.now());
        addressRepository.save(addresesEntity);

        return addressMapper.toResponse(addresesEntity);
    }

    @Override
    public List<AddressResponse> getLstAddressByUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

        List<AddresesEntity> addresesEntityList = addressRepository.findByUser_IdAndIsDeleteFalse(userEntity.getId());

        List<AddressResponse> responseList = addresesEntityList.stream()
                .map(item -> {
                    AddressResponse res = addressMapper.toResponse(item);

                    if (item.getUser() != null) {
                        res.setUser(item.getUser().getId());
                    }

                    return res;
                })
                .toList();


        return responseList;
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        AddresesEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.ADDRESS_NOT_FOUND));

        entity.setIsDelete(true);
        entity.setIsDefault(false);
        entity.setUpdatedAt(LocalDateTime.now());
        addressRepository.save(entity);
    }

}
