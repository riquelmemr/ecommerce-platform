package com.platform.storeservice.repository;

import com.platform.storeservice.model.BaseStoreModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BaseStoreRepository extends JpaRepository<BaseStoreModel, UUID> {

    Optional<BaseStoreModel> findByCode(String code);
}
