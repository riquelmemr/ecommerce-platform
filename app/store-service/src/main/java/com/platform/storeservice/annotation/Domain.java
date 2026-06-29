package com.platform.storeservice.annotation;

import com.platform.storeservice.validator.DomainValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DomainValidator.class)
public @interface Domain {
    String message() default "Invalid domain format (example: example.com)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
