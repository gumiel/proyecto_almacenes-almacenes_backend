package com.gestion.almacenes.servicesImpls;

import com.gestion.almacenes.commons.exception.AlreadyDeletedException;
import com.gestion.almacenes.commons.exception.DuplicateException;
import com.gestion.almacenes.commons.exception.EntityNotFound;
import com.gestion.almacenes.commons.exception.ValidationErrorException;
import java.util.function.Supplier;

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

}
