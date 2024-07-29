package com.gestion.almacenes.commons.exception;

public class ValidationErrorException extends RuntimeException {

  public ValidationErrorException(String messages) {
    super(String.format("%s", messages));
  }
}