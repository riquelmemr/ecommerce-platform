package com.platform.storeservice.service.impl;

import com.platform.storeservice.dto.request.BaseStoreRequest;
import com.platform.storeservice.dto.request.BaseStoreUpdateRequest;
import com.platform.storeservice.dto.response.BaseStoreResponse;
import com.platform.storeservice.exception.BaseStoreAlreadyExistsException;
import com.platform.storeservice.exception.BaseStoreNotFoundException;
import com.platform.storeservice.mapper.BaseStoreMapper;
import com.platform.storeservice.model.BaseStoreModel;
import com.platform.storeservice.repository.BaseStoreRepository;
import com.platform.storeservice.service.BaseStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@RequiredArgsConstructor
public class BaseStoreServiceImpl implements BaseStoreService {

    private final BaseStoreRepository baseStoreRepository;
    private final BaseStoreMapper baseStoreMapper;

    @Override
    @Transactional
    public BaseStoreResponse create(BaseStoreRequest request) {
        validateCodeIsAvailable(request.code(), null);

        BaseStoreModel model = baseStoreMapper.toModel(request);
        BaseStoreModel savedModel = baseStoreRepository.save(model);

        return baseStoreMapper.toResponse(savedModel);
    }

    @Override
    @Transactional(readOnly = true)
    public BaseStoreResponse findById(UUID id) {
        BaseStoreModel model = baseStoreRepository.findById(id)
                .orElseThrow(() -> new BaseStoreNotFoundException(id));

        return baseStoreMapper.toResponse(model);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BaseStoreResponse> findAll() {
        return baseStoreRepository.findAll().stream().map(baseStoreMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public BaseStoreResponse update(UUID id, BaseStoreUpdateRequest request) {
        BaseStoreModel model = baseStoreRepository.findById(id)
                .orElseThrow(() -> new BaseStoreNotFoundException(id));

        if (isNotEmpty(request.code())) {
            validateCodeIsAvailable(request.code(), id);
        }

        mergeStore(model, request);

        BaseStoreModel savedModel = baseStoreRepository.save(model);

        return baseStoreMapper.toResponse(savedModel);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!baseStoreRepository.existsById(id)) {
            throw new BaseStoreNotFoundException(id);
        }

        baseStoreRepository.deleteById(id);
    }

    private void validateCodeIsAvailable(String code, UUID currentId) {
        baseStoreRepository.findByCode(code).ifPresent(existingStore -> {
            if (!existingStore.getId().equals(currentId)) {
                throw new BaseStoreAlreadyExistsException(code);
            }
        });
    }

    private void mergeStore(BaseStoreModel model, BaseStoreUpdateRequest request) {
        if (isNotEmpty(request.code())) {
            model.setCode(request.code());
        }

        if (isNotEmpty(request.name())) {
            model.setName(request.name());
        }

        if (isNotEmpty(request.logoUrl())) {
            model.setLogoUrl(request.logoUrl());
        }

        if (isNotEmpty(request.primaryColor())) {
            model.setPrimaryColor(request.primaryColor());
        }

        if (nonNull(request.isActive())) {
            model.setActive(request.isActive());
        }

        if (isNotEmpty(request.domain())) {
            model.setDomain(request.domain());
        }
    }
}
