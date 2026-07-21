package com.platform.authservice.client;

import com.platform.authservice.dto.request.LoginRequest;
import com.platform.authservice.dto.request.ValidateCredentialsRequest;
import com.platform.authservice.dto.response.UserCredentialsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "identity-service")
public interface IdentityClient {

    @PostMapping("/internal/users/validate-credentials")
    UserCredentialsResponse validateCredentials(ValidateCredentialsRequest request);

}
