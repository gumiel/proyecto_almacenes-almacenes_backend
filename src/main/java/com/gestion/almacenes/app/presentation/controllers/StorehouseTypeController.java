package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.StorehouseTypeDto;
import com.gestion.almacenes.app.presentation.filters.StorehouseTypeFilter;
import com.gestion.almacenes.app.presentation.pojos.StorehouseTypePojo;
import com.gestion.almacenes.app.domain.services.StorehouseTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/storehouseType")
@Tag(name = "StorehouseTypeController", description = "Gestión de Tipo de almacenes. Los tipos son un clasificador que puede ser usado para organizar por secciones")
public class StorehouseTypeController {

  private final StorehouseTypeService storehouseTypeService;

  @GetMapping
  @Operation(summary = "Obtener todos los registros")
  public ResponseEntity<List<StorehouseTypePojo>> getAll() {

    return ResponseEntityGeneric.statusGET(
        storehouseTypeService.getAll()
    );

  }

  @Operation(summary = "Creación del registro")
  @PostMapping
  public ResponseEntity<StorehouseTypePojo> create(@Valid @RequestBody StorehouseTypeDto dto){

    return ResponseEntityGeneric.statusPOST(
            storehouseTypeService.create(dto)
    );

  }

  @Operation(summary = "Edición del registro")
  @PutMapping("/{id}")
  public ResponseEntity<StorehouseTypePojo> update(@PathVariable Integer id,
      @Valid @RequestBody StorehouseTypeDto dto) {

    return ResponseEntityGeneric.statusPUT(
            storehouseTypeService.update(id, dto)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el identificador")
  @GetMapping("/{id}")
  public ResponseEntity<StorehouseTypePojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
        storehouseTypeService.getById(id)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el código identificador")
  @GetMapping("/getByCode")
  public ResponseEntity<StorehouseTypePojo> getByCode(@RequestParam String code) {

    return ResponseEntityGeneric.statusGET(
        storehouseTypeService.getByCode(code)
    );

  }

  @Operation(summary = "Eliminación del registro por el identificador")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    storehouseTypeService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }


  @Operation(summary = "Deshabilitar del registro por el identificador")
  @DeleteMapping("/disabled/{id}")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {
    storehouseTypeService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();
  }

  @Operation(summary = "Paginador y buscador de registros por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<StorehouseTypePojo>> pageable(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "5") int size,
          @RequestParam(defaultValue = "id") String sortField,
          @RequestParam(defaultValue = "asc") String sortOrder,
          @RequestParam(required = false) String search,
          @RequestParam(required = false) Integer id,
          @RequestParam(required = false) String code,
          @RequestParam(required = false) String name  ) {

    StorehouseTypeFilter filter = StorehouseTypeFilter.builder()
            .id(id)
            .code(code)
            .name(name)
            .search(search)
            .build();
    return ResponseEntity.status(HttpStatus.OK).body(
            storehouseTypeService.pageable(page, size, sortField, sortOrder, filter)
    );

  }

}
