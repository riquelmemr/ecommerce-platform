package com.riquelmemr.productservice.converter;

import com.riquelmemr.productservice.dto.CategoryRequest;
import com.riquelmemr.productservice.model.CategoryModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class CategoryModelConverter implements Converter<CategoryRequest, CategoryModel> {

    @Override
    public CategoryModel convert(CategoryRequest source) {
        if (isNull(source)) {
            return null;
        }

        return CategoryModel.builder()
                .withCode(source.code())
                .withName(source.name())
                .withDescription(source.description())
                .build();
    }
}
