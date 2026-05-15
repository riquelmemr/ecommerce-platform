package com.riquelmemr.productservice.dto;

import java.util.List;

public record ProductsPurchaseResponse(

        List<ProductPurchaseResponse> products

) {
}
