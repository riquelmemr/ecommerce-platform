package com.platform.authservice.service.auth.impl;

import com.platform.authservice.client.IdentityClient;
import com.platform.authservice.dto.request.LoginRequest;
import com.platform.authservice.dto.response.EmployeeResponse;
import com.platform.authservice.dto.response.LoginResponse;
import com.platform.authservice.enums.UserType;
import com.platform.authservice.service.auth.AuthService;
import com.platform.authservice.service.jwt.JwtService;
import com.platform.authservice.service.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeAuthServiceImpl implements AuthService {

    private final IdentityClient identityClient;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse login(LoginRequest request) {
        final EmployeeResponse customer = identityClient.validateEmployeeCredentials(request);

        final String accessToken = jwtService.generateAccessToken(
                customer.id(),
                customer.storeId(),
                customer.roles(),
                getUserType()
        );

        final String refreshToken = refreshTokenService.create(
                customer.id(),
                customer.storeId(),
                getUserType()
        );

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public UserType getUserType() {
        return UserType.EMPLOYEE;
    }
}
