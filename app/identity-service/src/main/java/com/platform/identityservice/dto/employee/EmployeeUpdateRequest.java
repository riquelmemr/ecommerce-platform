package com.platform.identityservice.dto.employee;

import java.util.UUID;

public record EmployeeUpdateRequest(
        UUID storeId,
        String name,
        String email
) {
}
