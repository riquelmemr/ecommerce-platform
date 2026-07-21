package com.platform.identityservice.service.address;

import com.platform.identityservice.dto.address.AddressRequest;
import com.platform.identityservice.dto.address.AddressResponse;
import com.platform.identityservice.dto.address.AddressUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressResponse create(UUID userId, UUID storeId, AddressRequest request);

    List<AddressResponse> findAll(UUID userId, UUID storeId);

    AddressResponse update(UUID id, UUID userId, AddressUpdateRequest request);

    void setDefault(UUID id, UUID userId);

    void delete(UUID id, UUID userId);
}
