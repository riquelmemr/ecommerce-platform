package com.riquelmemr.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Set;

public record ProductRequest(

        @NotNull(message = "Product name is required")
        String name,

        String description,

        @Positive(message = "Stock should be positive")
        double stock,

        double reserved,

        @Positive(message = "Price should be positive")
        BigDecimal price,

        @NotEmpty
        Set<String> categoryCodes
) {
}
