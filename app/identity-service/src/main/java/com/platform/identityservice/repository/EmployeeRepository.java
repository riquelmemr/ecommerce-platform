package com.platform.identityservice.repository;

import com.platform.identityservice.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, UUID> {

    Optional<EmployeeModel> findByEmail(String email);

    boolean existsByEmailAndStoreId(String email, UUID storeId);

    boolean existsByEmailAndStoreIdAndIdNot(String email, UUID storeId, UUID id);

    List<EmployeeModel> findAllByStoreId(UUID storeId);

}
