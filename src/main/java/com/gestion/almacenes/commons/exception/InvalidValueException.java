package com.gestion.almacenes.commons.exception;

import com.gestion.almacenes.commons.config.TranslateException;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(String columnName, String invalidValue) {
        super(new TranslateException().getTranslate("exception.invalidValue.message", invalidValue, 1));
    }

    public InvalidValueException(String columnName, String invalidValue, String message) {
        super(new TranslateException().getTranslate( message, columnName, 1 ));

    }
}
