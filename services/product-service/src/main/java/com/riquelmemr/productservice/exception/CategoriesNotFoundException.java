package com.riquelmemr.productservice.exception;

import java.util.Set;

public class CategoriesNotFoundException extends RuntimeException {

    public CategoriesNotFoundException(String message) {
        super(message);
    }

    public CategoriesNotFoundException(Set<String> categories) {
        super("Categories not found: " + String.join(", ", categories));
    }
}
