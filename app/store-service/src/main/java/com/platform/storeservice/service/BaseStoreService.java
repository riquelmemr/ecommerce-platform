package com.platform.storeservice.service;

import com.platform.storeservice.dto.request.BaseStoreRequest;
import com.platform.storeservice.dto.request.BaseStoreUpdateRequest;
import com.platform.storeservice.dto.response.BaseStoreResponse;

import java.util.List;
import java.util.UUID;

public interface BaseStoreService {

    BaseStoreResponse create(BaseStoreRequest request);

    BaseStoreResponse findByCode(String code);

    BaseStoreResponse findById(UUID id);

    List<BaseStoreResponse> findAll();

    BaseStoreResponse update(UUID id, BaseStoreUpdateRequest request);

    void delete(UUID id);
}
