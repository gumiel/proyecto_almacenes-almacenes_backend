package com.gestion.almacenes.commons.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion.almacenes.app.presentation.validacion.FieldValidation;
import com.gestion.almacenes.app.presentation.validacion.ValidationExtractorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Map;
@Component
public class ValidationHeaderInterceptor implements HandlerInterceptor {

  private final ValidationExtractorService validationExtractorService;
  private final ObjectMapper objectMapper;

  public ValidationHeaderInterceptor(ValidationExtractorService validationExtractorService, ObjectMapper objectMapper) {
    this.validationExtractorService = validationExtractorService;
    this.objectMapper = objectMapper;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (handler instanceof HandlerMethod handlerMethod) {
      Class<?>[] parameterTypes = handlerMethod.getMethod().getParameterTypes();

      /*for (Class<?> paramType : parameterTypes) {
        Map<String, List<FieldValidation>> validationRules = validationExtractorService.extractValidationRules(paramType);

        if (!validationRules.isEmpty()) {
          validationRules.forEach((field, rules) -> {
            FieldValidation firstRule = rules.get(0);
            String headerName = "X-Validation-" + field;
            String headerValue = String.join("; ", firstRule.getMessageGeneric());
            response.addHeader(headerName, headerValue);
          });
        }
      }*/
      for (Class<?> paramType : parameterTypes) {
        Map<String, List<FieldValidation>> validationRules = validationExtractorService.extractValidationRules(paramType);

        if (!validationRules.isEmpty()) {
          try {
            // Convertimos el Map a JSON
            String jsonValidation = objectMapper.writeValueAsString(validationRules);

            // Opción 1: Sin codificación (si el JSON es corto y seguro para headers)
            response.setHeader("X-Validation-Rules", jsonValidation);

            // Opción 2: Codificado en Base64 (para evitar problemas con caracteres especiales)
            // String encodedJson = Base64.getEncoder().encodeToString(jsonValidation.getBytes());
            // response.setHeader("X-Validation-Rules", encodedJson);

          } catch (JsonProcessingException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return true;
  }
}