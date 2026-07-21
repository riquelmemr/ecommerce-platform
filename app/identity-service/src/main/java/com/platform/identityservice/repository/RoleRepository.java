package com.platform.identityservice.repository;

import com.platform.identityservice.model.RoleModel;
import com.platform.identityservice.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByName(RoleName name);
}
