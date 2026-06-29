package com.platform.storeservice.dto.request;

import com.platform.storeservice.annotation.HexColor;

public record BaseStoreUpdateRequest(
        String code,
        String name,
        String logoUrl,
        @HexColor
        String primaryColor,
        Boolean isActive,
        String domain
) {
}
