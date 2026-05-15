package com.riquelmemr.productservice.service;

import com.riquelmemr.productservice.dto.ProductsPurchaseRequest;
import com.riquelmemr.productservice.dto.ProductsPurchaseResponse;

public interface ProductPurchaseService {

    ProductsPurchaseResponse purchase(ProductsPurchaseRequest request);

}
