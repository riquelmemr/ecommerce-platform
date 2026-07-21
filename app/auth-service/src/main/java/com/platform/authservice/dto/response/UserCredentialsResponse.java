package com.platform.authservice.dto.response;

import java.util.List;
import java.util.UUID;

public record UserCredentialsResponse(
        UUID id,
        UUID storeId,
        String email,
        String name,
        List<String> roles
) {
}
