package com.platform.identityservice.controller;

import com.platform.identityservice.dto.employee.EmployeeRequest;
import com.platform.identityservice.dto.employee.EmployeeResponse;
import com.platform.identityservice.dto.employee.EmployeeUpdateRequest;
import com.platform.identityservice.service.employee.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Secured({ "ROLE_PLATFORM_ADMIN" })
    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Secured({ "ROLE_PLATFORM_ADMIN" })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable UUID id) {
        EmployeeResponse response = employeeService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Secured({ "ROLE_PLATFORM_ADMIN" })
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> findAll(@RequestHeader("X-Store-Id") UUID storeId) {
        List<EmployeeResponse> response = employeeService.findAll(storeId);
        return ResponseEntity.ok(response);
    }

    @Secured({ "ROLE_PLATFORM_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable UUID id, @Valid @RequestBody EmployeeUpdateRequest request) {
        EmployeeResponse response = employeeService.update(id, request);
        return ResponseEntity.ok().body(response);
    }

    @Secured({ "ROLE_PLATFORM_ADMIN" })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateCustomer(@PathVariable UUID id) {
        employeeService.deactivate(id);
    }
}