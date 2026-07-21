package com.platform.identityservice.repository;

import com.platform.identityservice.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    boolean existsByEmailAndStoreId(String email, UUID storeId);

    boolean existsByEmailAndStoreIdAndIdNot(String email, UUID storeId, UUID id);

    List<CustomerModel> findAllByStoreId(UUID storeId);

}
