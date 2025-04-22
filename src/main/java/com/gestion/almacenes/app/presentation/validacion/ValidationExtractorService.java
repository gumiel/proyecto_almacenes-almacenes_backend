package com.gestion.almacenes.app.presentation.validacion;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ValidationExtractorService {

  public Map<String, List<FieldValidation>> extractValidationRules(Class<?> dtoClass) {
    Map<String, List<FieldValidation>> validationRules = new HashMap<>();

    for (Field field : dtoClass.getDeclaredFields()) {
      List<FieldValidation> rules = new ArrayList<>();
      String fieldName = field.getName(); // Nombre del campo para personalización

      if (field.isAnnotationPresent(NotNull.class)) {

        NotNull annotation = field.getAnnotation(NotNull.class);
        String message = formatMessage(annotation.message(), fieldName);

        rules.add(
            FieldValidation.builder()
                .type("NotNull")
                .messageGeneric("No puede ser nulo")
                .messageCustom(message)
                .build()
        );
      }
      if (field.isAnnotationPresent(NotBlank.class)) {

        NotBlank annotation = field.getAnnotation(NotBlank.class);
        String message = formatMessage(annotation.message(), fieldName);
        rules.add(
            FieldValidation.builder().type("NotBlank")
                .messageGeneric("No puede estar vacío")
                .messageCustom(message)
                .build()
        );
      }
      if (field.isAnnotationPresent(Size.class)) {
        Size size = field.getAnnotation(Size.class);
        String message = formatMessage(size.message(), fieldName)
            .replace("{min}", String.valueOf(size.min()))
            .replace("{max}", String.valueOf(size.max()));
        rules.add(
            FieldValidation.builder()
                .type("Size")
                .messageGeneric("Tamaño entre " + size.min() + " y " + size.max() + " caracteres")
                .messageCustom(message)
                .build()
        );
      }
      if (field.isAnnotationPresent(Min.class)) {
        Min min = field.getAnnotation(Min.class);
        String message = formatMessage(min.message(), fieldName)
            .replace("{value}", String.valueOf(min.value()));
        rules.add(
            FieldValidation.builder()
                .type("Min")
                .messageGeneric("Valor mínimo: " + min.value())
                .messageCustom(message)
                .build()
        );
      }
      if (field.isAnnotationPresent(Max.class)) {
        Max max = field.getAnnotation(Max.class);
        String message = formatMessage(max.message(), fieldName)
            .replace("{value}", String.valueOf(max.value()));
        rules.add(
            FieldValidation.builder()
                .type("Max")
                .messageGeneric("Valor máximo: " + max.value())
                .messageCustom(message)
                .build()
        );
      }
      if (field.isAnnotationPresent(Pattern.class)) {
        Pattern pattern = field.getAnnotation(Pattern.class);
        String message = formatMessage(pattern.message(), fieldName)
            .replace("{pattern}", pattern.regexp());
        rules.add(
            FieldValidation.builder()
                .type("Pattern")
                .messageGeneric("Debe cumplir el patrón: " + pattern.regexp())
                .messageCustom(message)
                .build()
        );
      }

      if (!rules.isEmpty()) {
        validationRules.put(field.getName(), rules);
      }
    }

    return validationRules;
  }

  private String formatMessage(String message, String fieldName) {
    // Si no hay mensaje definido o usa el valor por defecto, generamos uno
    if (message == null || message.isBlank() || message.equals("{}")) {
      return "Regla de validación para " + fieldName;
    }
    // Reemplazamos dinámicamente {field} con el nombre real del campo
    return message//.replace("{field}", fieldName)
        .replace("{is.required}", "es obligatorio")
        .replace("{is.invalid}", "tiene un formato inválido");
  }
}
