package com.riquelmemr.productservice.converter;

import com.riquelmemr.productservice.data.ProductPurchaseData;
import com.riquelmemr.productservice.dto.ProductPurchaseResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ProductPurchaseResponseConverter implements Converter<ProductPurchaseData, ProductPurchaseResponse> {

    @Override
    public ProductPurchaseResponse convert(ProductPurchaseData source) {
        if (isNull(source)) {
            return null;
        }

        return new ProductPurchaseResponse(
                source.getProduct().getId(),
                source.getProduct().getName(),
                source.getProduct().getDescription(),
                source.getProduct().getPrice(),
                source.getQuantity()
        );
    }
}
