package com.riquelmemr.productservice.service;

import com.riquelmemr.productservice.dto.ProductRequest;
import com.riquelmemr.productservice.dto.ProductResponse;
import com.riquelmemr.productservice.dto.ProductUpdateRequest;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    ProductResponse update(Long id, ProductUpdateRequest request);

    void delete(Long id);
}
