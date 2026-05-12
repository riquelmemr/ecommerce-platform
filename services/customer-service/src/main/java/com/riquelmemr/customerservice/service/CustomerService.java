package com.riquelmemr.customerservice.service;

import com.riquelmemr.customerservice.dto.CustomerRequest;
import com.riquelmemr.customerservice.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    String create(CustomerRequest request);

    void update(CustomerRequest request);

    List<CustomerResponse> findAll();

    Boolean existsById(String customerId);

    CustomerResponse findById(String customerId);

    void delete(String customerId);
}
