package com.platform.identityservice.facade.impl;

import com.platform.identityservice.client.StoreClient;
import com.platform.identityservice.dto.customer.CustomerRequest;
import com.platform.identityservice.dto.customer.CustomerResponse;
import com.platform.identityservice.dto.customer.CustomerUpdateRequest;
import com.platform.identityservice.dto.store.BaseStoreResponse;
import com.platform.identityservice.facade.CustomerFacade;
import com.platform.identityservice.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerFacadeImpl implements CustomerFacade {

    private final CustomerService customerService;
    private final StoreClient storeClient;

    @Override
    public CustomerResponse create(CustomerRequest request) {
        BaseStoreResponse baseStore = storeClient.findByCode(request.storeCode());
        return customerService.create(baseStore, request);
    }

    @Override
    public CustomerResponse findById(UUID id) {
        return customerService.findById(id);
    }

    @Override
    public List<CustomerResponse> findAll(UUID storeId) {
        return customerService.findAll(storeId);
    }

    @Override
    public CustomerResponse update(UUID id, UUID userId, String roles, CustomerUpdateRequest request) {
        return customerService.update(id, userId, roles, request);
    }

    @Override
    public void deactivate(UUID id, UUID storeId, String roles) {
        customerService.deactivate(id, storeId, roles);
    }
}
