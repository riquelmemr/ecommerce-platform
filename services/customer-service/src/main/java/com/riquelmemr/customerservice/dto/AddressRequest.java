package com.riquelmemr.customerservice.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(

        @NotBlank(message = "Address name is required")
        String name,

        @NotBlank(message = "Zip code is required")
        String zipCode,

        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "Street is required")
        String neighborhood,

        @NotBlank(message = "Number is required")
        String number,

        String complement,

        @NotBlank(message = "District is required")
        String district,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Country is required")
        String country
) {
}
