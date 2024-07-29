package com.gestion.almacenes.commons.customValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidCodeValidator implements ConstraintValidator<ValidCode, String> {

  private static Pattern pattern = Pattern.compile("^[a-zA-Z0-9_/-]*$");

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return pattern.matcher(value).find();
  }

}

