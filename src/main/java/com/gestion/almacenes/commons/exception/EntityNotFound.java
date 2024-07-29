package com.gestion.almacenes.commons.exception;

import com.gestion.almacenes.commons.config.TranslateException;

public class EntityNotFound extends RuntimeException {

  public EntityNotFound(String message) {
    super(message);
  }

  public EntityNotFound(String entity, Integer id) {
    super(
        new TranslateException().getTranslate("exception.entityNotFoundById.message", entity, id));
  }

  public EntityNotFound(String entity, String attribute, String value) {
    super(new TranslateException().getTranslate("exception.entityNotFoundByValue.message", entity,
        attribute, value));
  }

}
