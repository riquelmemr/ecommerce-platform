package com.riquelmemr.productservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(

        @NotBlank(message = "Category code is required")
        String code,

        @NotBlank(message = "Category name is required")
        String name,

        String description
) {
}
