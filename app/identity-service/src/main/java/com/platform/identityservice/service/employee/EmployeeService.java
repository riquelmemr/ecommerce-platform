package com.platform.identityservice.service.employee;

import com.platform.identityservice.dto.employee.EmployeeRequest;
import com.platform.identityservice.dto.employee.EmployeeResponse;
import com.platform.identityservice.dto.employee.EmployeeUpdateRequest;
import com.platform.identityservice.model.EmployeeModel;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    EmployeeResponse create(EmployeeRequest request);

    EmployeeResponse findById(UUID id);

    List<EmployeeResponse> findAll(UUID storeId);

    EmployeeResponse update(UUID id, EmployeeUpdateRequest request);

    void deactivate(UUID id);

}
