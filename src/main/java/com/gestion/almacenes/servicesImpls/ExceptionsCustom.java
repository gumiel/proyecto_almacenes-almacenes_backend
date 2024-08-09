package com.gestion.almacenes.servicesImpls;

import com.gestion.almacenes.commons.exception.AlreadyDeletedException;
import com.gestion.almacenes.commons.exception.DuplicateException;
import com.gestion.almacenes.commons.exception.EntityNotFound;
import com.gestion.almacenes.commons.exception.ValidationErrorException;
import com.gestion.almacenes.entities.StorehouseType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Supplier;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ExceptionsCustom {


  public static void errorProcess(String msg) {
    throw new ValidationErrorException(msg);
  }


  public static Supplier<? extends RuntimeException> errorEntityNotFound(Class<?> data,
      Integer id) {
    return () -> new EntityNotFound(data.getSimpleName(), id);
  }

  public static Supplier<? extends RuntimeException> errorEntityNotFound(Class<?> data,
      String attribute, String value) {
    return () -> new EntityNotFound(data.getSimpleName(), attribute, value);
  }

  public static void errorDuplicate(Class<?> data, String nameAttribute, String value) {
    throw new DuplicateException(data.getSimpleName(), nameAttribute, value);
  }

  public static void errorAlreadyDeleted(Class<?> data, Integer id) {
    throw new AlreadyDeletedException(data.getSimpleName(), id);
  }

  public static void miError(StorehouseType data) {
    // Crear un BindingResult vacío
    BindingResult bindingResult = new BeanPropertyBindingResult(data, "usuario");
    bindingResult.addError(new FieldError("usuario", "codigo", "El codigo esta mal"));
//    // Validar manualmente las anotaciones en la clase Usuario
//    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//    Set<ConstraintViolation<T>> violations = validator.validate(usuario);
//
//    // Si hay violaciones, añadirlas al BindingResult
//    for (ConstraintViolation<Usuario> violation : violations) {
//      String fieldName = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
//      bindingResult.addError(new FieldError("usuario", fieldName, violation.getMessage()));
//    }

    // Si hay errores, lanzar la excepción
    if (bindingResult.hasErrors()) {
      try {
        throw new MethodArgumentNotValidException(getMethodParameter(), bindingResult);
      } catch (MethodArgumentNotValidException e) {
        throw new RuntimeException(e);
      }
    }

  }

  private static MethodParameter getMethodParameter() {
    try {
      // Suponiendo que el método 'miError' está en esta misma clase
      Method method = ExceptionsCustom.class.getMethod("miError", StorehouseType.class);

      // Crear el MethodParameter para el primer parámetro del método 'miError'
      return new MethodParameter(method, 0);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("No se pudo obtener el MethodParameter", e);
    }
  }

  /*public static void miError(StorehouseType data) {
    // Crear un BindingResult vacío
    BindingResult bindingResult = new BeanPropertyBindingResult(data, "usuario");
    bindingResult.addError(new FieldError("usuario", "codigo", "El codigo esta mal"));

    if (bindingResult.hasErrors()) {
      // Lanzar una excepción genérica o personalizada
      throw new IllegalArgumentException("Datos inválidos: " + bindingResult.getAllErrors());
    }
  }*/

}
