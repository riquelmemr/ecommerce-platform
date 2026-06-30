package com.platform.authservice.service.jwt;

import com.platform.authservice.enums.UserType;

import java.util.List;
import java.util.UUID;

public interface JwtService {

    String generateAccessToken(final UUID customerId, final UUID storeId, final List<String> roles, final UserType userType);

    String generateRefreshToken(final UUID customerId, final UUID storeId, final UserType userType);

    boolean validateToken(final String token);

    UUID extractCustomerId(final String token);

    UUID extractStoreId(final String token);

    List<String> extractRoles(final String token);

    UserType extractUserType(final String token);

}
