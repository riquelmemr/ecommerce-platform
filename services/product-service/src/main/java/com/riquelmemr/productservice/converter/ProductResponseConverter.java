package com.riquelmemr.productservice.converter;

import com.riquelmemr.productservice.dto.CategoryResponse;
import com.riquelmemr.productservice.dto.ProductResponse;
import com.riquelmemr.productservice.model.CategoryModel;
import com.riquelmemr.productservice.model.ProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductResponseConverter implements Converter<ProductModel, ProductResponse> {

    private final Converter<CategoryModel, CategoryResponse> categoryResponseConverter;

    @Override
    public ProductResponse convert(ProductModel source) {
        Set<CategoryResponse> categories = source.getCategories()
                .stream()
                .map(categoryResponseConverter::convert)
                .collect(Collectors.toSet());

        return new ProductResponse(
                source.getId(),
                source.getName(),
                source.getDescription(),
                source.getStock(),
                source.getReserved(),
                source.getPrice(),
                categories
        );
    }
}
