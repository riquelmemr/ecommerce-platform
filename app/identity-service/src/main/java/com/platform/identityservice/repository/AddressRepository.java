package com.platform.identityservice.repository;

import com.platform.identityservice.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressModel, UUID> {

    List<AddressModel> findAllByUserIdAndStoreId(UUID userId, UUID storeId);

    Optional<AddressModel> findByIdAndUserId(UUID id, UUID userId);
}
