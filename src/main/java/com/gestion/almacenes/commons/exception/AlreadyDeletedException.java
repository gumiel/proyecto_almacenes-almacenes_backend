package com.gestion.almacenes.commons.exception;

import com.gestion.almacenes.commons.util.Messages;

public class AlreadyDeletedException extends RuntimeException {

  public AlreadyDeletedException(String entity, Integer id) {
    super(String.format(Messages.getProperty("exception.alreadyDeleted.message"), entity, id));
  }
}
