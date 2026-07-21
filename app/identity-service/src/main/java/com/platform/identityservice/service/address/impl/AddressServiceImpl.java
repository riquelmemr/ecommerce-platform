package com.platform.identityservice.service.address.impl;

import com.platform.identityservice.dto.address.AddressRequest;
import com.platform.identityservice.dto.address.AddressResponse;
import com.platform.identityservice.dto.address.AddressUpdateRequest;
import com.platform.identityservice.exception.AddressNotFoundException;
import com.platform.identityservice.exception.UserNotFoundException;
import com.platform.identityservice.model.AddressModel;
import com.platform.identityservice.model.CustomerModel;
import com.platform.identityservice.repository.AddressRepository;
import com.platform.identityservice.repository.CustomerRepository;
import com.platform.identityservice.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public AddressResponse create(UUID userId, UUID storeId, AddressRequest request) {
        CustomerModel customer = findCustomer(userId);

        AddressModel address = new AddressModel();
        address.setUser(customer);
        address.setStoreId(storeId);
        address.setName(request.name());
        address.setStreet(request.street());
        address.setNumber(request.number());
        address.setComplement(request.complement());
        address.setNeighborhood(request.neighborhood());
        address.setCity(request.city());
        address.setState(request.state());
        address.setPostalCode(request.postalCode());
        address.setCountry(request.country());
        address.setDefault(request.isDefault());

        return toResponse(addressRepository.save(address));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> findAll(UUID userId, UUID storeId) {
        findCustomer(userId);

        return addressRepository.findAllByUserIdAndStoreId(userId, storeId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public AddressResponse update(UUID id, UUID userId, AddressUpdateRequest request) {
        AddressModel address = findAddress(id, userId);

        mergeAddress(address, request);

        return toResponse(addressRepository.save(address));
    }

    @Override
    @Transactional
    public void setDefault(UUID id, UUID userId) {
        AddressModel address = findAddress(id, userId);

        addressRepository.findAllByUserIdAndStoreId(userId, address.getStoreId())
                .forEach(item -> item.setDefault(false));

        address.setDefault(true);
        addressRepository.save(address);
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID userId) {
        AddressModel address = findAddress(id, userId);
        addressRepository.delete(address);
    }

    private CustomerModel findCustomer(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private AddressModel findAddress(UUID id, UUID userId) {
        return addressRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

    private void mergeAddress(AddressModel address, AddressUpdateRequest request) {
        if (isNotEmpty(request.name())) {
            address.setName(request.name());
        }

        if (isNotEmpty(request.street())) {
            address.setStreet(request.street());
        }

        if (isNotEmpty(request.number())) {
            address.setNumber(request.number());
        }

        if (request.complement() != null) {
            address.setComplement(request.complement());
        }

        if (isNotEmpty(request.neighborhood())) {
            address.setNeighborhood(request.neighborhood());
        }

        if (isNotEmpty(request.city())) {
            address.setCity(request.city());
        }

        if (isNotEmpty(request.state())) {
            address.setState(request.state());
        }

        if (isNotEmpty(request.postalCode())) {
            address.setPostalCode(request.postalCode());
        }

        if (isNotEmpty(request.country())) {
            address.setCountry(request.country());
        }

        if (nonNull(request.isDefault())) {
            address.setDefault(request.isDefault());
        }

    }

    private AddressResponse toResponse(AddressModel address) {
        return new AddressResponse(
                address.getId(),
                address.getUser().getId(),
                address.getStoreId(),
                address.getName(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.isDefault(),
                address.getCreationTime(),
                address.getModifiedTime()
        );
    }
}
