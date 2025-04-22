package com.gestion.almacenes.helpers.app_custom_errors;

import com.gestion.almacenes.commons.exception.AlreadyDeletedException;
import com.gestion.almacenes.commons.exception.DuplicateException;
import com.gestion.almacenes.commons.exception.EntityNotFound;
import com.gestion.almacenes.commons.exception.ValidationErrorException;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public class CustomErrorsLib {

  /**
   * Método para mostrar el error de un código duplicado
   * @param objectClass El objeto de la clase que estará asociado
   * @param nameAttribute Nombre del atributo de la clase
   * @param value Valor enviado
   */
  public static void errorDuplicateInFieldCode(Class<?> objectClass, String nameAttribute, String value) {
    // Crear un BindingResult vacío
    BindingResult bindingResult = new BeanPropertyBindingResult(null, objectClass.getSimpleName());
    bindingResult.addError(new FieldError(objectClass.getSimpleName(), nameAttribute, "El código con valor ("+value+") ya existe."));

    // Si hay errores, lanzar la excepción
    if (bindingResult.hasErrors()) {
      try {
        throw new MethodArgumentNotValidException(getMethodParameterFiled("errorDuplicateInFieldCode"), bindingResult);
      } catch (MethodArgumentNotValidException e) {
        throw new RuntimeException(e);
      }
    }

  }

  /**
   * Método para mostrar el error de un valor cualquiera duplicado
   * @param objectClass El objeto de la clase que estará asociado
   * @param nameAttribute Nombre del atributo de la clase
   * @param message Mensaje personalizado del error que se mostrara
   */
  public static void errorDuplicateInField(Class<?> objectClass, String nameAttribute, String message) {
    // Crear un BindingResult vacío
    BindingResult bindingResult = new BeanPropertyBindingResult(null, objectClass.getSimpleName());
    bindingResult.addError(new FieldError(objectClass.getSimpleName(), nameAttribute, message));

    // Si hay errores, lanzar la excepción
    if (bindingResult.hasErrors()) {
      try {
        throw new MethodArgumentNotValidException(getMethodParameterFiled("errorDuplicateInField"), bindingResult);
      } catch (MethodArgumentNotValidException e) {
        throw new RuntimeException(e);
      }
    }

  }


  public static void errorProcess(String msg) {
    throw new ValidationErrorException(msg);
  }


  public static void errorEntityNotFoundInLine(Class<?> data,
      Integer id) {
    throw new EntityNotFound(data.getSimpleName(), id);
  }

  public static Supplier<? extends RuntimeException> errorEntityNotFound(Class<?> data,
                                                                         Integer id) {
    return () -> new EntityNotFound(data.getSimpleName(), id);
  }

  public static Supplier<? extends RuntimeException> errorEntityNotFound(String data) {
    return () -> new EntityNotFound(data);
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



  public static void miError(Class<?> data) {
    // Crear un BindingResult vacío
    BindingResult bindingResult = new BeanPropertyBindingResult(null, "usuario");
    bindingResult.addError(new FieldError("usuario", "codigo", "El codigo esta mal"));

    // Si hay errores, lanzar la excepción
    if (bindingResult.hasErrors()) {
      try {
        throw new MethodArgumentNotValidException(getMethodParameter(), bindingResult);
      } catch (MethodArgumentNotValidException e) {
        throw new RuntimeException(e);
      }
    }

  }

  /**
   * Correccion para el throw en las clases
   * @return
   */
  private static MethodParameter getMethodParameter() {
    try {
      // Suponiendo que el método 'miError' está en esta misma clase
      Method method = CustomErrorsLib.class.getMethod("miError", Class.class);

      // Crear el MethodParameter para el primer parámetro del método 'miError'
      return new MethodParameter(method, 0);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("No se pudo obtener el MethodParameter", e);
    }
  }

  /**
   * Correccion para el throw en las clases
   * @return
   */
  private static MethodParameter getMethodParameterFiled(String nameFunction) {
    try {
      // Suponiendo que el método 'miError' está en esta misma clase
      Method method = CustomErrorsLib.class.getMethod(nameFunction, Class.class, String.class, String.class);

      // Crear el MethodParameter para el primer parámetro del método 'miError'
      return new MethodParameter(method, 0);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("No se pudo obtener el MethodParameter", e);
    }
  }


}
