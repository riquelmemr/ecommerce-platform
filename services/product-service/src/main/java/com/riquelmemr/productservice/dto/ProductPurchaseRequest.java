package com.riquelmemr.productservice.dto;

public record ProductPurchaseRequest(

        Long productId,

        int quantity
) {
}
