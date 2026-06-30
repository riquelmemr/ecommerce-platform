package com.platform.authservice.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
