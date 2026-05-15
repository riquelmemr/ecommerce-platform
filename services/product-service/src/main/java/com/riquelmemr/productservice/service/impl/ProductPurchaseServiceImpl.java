package com.riquelmemr.productservice.service.impl;

import com.riquelmemr.productservice.data.ProductPurchaseData;
import com.riquelmemr.productservice.dto.ProductPurchaseRequest;
import com.riquelmemr.productservice.dto.ProductPurchaseResponse;
import com.riquelmemr.productservice.dto.ProductsPurchaseRequest;
import com.riquelmemr.productservice.dto.ProductsPurchaseResponse;
import com.riquelmemr.productservice.exception.ProductsNotFoundException;
import com.riquelmemr.productservice.model.ProductModel;
import com.riquelmemr.productservice.service.ProductPurchaseService;
import com.riquelmemr.productservice.service.ProductService;
import com.riquelmemr.productservice.service.StockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPurchaseServiceImpl implements ProductPurchaseService {

    private final ProductService productService;
    private final StockService stockService;
    private final Converter<ProductPurchaseData, ProductPurchaseResponse> productPurchaseResponseConverter;

    @Override
    @Transactional
    public ProductsPurchaseResponse purchase(ProductsPurchaseRequest request) {
        List<ProductPurchaseRequest> productPurchases = request.products();

        Map<Long, Integer> productQuantityMap = productPurchases.stream()
                .collect(Collectors.toMap(
                        ProductPurchaseRequest::productId,
                        ProductPurchaseRequest::quantity
                ));

        List<Long> productIds = new ArrayList<>(productQuantityMap.keySet());
        List<ProductModel> products = productService.findByIds(productIds);

        validateProducts(products, productIds);

        List<ProductPurchaseResponse> productsResponse =  products.stream()
                .map(product -> reserveProduct(product, productQuantityMap.get(product.getId())))
                .map(productPurchaseResponseConverter::convert)
                .toList();

        return new ProductsPurchaseResponse(productsResponse);
    }

    private ProductPurchaseData reserveProduct(ProductModel product, Integer quantityToReserve) {
        stockService.reserveProduct(product, quantityToReserve);
        return new ProductPurchaseData(product, quantityToReserve);
    }

    private void validateProducts(List<ProductModel> products, List<Long> requestedIds) {
        Set<Long> foundIds = products.stream()
                .map(ProductModel::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = requestedIds.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new ProductsNotFoundException(missingIds);
        }
    }
}
