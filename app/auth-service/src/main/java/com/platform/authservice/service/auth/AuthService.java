package com.platform.authservice.service.auth;

import com.platform.authservice.dto.request.LoginRequest;
import com.platform.authservice.dto.response.LoginResponse;
import com.platform.authservice.enums.UserType;

public interface AuthService {

    LoginResponse authenticate(LoginRequest request, UserType userType);

}
