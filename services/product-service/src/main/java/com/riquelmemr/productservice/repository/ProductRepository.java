package com.riquelmemr.productservice.repository;

import com.riquelmemr.productservice.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
