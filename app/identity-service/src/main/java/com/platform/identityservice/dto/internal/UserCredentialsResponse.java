package com.platform.identityservice.dto.internal;

import java.util.Set;
import java.util.UUID;

public record UserCredentialsResponse(
        UUID id,
        UUID storeId,
        String email,
        String name,
        Set<String> roles
) {
}
