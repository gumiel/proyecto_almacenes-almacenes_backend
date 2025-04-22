package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.ConfigDto;
import com.gestion.almacenes.app.presentation.filters.ConfigFilter;
import com.gestion.almacenes.app.presentation.pojos.ConfigPojo;
import com.gestion.almacenes.app.domain.services.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/config")
@Tag(name = "ConfigController", description = "Gestión de parametros generales del sistema. Estas son variables que pueden ser modificadas en cualquier momento")
public class ConfigController {

  private final ConfigService configService;

  @Operation(summary = "Obtener todos los registros")
  @GetMapping
  public ResponseEntity<List<ConfigPojo>> getAll() {

    return ResponseEntityGeneric.statusGET(
        configService.getAll()
    );

  }

  @Operation(summary = "Creación del registro")
  @PostMapping
  public ResponseEntity<ConfigPojo> create(@Valid @RequestBody ConfigDto dto) {

    return ResponseEntityGeneric.statusPOST(
        configService.create(dto)
    );

  }

  @Operation(summary = "Edición del registro")
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Integer id,
      @Valid @RequestBody ConfigDto dto) {

    configService.update(id, dto);
    return ResponseEntityGeneric.statusPUT();

  }

  @Operation(summary = "Obtención de los datos del registro por el identificador")
  @GetMapping("/{id}")
  public ResponseEntity<ConfigPojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
        configService.getById(id)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el código identificador")
  @GetMapping("/{code}")
  public ResponseEntity<ConfigPojo> getByCode(@PathVariable String code) {

      return ResponseEntityGeneric.statusGET(
          configService.getByCode(code)
      );

  }

  @Operation(summary = "Eliminación del registro por el identificador")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    configService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }
  
  @Operation(summary = "Deshabilitación del registro por el identificador")
  @DeleteMapping("/disabled/{id}")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {

    configService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();

  }
  
  @Operation(summary = "Paginador y buscador de registros por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<ConfigPojo>> getAllPagination(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String value,
      @RequestParam(required = false) String description
  ) {

    ConfigFilter filter = ConfigFilter.builder()
            .id(id)
            .code(code)
            .value(value)
            .description(description)
            .search(search)            .build();

    return ResponseEntityGeneric.statusGET(
        configService.pageable(page, size, sortField, sortOrder, filter)
    );

  }

  @Operation(summary = "Lista de codigo de configuracion que es una prueba para usar consultas nativas con entiti manager")
  @GetMapping("/lista")
  public ResponseEntity<List<String>> lista() {

    return ResponseEntityGeneric.statusGET(
        configService.lista("", "")
    );

  }

}
