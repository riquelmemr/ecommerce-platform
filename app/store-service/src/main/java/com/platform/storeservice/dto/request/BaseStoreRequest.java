package com.platform.storeservice.dto.request;

import com.platform.storeservice.annotation.Domain;
import com.platform.storeservice.annotation.HexColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BaseStoreRequest(
        @NotBlank String code,
        @NotBlank String name,
        String logoUrl,
        @HexColor
        String primaryColor,
        @NotNull Boolean isActive,
        @Domain
        String domain
) {
}
