package com.platform.authservice.dto.response;

import java.util.List;
import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        UUID storeId,
        String email,
        List<String> roles
) {
}
