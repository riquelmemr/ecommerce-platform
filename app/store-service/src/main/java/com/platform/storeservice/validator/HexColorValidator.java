package com.platform.storeservice.validator;

import com.platform.storeservice.annotation.HexColor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class HexColorValidator implements ConstraintValidator<HexColor, String> {

    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(value)) {
            return true;
        }

        return value.matches(HEX_PATTERN);
    }
}
