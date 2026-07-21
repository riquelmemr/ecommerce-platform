package com.platform.storeservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseStoreResponse(
        UUID id,
        String code,
        String name,
        String logoUrl,
        String primaryColor,
        boolean isActive,
        String domain
) {
}
