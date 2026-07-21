package com.platform.identityservice.dto.internal;

import com.platform.identityservice.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ValidateCredentialsRequest(

        @NotBlank
        String email,

        @NotBlank
        String password,

        UUID storeId,

        @NotNull
        UserType userType
) {
}
