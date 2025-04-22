package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.app.presentation.dtos.ProductDto;
import com.gestion.almacenes.app.presentation.validacion.FieldValidation;
import com.gestion.almacenes.app.presentation.validacion.ValidationExtractorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testSchemaValidation")
@Validated
@Tag(name = "SchemaValidationController", description = "Seccion para hacer pruebas de validaciones")
public class TestSchemaValidationController {

  private final ValidationExtractorService validationExtractorService;

  public TestSchemaValidationController(ValidationExtractorService validationExtractorService) {
    this.validationExtractorService = validationExtractorService;
  }

  @GetMapping("/product")
  public Map<String, List<FieldValidation>> getUserValidationRules() {
    return validationExtractorService.extractValidationRules(ProductDto.class);
  }

  @PostMapping("/product")
  public Map<String, List<FieldValidation>> create(@RequestBody @Valid ProductDto productDto) {
    return validationExtractorService.extractValidationRules(ProductDto.class);
  }

}