package com.riquelmemr.productservice.converter;

import com.riquelmemr.productservice.data.ProductData;
import com.riquelmemr.productservice.model.ProductModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductModelConverter implements Converter<ProductData, ProductModel> {

    @Override
    public ProductModel convert(ProductData source) {
        return ProductModel.builder()
                .withId(source.getId())
                .withName(source.getName())
                .withCategories(source.getCategories())
                .withPrice(source.getPrice())
                .withStock(source.getStock())
                .withReserved(source.getReserved())
                .withDescription(source.getDescription())
                .build();
    }
}
