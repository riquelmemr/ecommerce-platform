package com.platform.authservice.dto.response;

import java.util.List;
import java.util.UUID;

public record CustomerResponse(
        UUID id,
        UUID storeId,
        String email,
        List<String> roles
) {
}
