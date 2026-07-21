package com.platform.identityservice.service.user.impl;

import com.platform.identityservice.dto.internal.ValidateCredentialsRequest;
import com.platform.identityservice.dto.internal.UserCredentialsResponse;
import com.platform.identityservice.enums.UserType;
import com.platform.identityservice.exception.InvalidCredentialsException;
import com.platform.identityservice.exception.UserNotFoundException;
import com.platform.identityservice.model.UserModel;
import com.platform.identityservice.repository.UserRepository;
import com.platform.identityservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserCredentialsResponse validateCredentials(ValidateCredentialsRequest request) {
        final UserModel user = findUser(request);

        validateUserCredentials(user, request.password(), request.userType());

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        log.info("Credentials successfully validated for user [{}]", user.getEmail());

        return new UserCredentialsResponse(
                user.getId(),
                user.getStoreId(),
                user.getEmail(),
                user.getName(),
                roles
        );
    }

    private UserModel findUser(ValidateCredentialsRequest request) {
        if (isCustomerRequestType(request) && nonNull(request.storeId())) {
            return userRepository.findByEmailAndStoreId(request.email(), request.storeId())
                    .orElseThrow(() -> new UserNotFoundException("Invalid credentials"));
        }

        return userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("Invalid credentials"));
    }

    private void validateUserCredentials(UserModel user, String rawPassword, UserType userType) {
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            log.error("Incorrect password for the user [{}]", user.getEmail());
            throw new InvalidCredentialsException();
        }

        if (!user.isActive()) {
            log.error("User [{}] is not active to perform login", user.getEmail());
            throw new InvalidCredentialsException();
        }

        if (!user.isEmployee() && UserType.EMPLOYEE.equals(userType)) {
            log.error("Customer [{}] attempted to login using employee authentication", user.getEmail());
            throw new InvalidCredentialsException();
        }

        if (user.isEmployee() && UserType.CUSTOMER.equals(userType)) {
            log.error("Employee [{}] attempted to login using customer authentication", user.getEmail());
            throw new InvalidCredentialsException();
        }
    }

    private boolean isCustomerRequestType(ValidateCredentialsRequest request) {
        return UserType.CUSTOMER.equals(request.userType());
    }
}
