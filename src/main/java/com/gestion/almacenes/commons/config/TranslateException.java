package com.gestion.almacenes.commons.config;

import java.util.Formatter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class TranslateException {


  public TranslateException() {
  }

  public String getTranslate(String messages, String entity, Integer id) {

    String entityString = "Entity-not-found";
    String messagesCode = "Messages-not-found";

    try {
      ResourceBundle resourceBundleEntity = ResourceBundle.getBundle("messages/translate-entity");
      entityString = resourceBundleEntity.getString(entity);

    } catch (MissingResourceException exception) {
      entityString = "[" + entity + "](Falta traducción)";
    }

    try {
      ResourceBundle resourceBundleMsg = ResourceBundle.getBundle("messages/exception-messages");
      messagesCode = resourceBundleMsg.getString(messages);

    } catch (MissingResourceException exception) {
      messagesCode = "Messages-not-found";
    }

    return String.format(messagesCode, entityString, id);

//        try{
//            ResourceBundle resourceBundleEntity = ResourceBundle.getBundle("messages/translate-entity");
//            String entityString = resourceBundleEntity.getString(entity);
//
//            ResourceBundle resourceBundleMsg = ResourceBundle.getBundle("messages/exception-messages");
//            String messagesCode = resourceBundleMsg.getString(messages);
//
//            return String.format(messagesCode, entityString, id);
//
//        }catch (MissingResourceException exception){
//            ResourceBundle resourceBundleMsg = ResourceBundle.getBundle("messages/exception-messages");
//            String messagesCode = resourceBundleMsg.getString(messages);
//            return String.format(messagesCode, "["+entity+"](Falta traducción)", id);
//        }

  }

  public String getTranslate(String messages, String entity, String attribute, String value) {

    String entityString = "Entity-not-found";
    String messagesCode = "Messages-not-found";

    try {
      ResourceBundle resourceBundleEntity = ResourceBundle.getBundle("messages/translate-entity");
      entityString = resourceBundleEntity.getString(entity);

    } catch (MissingResourceException exception) {
      entityString = "[" + entity + "](Falta traducción)";
    }

    try {
      ResourceBundle resourceBundleMsg = ResourceBundle.getBundle("messages/exception-messages");
      messagesCode = resourceBundleMsg.getString(messages);

    } catch (MissingResourceException exception) {
      messagesCode = "Messages-not-found";
    }

    return String.format(messagesCode, entityString, attribute, value);


  }

  /**
   * @param format
   * @param args
   * @return
   */
  public static String format(String format, Object... args) {
    return new Formatter().format(format, args).toString();
  }


}