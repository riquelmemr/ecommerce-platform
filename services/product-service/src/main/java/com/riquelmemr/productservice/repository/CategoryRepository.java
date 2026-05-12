package com.riquelmemr.productservice.repository;

import com.riquelmemr.productservice.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    List<CategoryModel> findByCodeIn(Set<String> codes);

}
