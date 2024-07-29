package com.gestion.almacenes.commons.exception;

import com.gestion.almacenes.commons.util.Messages;

public class DuplicateException extends RuntimeException {

  public DuplicateException(String entityName, String columnName, String id) {
    super(String.format(Messages.getProperty("exception.duplicate.message"), entityName, columnName,
        id));
  }
}
