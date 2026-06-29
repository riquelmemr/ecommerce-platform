package com.platform.storeservice.annotation;

import com.platform.storeservice.validator.HexColorValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HexColorValidator.class)
public @interface HexColor {
    String message() default "Invalid hex color format (expected: #RRGGBB)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
