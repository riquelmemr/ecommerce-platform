package com.platform.identityservice.repository;

import com.platform.identityservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByEmailAndStoreId(String email, UUID storeId);

    Optional<UserModel> findByEmail(String email);

}
