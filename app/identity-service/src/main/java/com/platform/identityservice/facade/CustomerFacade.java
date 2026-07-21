package com.platform.identityservice.facade;

import com.platform.identityservice.dto.customer.CustomerRequest;
import com.platform.identityservice.dto.customer.CustomerResponse;
import com.platform.identityservice.dto.customer.CustomerUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface CustomerFacade {

    CustomerResponse create(CustomerRequest request);

    CustomerResponse findById(UUID id);

    List<CustomerResponse> findAll(UUID storeId);

    CustomerResponse update(UUID id, UUID userId, String roles, CustomerUpdateRequest request);

    void deactivate(UUID id, UUID storeId, String roles);

}
