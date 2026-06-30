package com.platform.authservice.service.refreshtoken.impl;

import com.platform.authservice.config.JwtProperties;
import com.platform.authservice.dto.response.LoginResponse;
import com.platform.authservice.enums.UserType;
import com.platform.authservice.exception.InvalidRefreshTokenException;
import com.platform.authservice.model.RefreshTokenModel;
import com.platform.authservice.repository.RefreshTokenRepository;
import com.platform.authservice.service.jwt.JwtService;
import com.platform.authservice.service.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public String create(UUID customerId, UUID storeId, UserType userType) {
        final String token = jwtService.generateRefreshToken(customerId, storeId, userType);

        final RefreshTokenModel refreshToken = new RefreshTokenModel();

        refreshToken.setUserId(customerId);
        refreshToken.setStoreId(storeId);
        refreshToken.setUserType(userType);
        refreshToken.setToken(token);
        refreshToken.setExpiresAt(getExpiresAt());

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse refresh(String token) {
        final RefreshTokenModel refreshToken = findByToken(token);

        if (isExpiredOrRevoked(refreshToken)) {
            throw new InvalidRefreshTokenException("Refresh token expired or revoked.");
        }

        final UserType userType = jwtService.extractUserType(token);
        final List<String> roles = jwtService.extractRoles(token);

        final String newAccessToken = jwtService.generateAccessToken(
                refreshToken.getUserId(),
                refreshToken.getStoreId(),
                roles,
                userType
        );

        return new LoginResponse(newAccessToken, refreshToken.getToken());
    }

    @Override
    @Transactional
    public void logout(String token) {
        final RefreshTokenModel refreshToken = findByToken(token);

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    private RefreshTokenModel findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found."));
    }

    private boolean isExpiredOrRevoked(RefreshTokenModel refreshToken) {
        return refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(LocalDateTime.now());
    }

    private LocalDateTime getExpiresAt() {
        final long expiration = jwtProperties.getRefreshToken().getExpiration();
        return LocalDateTime.now().plus(expiration, ChronoUnit.MILLIS);
    }
}
