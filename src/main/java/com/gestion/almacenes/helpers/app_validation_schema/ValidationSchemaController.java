package com.gestion.almacenes.helpers.app_validation_schema;

import com.gestion.almacenes.app.presentation.validacion.FieldValidation;
import com.gestion.almacenes.app.presentation.validacion.ValidationExtractorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/validationSchema")
@Tag(name = "ValidationSchema", description = "Seccion para hacer pruebas de validaciones para frontend")
public class ValidationSchemaController {

  /*private final RequestMappingHandlerMapping handlerMapping;
  private final ValidationExtractorService validationExtractorService;

  public ValidationSchemaController(ApplicationContext applicationContext, ValidationExtractorService validationExtractorService) {
    this.handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
    this.validationExtractorService = validationExtractorService;
  }

  @GetMapping("/searchEndpoint")
  public ResponseEntity<?> searchEndpoint(
          @RequestParam(required = false) String method,
          @RequestParam(required = false) String endpointPath
  ) {
    String fullPath = "/" + endpointPath; // Convertir "user/create" en "/user/create"

    for (Map.Entry<?, ?> entry : handlerMapping.getHandlerMethods().entrySet()) {
      Object key = entry.getKey();
      HandlerMethod handlerMethod = (HandlerMethod) entry.getValue();
      System.out.println(key.toString()+" == "+fullPath);
      if (key.toString().contains(fullPath) && matchesHttpMethod(handlerMethod, method)) {
        // Buscar el DTO con @Valid
        for (Parameter parameter : handlerMethod.getMethod().getParameters()) {
          if (parameter.isAnnotationPresent(Valid.class)) {
            Class<?> dtoClass = parameter.getType();
            Map<String, List<FieldValidation>> validations = validationExtractorService.extractValidationRules(dtoClass);
            return ResponseEntity.ok(validations);
          }
        }
        return ResponseEntity.badRequest().body("No se encontró un DTO con @Valid en este endpoint.");
      }
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{method}/{endpointPath}/")
  public ResponseEntity<?> getValidations(@PathVariable String method, @PathVariable String endpointPath) {
    String fullPath = "/" + endpointPath; // Convertir "user/create" en "/user/create"

    for (Map.Entry<?, ?> entry : handlerMapping.getHandlerMethods().entrySet()) {
      Object key = entry.getKey();
      HandlerMethod handlerMethod = (HandlerMethod) entry.getValue();

      if (key.toString().contains(fullPath) && matchesHttpMethod(handlerMethod, method)) {
        // Buscar el DTO con @Valid
        for (Parameter parameter : handlerMethod.getMethod().getParameters()) {
          if (parameter.isAnnotationPresent(Valid.class)) {
            Class<?> dtoClass = parameter.getType();
            Map<String, List<FieldValidation>> validations = validationExtractorService.extractValidationRules(dtoClass);
            return ResponseEntity.ok(validations);
          }
        }
        return ResponseEntity.badRequest().body("No se encontró un DTO con @Valid en este endpoint.");
      }
    }

    return ResponseEntity.notFound().build();
  }




  @GetMapping("/{endpointPath}/")
  public ResponseEntity<?> getValidations(@PathVariable String endpointPath) {
    String fullPath = "/" + endpointPath; // Convertir "user/create" en "/user/create"

    // Buscar el handler (controlador) correspondiente a la ruta
    for (Map.Entry<?, ?> entry : handlerMapping.getHandlerMethods().entrySet()) {
      Object key = entry.getKey();
      HandlerMethod method = (HandlerMethod) entry.getValue();

      if (key.toString().contains(fullPath)) { // Verifica si la ruta coincide
        // Buscar el DTO con @Valid
        for (Parameter parameter : method.getMethod().getParameters()) {
          if (parameter.isAnnotationPresent(Valid.class)) {
            Class<?> dtoClass = parameter.getType();
            Map<String, List<FieldValidation>> validations = validationExtractorService.extractValidationRules(dtoClass);
            return ResponseEntity.ok(validations);
          }
        }
        return ResponseEntity.badRequest().body("No se encontró un DTO con @Valid en este endpoint.");
      }
    }

    return ResponseEntity.notFound().build();
  }



  @GetMapping
  public ResponseEntity<?> getValidations1(
      @RequestParam String method,
      @RequestParam String endpointPath
  ) {
    // String fullPath = "/" + endpointPath; // Convertir "user/create" en "/user/create"
    String fullPath = endpointPath;

    for (Map.Entry<?, ?> entry : handlerMapping.getHandlerMethods().entrySet()) {
      Object key = entry.getKey();
      HandlerMethod handlerMethod = (HandlerMethod) entry.getValue();

      if (key.toString().contains(fullPath) && matchesHttpMethod(handlerMethod, method)) {
        // Buscar el DTO con @Valid
        for (Parameter parameter : handlerMethod.getMethod().getParameters()) {
          if (parameter.isAnnotationPresent(Valid.class)) {
            Class<?> dtoClass = parameter.getType();
            Map<String, List<FieldValidation>> validations = validationExtractorService.extractValidationRules(dtoClass);
            return ResponseEntity.ok(validations);
          }
        }
        return ResponseEntity.badRequest().body("No se encontró un DTO con @Valid en este endpoint.");
      }
    }

    return ResponseEntity.notFound().build();
  }

  private boolean matchesHttpMethod(HandlerMethod handlerMethod, String method) {
    switch (method.toUpperCase()) {
      case "POST":
        return handlerMethod.hasMethodAnnotation(PostMapping.class);
      case "PUT":
        return handlerMethod.hasMethodAnnotation(PutMapping.class);
      case "GET":
        return handlerMethod.hasMethodAnnotation(GetMapping.class);
      case "DELETE":
        return handlerMethod.hasMethodAnnotation(DeleteMapping.class);
      default:
        return false;
    }
  }*/
}