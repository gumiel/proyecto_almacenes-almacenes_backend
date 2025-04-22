package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.app.domain.services.CacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cache")
@Tag(name = "Cache", description = "Control del cache del sistema.")
public class CacheController {

  private final CacheService cacheService;

  @Operation(summary = "Actualiza el cache registrado para rescatar los nuevos datos")
  @PutMapping("/cleanCache")
  public ResponseEntity<String> cleanCache() {
    cacheService.cleanCache();
    return ResponseEntity.status(HttpStatus.OK).body("(Refresh cache) Updated cache throughout the system");
  }

}
