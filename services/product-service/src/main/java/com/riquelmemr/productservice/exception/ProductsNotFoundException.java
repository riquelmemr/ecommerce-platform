package com.riquelmemr.productservice.exception;

import java.util.Set;

import static com.riquelmemr.productservice.utils.StringUtils.convertLongToString;

public class ProductsNotFoundException extends RuntimeException {

    public ProductsNotFoundException(Set<Long> missingIds) {
        super("Products not found: " + String.join(", ", convertLongToString(missingIds)));
    }
}
