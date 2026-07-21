package com.platform.authservice.service.jwt.impl;

import com.platform.authservice.config.JwtProperties;
import com.platform.authservice.enums.TokenType;
import com.platform.authservice.enums.UserType;
import com.platform.authservice.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final String STORE_ID_CLAIM = "store_id";
    private static final String USER_TYPE_CLAIM = "user_type";
    private static final String ROLES_CLAIM = "roles";
    private static final String TYPE_CLAIM = "type";
    private static final String TYPE_REFRESH_CLAIM_VALUE = "refresh";

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final JwtProperties jwtProperties;

    @Override
    public String generateAccessToken(UUID userId, UUID storeId, List<String> roles, UserType userType) {
        JwtBuilder builder = Jwts.builder()
                .subject(userId.toString())
                .claim(ROLES_CLAIM, roles)
                .claim(USER_TYPE_CLAIM, userType)
                .issuedAt(new Date())
                .expiration(getExpirationDate(TokenType.ACCESS_TOKEN))
                .signWith(privateKey, Jwts.SIG.RS256);

        if (nonNull(storeId)) {
            builder.claim(STORE_ID_CLAIM, storeId.toString());
        }

        return builder.compact();
    }

    @Override
    public String generateRefreshToken(UUID userId, UUID storeId, UserType userType) {
        JwtBuilder builder = Jwts.builder()
                .subject(userId.toString())
                .claim(TYPE_CLAIM, TYPE_REFRESH_CLAIM_VALUE)
                .claim(USER_TYPE_CLAIM, userType)
                .issuedAt(new Date())
                .expiration(getExpirationDate(TokenType.REFRESH_TOKEN))
                .signWith(privateKey, Jwts.SIG.RS256);

        if (nonNull(storeId)) {
            builder.claim(STORE_ID_CLAIM, storeId.toString());
        }

        return builder.compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public UUID extractCustomerId(String token) {
        return UUID.fromString(getClaims(token).getSubject());
    }

    @Override
    public UUID extractStoreId(String token) {
        return UUID.fromString(getClaims(token).get(STORE_ID_CLAIM, String.class));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return (List<String>) getClaims(token).get(ROLES_CLAIM, List.class);
    }

    @Override
    public UserType extractUserType(String token) {
        return UserType.valueOf(getClaims(token).get(USER_TYPE_CLAIM, String.class));
    }

    private Date getExpirationDate(TokenType tokenType) {
        long expiration = TokenType.ACCESS_TOKEN.equals(tokenType)
                ? jwtProperties.getAccessToken().getExpiration()
                : jwtProperties.getRefreshToken().getExpiration();

        return new Date(System.currentTimeMillis() + expiration);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
