package com.platform.authservice.service.refreshtoken;

import com.platform.authservice.dto.response.LoginResponse;
import com.platform.authservice.enums.UserType;

import java.util.UUID;

public interface RefreshTokenService {

    String create(final UUID customerId, final UUID storeId, final UserType userType);

    LoginResponse refresh(final String token);

    void logout(final String token);

}
