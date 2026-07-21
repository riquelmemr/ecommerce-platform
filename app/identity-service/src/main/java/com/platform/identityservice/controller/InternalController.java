package com.platform.identityservice.controller;

import com.platform.identityservice.dto.internal.ValidateCredentialsRequest;
import com.platform.identityservice.dto.internal.UserCredentialsResponse;
import com.platform.identityservice.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalController {

    private final UserService userService;

    @PostMapping("/users/validate-credentials")
    public ResponseEntity<UserCredentialsResponse> validateCredentials(@Valid @RequestBody ValidateCredentialsRequest request) {
        UserCredentialsResponse response = userService.validateCredentials(request);
        return ResponseEntity.ok(response);
    }
}
