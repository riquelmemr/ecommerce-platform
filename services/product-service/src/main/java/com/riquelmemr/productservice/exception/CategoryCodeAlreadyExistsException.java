package com.riquelmemr.productservice.exception;

public class CategoryCodeAlreadyExistsException extends RuntimeException {

    public CategoryCodeAlreadyExistsException(String code) {
        super("Category code already exists: " + code);
    }
}
