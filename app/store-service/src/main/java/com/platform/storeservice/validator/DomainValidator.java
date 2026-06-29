package com.platform.storeservice.validator;

import com.platform.storeservice.annotation.Domain;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class DomainValidator implements ConstraintValidator<Domain, String> {

    private static final String DOMAIN_PATTERN = "^(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\\.(?!-)[A-Za-z0-9-]{1,63}(?<!-))*\\.[A-Za-z]{2,}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(value)) {
            return true;
        }

        return value.matches(DOMAIN_PATTERN);
    }
}
