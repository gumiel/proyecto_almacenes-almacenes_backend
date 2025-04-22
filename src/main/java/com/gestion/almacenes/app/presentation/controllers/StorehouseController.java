package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.StorehouseDto;
import com.gestion.almacenes.app.presentation.filters.StorehouseFilter;
import com.gestion.almacenes.app.presentation.pojos.StorehousePojo;
import com.gestion.almacenes.app.domain.services.StorehouseService;
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
@RequestMapping("/storehouse")
@Tag(name = "StoreHouseController", description = "Gestión de Almacenes")
public class StorehouseController {

  private final StorehouseService storehouseService;

  @GetMapping
  @Operation(summary = "Obtener todos los registros")
  public ResponseEntity<List<StorehousePojo>> getAll() {

    return ResponseEntityGeneric.statusGET(
        storehouseService.getAll()
    );

  }

  @PostMapping
  @Operation(summary = "Creación del registro")
  public ResponseEntity<StorehousePojo> create(@Valid @RequestBody StorehouseDto dto) {

    return ResponseEntityGeneric.statusPOST(
        storehouseService.create(dto)
    );

  }

  @PutMapping("/{id}")
  @Operation(summary = "Edición del registro")
  public ResponseEntity<StorehousePojo> update(@PathVariable Integer id,
      @Valid @RequestBody StorehouseDto dto) {

    return ResponseEntityGeneric.statusPUT(
        storehouseService.update(id, dto)
    );

  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtención de los datos del registro por el identificador")
  public ResponseEntity<StorehousePojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
        storehouseService.getById(id)
    );

  }
  
  @Operation(summary = "Obtención de los datos del registro por el código identificador")
  @GetMapping("/getByCode")
  public ResponseEntity<StorehousePojo> getByCode(@RequestParam String code) {

      return ResponseEntityGeneric.statusGET(
          storehouseService.getByCode(code)
      );

  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminación del registro por el identificador")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    storehouseService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @DeleteMapping("/disabled/{id}")
  @Operation(summary = "Desactivar del registro por el identificador")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {

    storehouseService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Paginador y buscador de registros por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<StorehousePojo>> pageable(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "5") int size,
          @RequestParam(defaultValue = "id") String sortField,
          @RequestParam(defaultValue = "asc") String sortOrder,
          @RequestParam(required = false) String search,
          @RequestParam(required = false) Integer id,
          @RequestParam(required = false) String code,
          @RequestParam(required = false) String name,
          @RequestParam(required = false) String description,
          @RequestParam(required = false) Integer storehouseTypeId  ) {

    StorehouseFilter filter = StorehouseFilter.builder()
            .id(id)
            .code(code)
            .name(name)
            .description(description)
            .storehouseTypeId(storehouseTypeId)
            .search(search)            .build();
    return ResponseEntity.status(HttpStatus.OK).body(
            storehouseService.pageable(page, size, sortField, sortOrder, filter)
    );

  }

}
