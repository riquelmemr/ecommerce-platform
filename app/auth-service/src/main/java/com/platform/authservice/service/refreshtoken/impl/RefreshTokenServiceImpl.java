package com.platform.authservice.service.refreshtoken.impl;

import com.platform.authservice.config.JwtProperties;
import com.platform.authservice.dto.response.LoginResponse;
import com.platform.authservice.enums.UserType;
import com.platform.authservice.exception.InvalidRefreshTokenException;
import com.platform.authservice.exception.RefreshTokenException;
import com.platform.authservice.model.RefreshTokenModel;
import com.platform.authservice.repository.RefreshTokenRepository;
import com.platform.authservice.service.jwt.JwtService;
import com.platform.authservice.service.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public String create(UUID customerId, UUID storeId, UserType userType) {
        try {
            log.info("Generating refresh token for user [{}]", customerId);

            String token = jwtService.generateRefreshToken(customerId, storeId, userType);

            RefreshTokenModel refreshToken = new RefreshTokenModel();

            refreshToken.setUserId(customerId);
            refreshToken.setStoreId(storeId);
            refreshToken.setUserType(userType);
            refreshToken.setToken(token);
            refreshToken.setExpiresAt(getExpiresAt());

            refreshTokenRepository.save(refreshToken);

            log.info("Refresh token successfully generated for user [{}]", customerId);
            return token;
        } catch (Exception e) {
            String msg = String.format("Failed to generated refresh token for user [%s]", customerId);
            throw new RefreshTokenException(msg, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse refresh(String token) {
        RefreshTokenModel refreshToken = findByToken(token);

        log.info("Initiating refresh token update for user [{}]", refreshToken.getUserId());

        try {
            if (isExpiredOrRevoked(refreshToken)) {
                throw new InvalidRefreshTokenException("Refresh token expired or revoked.");
            }

            UserType userType = jwtService.extractUserType(token);
            List<String> roles = jwtService.extractRoles(token);

            String newAccessToken = jwtService.generateAccessToken(
                    refreshToken.getUserId(),
                    refreshToken.getStoreId(),
                    roles,
                    userType
            );

            return new LoginResponse(newAccessToken, refreshToken.getToken());
        } catch (Exception e) {
            String msg = String.format("Failed to update refresh token for user [%s]", refreshToken.getUserId());
            throw new RefreshTokenException(msg, e);
        }
    }

    @Override
    @Transactional
    public void logout(String token) {
        RefreshTokenModel refreshToken = findByToken(token);

        log.info("Revoking refresh token for user [{}]", refreshToken.getUserId());

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        log.info("Refresh token successfully revoked for user [{}]", refreshToken.getUserId());
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
