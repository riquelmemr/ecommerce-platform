package com.platform.identityservice.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EmployeeRequest(

        @NotNull
        UUID storeId,

        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
