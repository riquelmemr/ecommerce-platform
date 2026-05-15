package com.riquelmemr.productservice.service;

import com.riquelmemr.productservice.dto.ProductRequest;
import com.riquelmemr.productservice.dto.ProductResponse;
import com.riquelmemr.productservice.dto.ProductUpdateRequest;
import com.riquelmemr.productservice.model.ProductModel;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    ProductResponse update(Long id, ProductUpdateRequest request);

    List<ProductModel> findByIds(List<Long> productIds);

    void delete(Long id);
}
