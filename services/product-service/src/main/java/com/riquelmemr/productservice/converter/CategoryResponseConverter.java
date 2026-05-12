package com.riquelmemr.productservice.converter;

import com.riquelmemr.productservice.dto.CategoryResponse;
import com.riquelmemr.productservice.model.CategoryModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryResponseConverter implements Converter<CategoryModel, CategoryResponse> {

    @Override
    public CategoryResponse convert(CategoryModel source) {
        return new CategoryResponse(
                source.getId(),
                source.getCode(),
                source.getName(),
                source.getDescription()
        );
    }
}
