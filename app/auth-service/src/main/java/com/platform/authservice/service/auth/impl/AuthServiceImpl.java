package com.platform.authservice.service.auth.impl;

import com.platform.authservice.client.IdentityClient;
import com.platform.authservice.dto.request.LoginRequest;
import com.platform.authservice.dto.response.LoginResponse;
import com.platform.authservice.dto.response.UserCredentialsResponse;
import com.platform.authservice.enums.UserType;
import com.platform.authservice.service.auth.AuthService;
import com.platform.authservice.service.identity.IdentityService;
import com.platform.authservice.service.jwt.JwtService;
import com.platform.authservice.service.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final IdentityService identityService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse authenticate(LoginRequest request, UserType userType) {
        log.info("Initiating authentication for user [{}] with type [{}]", request.email(), userType);

        UserCredentialsResponse user = identityService.validateCredentials(request, userType);

        String accessToken = jwtService.generateAccessToken(
                user.id(), user.storeId(), user.roles(), userType
        );

        String refreshToken = refreshTokenService.create(
                user.id(), user.storeId(), userType
        );

        log.info("User [{}] successfully authenticated", user.email());

        return new LoginResponse(accessToken, refreshToken);
    }
}
