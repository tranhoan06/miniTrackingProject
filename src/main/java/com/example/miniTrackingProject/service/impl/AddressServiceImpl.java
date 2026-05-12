package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.dto.request.AddressRequest;
import com.example.miniTrackingProject.dto.response.AddressResponse;
import com.example.miniTrackingProject.entity.AddressesEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.AddressMapper;
import com.example.miniTrackingProject.repository.AddressRepository;
import com.example.miniTrackingProject.repository.UserRepository;
import com.example.miniTrackingProject.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public AddressResponse createAddress(AddressRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUser())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

        AddressesEntity addressesEntity = new AddressesEntity();
        addressesEntity.setUser(userEntity);
        addressesEntity.setReceiverName(request.getReceiverName());
        addressesEntity.setPhone(request.getPhone());
        addressesEntity.setProvinceId(request.getProvinceId());
        addressesEntity.setProvinceName(request.getProvinceName());
        addressesEntity.setDistrictId(request.getDistrictId());
        addressesEntity.setDistrictName(request.getDistrictName());
        addressesEntity.setWardId(request.getWardId());
        addressesEntity.setWardName(request.getWardName());
        addressesEntity.setDetailAddress(request.getDetailAddress());
        addressesEntity.setIsDefault(request.getIsDefault());
        addressesEntity.setIsDelete(false);
        addressesEntity.setCreatedAt(LocalDateTime.now());
        addressRepository.save(addressesEntity);

        return addressMapper.toResponse(addressesEntity);
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(Long id, AddressRequest request) {
        AddressesEntity addressesEntity = addressRepository.findById(id).orElseThrow(() -> new JavaBuilderException(ErrorCode.ADDRESS_NOT_FOUND));

        addressesEntity.setReceiverName(request.getReceiverName());
        addressesEntity.setPhone(request.getPhone());
        addressesEntity.setProvinceId(request.getProvinceId());
        addressesEntity.setProvinceName(request.getProvinceName());
        addressesEntity.setDistrictId(request.getDistrictId());
        addressesEntity.setDistrictName(request.getDistrictName());
        addressesEntity.setWardId(request.getWardId());
        addressesEntity.setWardName(request.getWardName());
        addressesEntity.setDetailAddress(request.getDetailAddress());
        addressesEntity.setIsDefault(request.getIsDefault());
        addressesEntity.setUpdatedAt(LocalDateTime.now());
        addressRepository.save(addressesEntity);

        return addressMapper.toResponse(addressesEntity);
    }

    @Override
    public List<AddressResponse> getLstAddressByUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

        List<AddressesEntity> addressesEntityList = addressRepository.findByUser_IdAndIsDeleteFalse(userEntity.getId());

        List<AddressResponse> responseList = addressesEntityList.stream()
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
        AddressesEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.ADDRESS_NOT_FOUND));

        entity.setIsDelete(true);
        entity.setIsDefault(false);
        entity.setUpdatedAt(LocalDateTime.now());
        addressRepository.save(entity);
    }

}
