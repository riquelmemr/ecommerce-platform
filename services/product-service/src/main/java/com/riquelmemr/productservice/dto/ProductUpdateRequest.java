package com.riquelmemr.productservice.dto;

import java.math.BigDecimal;
import java.util.Set;

public record ProductUpdateRequest(

        String name,

        String description,

        Double stock,

        Double reserved,

        BigDecimal price,

        Set<String> categoryCodes
) {
}