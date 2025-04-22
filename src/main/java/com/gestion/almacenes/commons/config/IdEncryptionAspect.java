package com.gestion.almacenes.commons.config;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

@Aspect
@Component
public class IdEncryptionAspect {

  @AfterReturning(pointcut = "execution(* com.gestion.almacenes.controllers..*(..))", returning = "result")
  public void encryptIdInResponse(Object result) throws Exception {
    if (result == null) return;

    // Si el resultado es un ResponseEntity, accedemos al body
    if (result instanceof ResponseEntity) {
      ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
      Object body = responseEntity.getBody();
      if (body != null) {
        processBody(body);
      }
    } else {
      // Si no es ResponseEntity, lo tratamos directamente como una entidad o colección
      processBody(result);
    }
  }

  private void processBody(Object body) throws Exception {
    if (body instanceof Collection) {
      for (Object entity : (Collection<?>) body) {
        encryptEntityId(entity);
      }
    } else {
      encryptEntityId(body);
    }
  }

  private void encryptEntityId(Object entity) throws Exception {
    if (entity == null) return;

    // Buscar el campo 'id' en la clase o sus superclases
    Field field = getFieldWithId(entity);
    if (field != null && field.getType() == Long.class) {
      field.setAccessible(true);
      Long id = (Long) field.get(entity);
      if (id != null) {
        // Encriptar el ID
        String encryptedId = AESUtil.encrypt(id.toString());
        field.set(entity, encryptedId);
      }
    }
  }

  private Field getFieldWithId(Object entity) {
    Field[] fields = entity.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.getName().equals("id")) {
        return field;
      }
    }
    // Si el campo no está directamente en la clase, busca en la clase padre (herencia)
    Class<?> superclass = entity.getClass().getSuperclass();
    if (superclass != null && superclass != Object.class) {
      return getFieldWithId(superclass);
    }
    return null;
  }
}

/*
import java.util.List;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.Collection;

@Aspect
@Component
public class IdEncryptionAspect {

  @AfterReturning(pointcut = "execution(* com.gestion.almacenes.controllers..*(..))", returning = "result")
  public void encryptIdInResponse(Object result) throws Exception {
    if (result == null) return;

    if (result instanceof List<?>) {
      for (Object entity : (List<?>) result) {
        encryptEntityId(entity);
      }
    } else {
      encryptEntityId(result);
    }
  }

  private void encryptEntityId(Object entity) throws Exception {
    if (entity == null) return;

    // Buscar el campo 'id' en la clase o sus superclases
    Field field = getFieldWithId(entity);
    if (field != null && field.getType() == Long.class) {
      field.setAccessible(true);
      Long id = (Long) field.get(entity);
      if (id != null) {
        // Encriptar el ID
        String encryptedId = AESUtil.encrypt(id.toString());
        // Cambiar el tipo de 'id' a String si es necesario o crear un nuevo campo
        field.set(entity, encryptedId);
      }
    }
  }

  private Field getFieldWithId(Object entity) {
    Field[] fields = entity.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.getName().equals("id")) {
        return field;
      }
    }
    // Si el campo no está directamente en la clase, busca en la clase padre (herencia)
    Class<?> superclass = entity.getClass().getSuperclass();
    if (superclass != null && superclass != Object.class) {
      return getFieldWithId(superclass);
    }
    return null;
  }
}*/