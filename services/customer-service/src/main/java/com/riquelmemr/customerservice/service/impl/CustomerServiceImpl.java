package com.riquelmemr.customerservice.service.impl;

import com.riquelmemr.customerservice.converter.AddressModelConverter;
import com.riquelmemr.customerservice.dto.CustomerRequest;
import com.riquelmemr.customerservice.dto.CustomerResponse;
import com.riquelmemr.customerservice.exception.CustomerNotFoundException;
import com.riquelmemr.customerservice.model.AddressModel;
import com.riquelmemr.customerservice.model.CustomerModel;
import com.riquelmemr.customerservice.repository.CustomerRepository;
import com.riquelmemr.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final Converter<CustomerRequest, CustomerModel> customerModelConverter;
    private final Converter<CustomerModel, CustomerResponse> customerResponseConverter;
    private final AddressModelConverter addressModelConverter;

    @Override
    public String create(CustomerRequest request) {
        CustomerModel customer = customerRepository.save(customerModelConverter.convert(request));
        return customer.getId();
    }

    @Override
    public void update(CustomerRequest request) {
        CustomerModel customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(request.id()));

        mergeCustomer(customer, request);
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerResponseConverter::convert)
                .toList();
    }

    @Override
    public Boolean existsById(String customerId) {
        return customerRepository.existsById(customerId);
    }

    @Override
    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerResponseConverter::convert)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public void delete(String customerId) {
        findById(customerId);
        customerRepository.deleteById(customerId);
    }

    private void mergeCustomer(CustomerModel customer, CustomerRequest request) {
        if (isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }

        if (isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }

        if (isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }

        if (nonNull(request.addresses()) && !request.addresses().isEmpty()) {
            List<AddressModel> addresses = request.addresses()
                    .stream()
                    .map(addressModelConverter::convert)
                    .toList();

            customer.setAddresses(addresses);
        }
    }
}
