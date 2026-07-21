package com.platform.identityservice.service.customer.impl;

import com.platform.identityservice.dto.customer.CustomerRequest;
import com.platform.identityservice.dto.customer.CustomerResponse;
import com.platform.identityservice.dto.customer.CustomerUpdateRequest;
import com.platform.identityservice.dto.store.BaseStoreResponse;
import com.platform.identityservice.enums.RoleName;
import com.platform.identityservice.exception.UnauthorizedException;
import com.platform.identityservice.exception.UserAlreadyExistsException;
import com.platform.identityservice.exception.UserNotFoundException;
import com.platform.identityservice.mapper.CustomerMapper;
import com.platform.identityservice.model.CustomerModel;
import com.platform.identityservice.model.RoleModel;
import com.platform.identityservice.repository.CustomerRepository;
import com.platform.identityservice.repository.RoleRepository;
import com.platform.identityservice.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.platform.identityservice.utils.UserUtils.*;
import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponse create(BaseStoreResponse baseStore, CustomerRequest request) {
        validateEmailIsAvailable(request.email(), baseStore.id(), null);

        CustomerModel customer = customerMapper.toEntity(request);

        customer.setStoreId(baseStore.id());
        customer.setPassword(passwordEncoder.encode(request.password()));
        customer.setActive(true);
        customer.setEmployee(false);
        customer.getRoles().add(loadRole());

        customerRepository.save(customer);

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID id) {
        CustomerModel customer = findCustomer(id);
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll(UUID storeId) {
        return customerRepository.findAllByStoreId(storeId).stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CustomerResponse update(UUID id, UUID userId, String roles, CustomerUpdateRequest request) {
        if (isNotAuthorizedToUpdate(id, userId, roles)) {
            throw new UnauthorizedException("You can only update your own data");
        }

        CustomerModel customer = findCustomer(id);

        mergeCustomer(customer, request);
        customerRepository.save(customer);

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public void deactivate(UUID id, UUID storeId, String roles) {
        CustomerModel customer = findCustomer(id);

        if (isNotAuthorizedToDeactivate(customer, roles, storeId)) {
            throw new UnauthorizedException("You can only deactivate customers from your own store");
        }

        customer.setActive(false);
        customerRepository.save(customer);
    }

    private CustomerModel findCustomer(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private RoleModel loadRole() {
        return roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(() -> new IllegalStateException("Role not found: " + RoleName.CUSTOMER));
    }

    private void validateEmailIsAvailable(String email, UUID storeId, UUID currentId) {
        boolean exists = isNull(currentId)
                ? customerRepository.existsByEmailAndStoreId(email, storeId)
                : customerRepository.existsByEmailAndStoreIdAndIdNot(email, storeId, currentId);

        if (exists) {
            throw new UserAlreadyExistsException("Customer already exists: " + email);
        }
    }

    private boolean isNotAuthorizedToUpdate(UUID idToUpdate, UUID userId, String roles) {
        return !idToUpdate.equals(userId) && isNotPlatformAdmin(roles);
    }

    private boolean isNotAuthorizedToDeactivate(CustomerModel customer, String roles, UUID storeId) {
        return isNotPlatformAdmin(roles) && !customer.getStoreId().equals(storeId);
    }

    private void mergeCustomer(CustomerModel customer, CustomerUpdateRequest request) {
        if (isNotEmpty(request.name())) {
            customer.setName(request.name());
        }
    }
}
