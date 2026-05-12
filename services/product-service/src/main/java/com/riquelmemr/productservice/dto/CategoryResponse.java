package com.riquelmemr.productservice.dto;

public record CategoryResponse(
        Long id,
        String code,
        String name,
        String description
) {
}
