package com.riquelmemr.productservice.repository;

import com.riquelmemr.productservice.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findByIdIn(List<Long> productIds);

}
