package com.platform.authservice.client;

import com.platform.authservice.dto.request.LoginRequest;
import com.platform.authservice.dto.response.CustomerResponse;
import com.platform.authservice.dto.response.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "identity-service", path = "/api/v1")
public interface IdentityClient {

    @PostMapping("/customers/validate-credentials")
    CustomerResponse validateCustomerCredentials(LoginRequest loginRequest);

    @PostMapping("/employees/validate-credentials")
    EmployeeResponse validateEmployeeCredentials(LoginRequest loginRequest);
}
