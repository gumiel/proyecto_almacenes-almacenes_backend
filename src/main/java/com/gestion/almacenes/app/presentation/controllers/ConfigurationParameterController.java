package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.ConfigurationParameterDto;
import com.gestion.almacenes.app.presentation.filters.ConfigurationParameterFilter;
import com.gestion.almacenes.app.presentation.pojos.ConfigurationParameterPojo;
import com.gestion.almacenes.app.domain.services.ConfigurationParameterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/configurationParameter")
@Tag(name = "ConfigurationParameterController_bk", description = "Gestión de productos. Los productos son los unicos que podran ingresar o salir a un almacen")
public class ConfigurationParameterController {

  private final ConfigurationParameterService configurationParameterService;

  @Operation(summary = "Obtener todos los registros")
  @GetMapping
  public ResponseEntity<List<ConfigurationParameterPojo>> getAll() {

    return ResponseEntityGeneric.statusGET(
        configurationParameterService.getAll()
    );

  }

  @Operation(summary = "Creación del registro")
  @PostMapping
  public ResponseEntity<ConfigurationParameterPojo> create(@Valid @RequestBody ConfigurationParameterDto dto) {

    return ResponseEntityGeneric.statusPOST(
            configurationParameterService.create(dto)
    );

  }

  @Operation(summary = "Edición del registro")
  @PutMapping("/{id}")
  public ResponseEntity<ConfigurationParameterPojo> update(@PathVariable Integer id,
      @Valid @RequestBody ConfigurationParameterDto dto) {

    return ResponseEntityGeneric.statusPUT(
            configurationParameterService.update(id, dto)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el identificador")
  @GetMapping("/{id}")
  public ResponseEntity<ConfigurationParameterPojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
        configurationParameterService.getById(id)
    );

  }

  @Operation(summary = "Eliminación del registro por el identificador")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    configurationParameterService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Deshabilitar del registro por el identificador")
  @DeleteMapping("/disabled/{id}")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {

    configurationParameterService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Paginador y buscador de registros por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<ConfigurationParameterPojo>> pageable(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String value,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String search
  ) {

    ConfigurationParameterFilter filter = ConfigurationParameterFilter.builder()
            .id(id)
            .code(code)
            .value(value)
            .description(description)
            .search(search)
            .build();

    return ResponseEntityGeneric.statusGET(
        configurationParameterService.pageable(page, size, sortField, sortOrder, filter)
    );

  }

}
