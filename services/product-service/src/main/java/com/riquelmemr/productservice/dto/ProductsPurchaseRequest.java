package com.riquelmemr.productservice.dto;

import java.util.List;

public record ProductsPurchaseRequest(

        List<ProductPurchaseRequest> products

) {
}
