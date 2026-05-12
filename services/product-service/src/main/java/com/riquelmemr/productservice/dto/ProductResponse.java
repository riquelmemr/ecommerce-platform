package com.riquelmemr.productservice.dto;

import java.math.BigDecimal;
import java.util.Set;

public record ProductResponse(
        Long id,
        String name,
        String description,
        double stock,
        double reserved,
        BigDecimal price,
        Set<CategoryResponse> categories
) {
}