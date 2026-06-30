package com.platform.authservice.factory.impl;

import com.platform.authservice.enums.UserType;
import com.platform.authservice.factory.Factory;
import com.platform.authservice.service.auth.AuthService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthServiceFactoryImpl implements Factory<AuthService, UserType> {

    private final Map<UserType, AuthService> services;

    public AuthServiceFactoryImpl(List<AuthService> services) {
        this.services = services.stream()
                .collect(Collectors.toUnmodifiableMap(
                        AuthService::getUserType, Function.identity()));
    }

    @Override
    public AuthService getObject(UserType parameter) {
        return Optional.ofNullable(services.get(parameter))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No AuthService found for user type: " + parameter
                        ));
    }

}
