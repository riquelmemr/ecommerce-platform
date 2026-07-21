package com.platform.identityservice.controller;

import com.platform.identityservice.dto.customer.CustomerRequest;
import com.platform.identityservice.dto.customer.CustomerResponse;
import com.platform.identityservice.dto.customer.CustomerUpdateRequest;
import com.platform.identityservice.facade.CustomerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerFacade customerFacade;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerFacade.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Secured({ "ROLE_PLATFORM_ADMIN", "ROLE_STORE_EMPLOYEE" })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable UUID id) {
        CustomerResponse response = customerFacade.findById(id);
        return ResponseEntity.ok(response);
    }

    @Secured({ "ROLE_CUSTOMER", "ROLE_PLATFORM_ADMIN", "ROLE_STORE_EMPLOYEE" })
    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> findMe(@RequestHeader("X-User-Id") UUID userId) {
        CustomerResponse response = customerFacade.findById(userId);
        return ResponseEntity.ok(response);
    }

    @Secured({ "ROLE_PLATFORM_ADMIN", "ROLE_STORE_EMPLOYEE" })
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(@RequestHeader("X-Store-Id") UUID storeId) {
        List<CustomerResponse> response = customerFacade.findAll(storeId);
        return ResponseEntity.ok(response);
    }

    @Secured({ "ROLE_CUSTOMER", "ROLE_PLATFORM_ADMIN", "ROLE_STORE_EMPLOYEE" })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-User-Roles") String roles,
            @Valid @RequestBody CustomerUpdateRequest request) {
        CustomerResponse response = customerFacade.update(id, userId, roles, request);
        return ResponseEntity.ok().body(response);
    }

    @Secured({ "ROLE_PLATFORM_ADMIN", "ROLE_STORE_EMPLOYEE" })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateCustomer(
            @PathVariable UUID id,
            @RequestHeader("X-Store-Id") UUID storeId,
            @RequestHeader("X-User-Roles") String roles) {
        customerFacade.deactivate(id, storeId, roles);
    }
}