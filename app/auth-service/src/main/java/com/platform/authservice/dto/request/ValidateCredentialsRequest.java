package com.platform.authservice.dto.request;

import com.platform.authservice.enums.UserType;

import java.util.UUID;

public record ValidateCredentialsRequest(
        UUID storeId,
        String email,
        String password,
        UserType userType
) {
}
