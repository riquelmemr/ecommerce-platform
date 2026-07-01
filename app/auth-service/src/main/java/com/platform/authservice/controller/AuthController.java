package com.platform.authservice.controller;

import com.platform.authservice.dto.request.LoginRequest;
import com.platform.authservice.dto.request.TokenRequest;
import com.platform.authservice.dto.response.LoginResponse;
import com.platform.authservice.enums.UserType;
import com.platform.authservice.factory.Factory;
import com.platform.authservice.service.auth.AuthService;
import com.platform.authservice.service.refreshtoken.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Factory<AuthService, UserType> authServiceFactory;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login/customer")
    public ResponseEntity<LoginResponse> loginCustomer(@Valid @RequestBody final LoginRequest request) {
        LoginResponse response = getAuthService(UserType.CUSTOMER).login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/employee")
    public ResponseEntity<LoginResponse> loginEmployee(@Valid @RequestBody final LoginRequest request) {
        LoginResponse response = getAuthService(UserType.EMPLOYEE).login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody final TokenRequest request) {
        LoginResponse response = refreshTokenService.refresh(request.token());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody final TokenRequest request) {
        refreshTokenService.logout(request.token());
        return ResponseEntity.ok().build();
    }

    private AuthService getAuthService(final UserType userType) {
        return authServiceFactory.getObject(userType);
    }
}
