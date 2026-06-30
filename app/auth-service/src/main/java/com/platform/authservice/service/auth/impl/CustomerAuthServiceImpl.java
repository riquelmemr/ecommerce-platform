package com.platform.authservice.service.auth.impl;

import com.platform.authservice.client.IdentityClient;
import com.platform.authservice.dto.request.LoginRequest;
import com.platform.authservice.dto.response.CustomerResponse;
import com.platform.authservice.dto.response.LoginResponse;
import com.platform.authservice.enums.UserType;
import com.platform.authservice.service.auth.AuthService;
import com.platform.authservice.service.jwt.JwtService;
import com.platform.authservice.service.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerAuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final IdentityClient identityClient;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse login(LoginRequest request) {
        final CustomerResponse customer = identityClient.validateCustomerCredentials(request);

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
        return UserType.CUSTOMER;
    }
}
