package com.platform.identityservice.service.employee.impl;

import com.platform.identityservice.dto.employee.EmployeeRequest;
import com.platform.identityservice.dto.employee.EmployeeResponse;
import com.platform.identityservice.dto.employee.EmployeeUpdateRequest;
import com.platform.identityservice.dto.internal.UserCredentialsResponse;
import com.platform.identityservice.enums.RoleName;
import com.platform.identityservice.exception.UserAlreadyExistsException;
import com.platform.identityservice.exception.UserNotFoundException;
import com.platform.identityservice.model.EmployeeModel;
import com.platform.identityservice.model.RoleModel;
import com.platform.identityservice.repository.EmployeeRepository;
import com.platform.identityservice.repository.RoleRepository;
import com.platform.identityservice.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        validateEmailIsAvailable(request.email(), request.storeId(), null);

        EmployeeModel employee = new EmployeeModel();
        employee.setStoreId(request.storeId());
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setPassword(passwordEncoder.encode(request.password()));
        employee.setActive(true);
        employee.setEmployee(true);
        employee.getRoles().add(loadRole());

        return toResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse findById(UUID id) {
        return toResponse(findEmployee(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll(UUID storeId) {
        return employeeRepository.findAllByStoreId(storeId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeResponse update(UUID id, EmployeeUpdateRequest request) {
        EmployeeModel employee = findEmployee(id);

        UUID storeId = nonNull(request.storeId()) ? request.storeId()
                : employee.getStoreId();

        if (isNotEmpty(request.email()) || nonNull(request.storeId())) {
            String email = isNotEmpty(request.email()) ? request.email() : employee.getEmail();
            validateEmailIsAvailable(email, storeId, id);
        }

        mergeEmployee(employee, request);
        return toResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void deactivate(UUID id) {
        EmployeeModel employee = findEmployee(id);
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    private EmployeeModel findEmployee(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private RoleModel loadRole() {
        return roleRepository.findByName(RoleName.STORE_EMPLOYEE)
                .orElseThrow(() -> new IllegalStateException("Role not found: " + RoleName.STORE_EMPLOYEE));
    }

    private void validateEmailIsAvailable(String email, UUID storeId, UUID currentId) {
        boolean exists = isNull(currentId)
                ? employeeRepository.existsByEmailAndStoreId(email, storeId)
                : employeeRepository.existsByEmailAndStoreIdAndIdNot(email, storeId, currentId);

        if (exists) {
            throw new UserAlreadyExistsException("Employee already exists: " + email);
        }
    }

    private void mergeEmployee(EmployeeModel employee, EmployeeUpdateRequest request) {
        if (nonNull(request.storeId())) {
            employee.setStoreId(request.storeId());
        }

        if (isNotEmpty(request.name())) {
            employee.setName(request.name());
        }

        if (isNotEmpty(request.email())) {
            employee.setEmail(request.email());
        }
    }

    private EmployeeResponse toResponse(EmployeeModel employee) {
        Set<String> roles = employee.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        return new EmployeeResponse(
                employee.getId(),
                employee.getStoreId(),
                employee.getName(),
                employee.getEmail(),
                employee.isActive(),
                roles,
                employee.getCreationTime(),
                employee.getModifiedTime()
        );
    }

    private UserCredentialsResponse toCredentialsResponse(EmployeeModel employee) {
        Set<String> roles = employee.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        return new UserCredentialsResponse(
                employee.getId(),
                employee.getStoreId(),
                employee.getEmail(),
                employee.getName(),
                roles
        );
    }
}
