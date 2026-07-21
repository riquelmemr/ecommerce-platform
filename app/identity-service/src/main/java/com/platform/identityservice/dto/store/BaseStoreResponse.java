package com.platform.identityservice.dto.store;

import java.time.LocalDateTime;
import java.util.UUID;

public record BaseStoreResponse(
        UUID id,
        String code,
        String name,
        String logoUrl,
        String primaryColor,
        boolean isActive,
        String domain,
        LocalDateTime creationTime,
        LocalDateTime modifiedTime
) {
}
