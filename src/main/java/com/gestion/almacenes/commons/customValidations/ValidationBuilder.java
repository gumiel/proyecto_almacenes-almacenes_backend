package com.gestion.almacenes.commons.customValidations;

import com.gestion.almacenes.commons.exception.ValidationErrorException;

import java.util.ArrayList;
import java.util.List;

public class ValidationBuilder {

  private final List<String> validationErrors = new ArrayList<>();

  /**
   * Método para inicializar el builder
   * @return ValidationBuilder objeto de la clase
   */
  public static ValidationBuilder create() {
    return new ValidationBuilder();
  }

  /**
   * Validar que no sea nulo
   * @param object Cualquier objeto que pueda ser también nulo
   * @param errorMessage Mensaje de error que se mostrara al cliente
   * @return ValidationBuilder objeto de la clase
   */
  public ValidationBuilder notNull(Object object, String errorMessage) {
    if (object == null)
      validationErrors.add(errorMessage);

    return this;
  }

  /**
   * Validar que no sea nulo y si es nulo lanzar una excepción directamente sin esperar las demás
   * validaciones posteriores
   * @param object Cualquier objeto que pueda ser también nulo
   * @param errorMessage Mensaje de error que se mostrara al cliente
   */
  public void notNullStrict(Object object, String errorMessage) {
    if (object == null)
      validationErrors.add(errorMessage);
    throw new ValidationErrorException(String.join(", ", validationErrors));
  }

  // Validar que la cadena no esté vacía o nula
  public ValidationBuilder notEmpty(String str, String errorMessage) {
    if (str == null || str.isEmpty())
      validationErrors.add(errorMessage);

    return this;
  }

  // Validar que la cadena no esté vacía o nula y sea estricto
  public void notEmptyStrict(String str, String errorMessage) {
    if (str == null || str.isEmpty())
      validationErrors.add(errorMessage);
    throw new ValidationErrorException(String.join(", ", validationErrors));
  }

  // Validar que el valor esté dentro de un rango
  public ValidationBuilder isWithinRange(int value, int min, int max, String errorMessage) {
    if (value < min || value > max)
      validationErrors.add(errorMessage);

    return this;
  }

  // Validar el formato del email
  public ValidationBuilder isEmailValid(String email, String errorMessage) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    if (email == null || !email.matches(emailRegex))
      validationErrors.add(errorMessage);

    return this;
  }

  // Método para lanzar una excepción si hay errores de validación
  public void throwIfInvalid() {
    if (!validationErrors.isEmpty())
      throw new ValidationErrorException(String.join(", ", validationErrors));

  }

  // Validar que sea True
  public ValidationBuilder notTrue(Boolean data, String errorMessage) {
    if (data)
      validationErrors.add(errorMessage);
    return this;
  }

  // Validar que sea True
  public void notTrueStrict(Boolean data, String errorMessage) {
    if (data)
      validationErrors.add(errorMessage);
    throw new ValidationErrorException(String.join(", ", validationErrors));
  }

  // Validar que sea True
  public ValidationBuilder exist(Boolean valueBoolean, String errorMessage) {
    if (Boolean.TRUE.equals(valueBoolean))
      validationErrors.add(errorMessage);
    return this;
  }

  // Validar que sea True
  public void existStrict(Boolean data, String errorMessage) {
    if (data)
      validationErrors.add(errorMessage);
    throw new ValidationErrorException(String.join(", ", validationErrors));
  }
}
