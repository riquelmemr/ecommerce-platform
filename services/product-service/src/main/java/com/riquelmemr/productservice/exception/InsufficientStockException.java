package com.riquelmemr.productservice.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Long productId, int requested, int available) {
        super("Insufficient stock for product " + productId + ". Requested: " + requested + ", available: " + available);
    }
}
