package com.platform.identityservice.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(

        @NotBlank
        String storeCode,

        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
