package com.platform.identityservice.dto.customer;

import java.util.Set;
import java.util.UUID;

public record CustomerResponse(
        UUID id,
        UUID storeId,
        String name,
        String email,
        boolean active,
        Set<String> roles
) {
}
