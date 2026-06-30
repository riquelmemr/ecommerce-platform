package com.platform.authservice.repository;

import com.platform.authservice.model.RefreshTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, UUID> {

    Optional<RefreshTokenModel> findByToken(String token);

}
