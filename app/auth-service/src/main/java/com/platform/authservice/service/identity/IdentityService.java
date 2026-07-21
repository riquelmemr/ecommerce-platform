package com.platform.authservice.service.identity;

import com.platform.authservice.dto.request.LoginRequest;
import com.platform.authservice.dto.response.UserCredentialsResponse;
import com.platform.authservice.enums.UserType;

public interface IdentityService {

    UserCredentialsResponse validateCredentials(LoginRequest loginRequest, UserType userType);
}
