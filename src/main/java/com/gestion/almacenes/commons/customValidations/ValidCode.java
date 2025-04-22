package com.gestion.almacenes.commons.customValidations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidCodeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ValidCode {

  String message() default "{custom.validation.code}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}


