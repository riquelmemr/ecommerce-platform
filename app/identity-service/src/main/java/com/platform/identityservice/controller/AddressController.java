package com.platform.identityservice.controller;

import com.platform.identityservice.dto.address.AddressRequest;
import com.platform.identityservice.dto.address.AddressResponse;
import com.platform.identityservice.dto.address.AddressUpdateRequest;
import com.platform.identityservice.service.address.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers/{userId}/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @Secured({ "ROLE_CUSTOMER" })
    @PostMapping
    public AddressResponse create(
            @PathVariable UUID userId,
            @RequestHeader("X-Store-Id") UUID storeId,
            @Valid @RequestBody AddressRequest request) {
        return addressService.create(userId, storeId, request);
    }

    @Secured({ "ROLE_CUSTOMER" })
    @GetMapping
    public List<AddressResponse> findAll(@PathVariable UUID userId, @RequestHeader("X-Store-Id") UUID storeId) {
        return addressService.findAll(userId, storeId);
    }

    @Secured({ "ROLE_CUSTOMER" })
    @PutMapping("/{id}")
    public AddressResponse update(
            @PathVariable UUID userId,
            @PathVariable UUID id,
            @Valid @RequestBody AddressUpdateRequest request) {
        return addressService.update(id, userId, request);
    }

    @Secured({ "ROLE_CUSTOMER" })
    @PatchMapping("/{id}/default")
    public void setDefault(@PathVariable UUID userId, @PathVariable UUID id) {
        addressService.setDefault(id, userId);
    }

    @Secured({ "ROLE_CUSTOMER" })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId, @PathVariable UUID id) {
        addressService.delete(id, userId);
    }
}

