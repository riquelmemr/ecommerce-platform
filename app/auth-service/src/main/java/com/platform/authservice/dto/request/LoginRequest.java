package com.platform.authservice.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LoginRequest(
        UUID storeId,
        @NotNull
        String email,
        @NotNull
        String password
) {
}
