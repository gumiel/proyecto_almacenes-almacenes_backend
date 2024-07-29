package com.gestion.almacenes.commons.util;

import java.util.ResourceBundle;

public class Messages {

  private static final ResourceBundle messages = ResourceBundle.getBundle(
      "messages/translate-entity.properties");

  public static String getProperty(String key) {
    return messages.getString(key);
  }
}
