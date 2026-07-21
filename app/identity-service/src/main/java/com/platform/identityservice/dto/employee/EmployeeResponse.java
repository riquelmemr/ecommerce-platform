package com.platform.identityservice.dto.employee;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        UUID storeId,
        String name,
        String email,
        boolean isActive,
        Set<String> roles,
        LocalDateTime creationTime,
        LocalDateTime modifiedTime
) {
}
