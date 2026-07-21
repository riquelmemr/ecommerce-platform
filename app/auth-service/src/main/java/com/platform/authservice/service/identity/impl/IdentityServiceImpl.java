package com.platform.authservice.service.identity.impl;

import com.platform.authservice.client.IdentityClient;
import com.platform.authservice.dto.request.LoginRequest;
import com.platform.authservice.dto.request.ValidateCredentialsRequest;
import com.platform.authservice.dto.response.UserCredentialsResponse;
import com.platform.authservice.enums.UserType;
import com.platform.authservice.service.identity.IdentityService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {

    private final IdentityClient identityClient;

    @Override
    public UserCredentialsResponse validateCredentials(LoginRequest loginRequest, UserType userType) {
        ValidateCredentialsRequest request = new ValidateCredentialsRequest(
                loginRequest.storeId(),
                loginRequest.email(),
                loginRequest.password(),
                userType
        );

        log.info("Sending request to validate credentials for user [{}]", loginRequest.email());

        try {
            return identityClient.validateCredentials(request);
        } catch (FeignException.Unauthorized e) {
            log.error("Invalid credentials for user [{}]", request.email());
            throw e;
        } catch (FeignException.NotFound e) {
            log.error("User not found with e-mail [{}]", request.email());
            throw e;
        }
    }
}
