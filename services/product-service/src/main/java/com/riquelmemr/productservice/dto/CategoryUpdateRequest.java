package com.riquelmemr.productservice.dto;

public record CategoryUpdateRequest(

        String code,

        String name,

        String description
) {
}
