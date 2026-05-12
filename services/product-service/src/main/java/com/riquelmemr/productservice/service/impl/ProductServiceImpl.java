package com.riquelmemr.productservice.service.impl;

import com.riquelmemr.productservice.data.ProductData;
import com.riquelmemr.productservice.dto.ProductRequest;
import com.riquelmemr.productservice.dto.ProductResponse;
import com.riquelmemr.productservice.dto.ProductUpdateRequest;
import com.riquelmemr.productservice.exception.ProductNotFoundException;
import com.riquelmemr.productservice.model.CategoryModel;
import com.riquelmemr.productservice.model.ProductModel;
import com.riquelmemr.productservice.repository.ProductRepository;
import com.riquelmemr.productservice.service.CategoryService;
import com.riquelmemr.productservice.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final Converter<ProductModel, ProductResponse> productResponseConverter;
    private final Converter<ProductData, ProductModel> productModelConverter;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Set<CategoryModel> categories = categoryService.getByCodes(request.categoryCodes());

        ProductData productData = new ProductData(request);
        productData.setCategories(categories);

        ProductModel product = productModelConverter.convert(productData);
        ProductModel productCreated =productRepository.save(product);

        return productResponseConverter.convert(productCreated);
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productResponseConverter::convert)
                .toList();
    }

    @Override
    public ProductResponse findById(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return productResponseConverter.convert(product);
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        ProductModel product = findProductById(id);

        mergeProduct(product, request);
        productRepository.save(product);

        return productResponseConverter.convert(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductModel product = findProductById(id);
        productRepository.delete(product);
    }

    private ProductModel findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private void mergeProduct(ProductModel product, ProductUpdateRequest request) {
        if (nonNull(request.name())) {
            product.setName(request.name());
        }

        if (nonNull(request.description())) {
            product.setDescription(request.description());
        }

        if (nonNull(request.stock())) {
            product.setStock(request.stock());
        }

        if (nonNull(request.reserved())) {
            product.setReserved(request.reserved());
        }

        if (nonNull(request.price())) {
            product.setPrice(request.price());
        }

        if (nonNull(request.categoryCodes())) {
            Set<CategoryModel> categories = categoryService.getByCodes(request.categoryCodes());
            product.setCategories(categories);
        }
    }
}
