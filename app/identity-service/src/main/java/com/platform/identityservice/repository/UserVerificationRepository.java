package com.platform.identityservice.repository;

import com.platform.identityservice.model.UserVerificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserVerificationRepository extends JpaRepository<UserVerificationModel, UUID> {

    Optional<UserVerificationModel> findByToken(String token);

    Optional<UserVerificationModel> findByUserId(UUID userId);
}
