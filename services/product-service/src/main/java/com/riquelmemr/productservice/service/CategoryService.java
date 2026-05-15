package com.riquelmemr.productservice.service;

import com.riquelmemr.productservice.dto.CategoryRequest;
import com.riquelmemr.productservice.dto.CategoryResponse;
import com.riquelmemr.productservice.dto.CategoryUpdateRequest;
import com.riquelmemr.productservice.model.CategoryModel;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> findAll();

    CategoryResponse findById(Long id);

    CategoryResponse update(Long id, CategoryUpdateRequest request);

    void delete(Long id);

    Set<CategoryModel> getByCodes(Set<String> categoryCodes);

}
