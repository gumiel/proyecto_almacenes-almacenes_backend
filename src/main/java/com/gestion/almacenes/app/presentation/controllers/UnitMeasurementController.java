package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.UnitMeasurementDto;
import com.gestion.almacenes.app.presentation.filters.UnitMeasurementFilter;
import com.gestion.almacenes.app.presentation.pojos.UnitMeasurementPojo;
import com.gestion.almacenes.app.domain.services.UnitMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/unitMeasurement")
@Tag(name = "UnitMeasurementController", description = "Gestión de unidad de medida. Estas unidades de medida se relacionan con los productos")
public class UnitMeasurementController {

  private final UnitMeasurementService unitMeasurementService;

  @GetMapping
  @Operation(summary = "Obtener todos los registros")
  public ResponseEntity<List<UnitMeasurementPojo>> getAll() {

    return ResponseEntityGeneric.statusGET(
        unitMeasurementService.getAll()
    );

  }

  @PostMapping
  @Operation(summary = "Creación del registro")
  public ResponseEntity<UnitMeasurementPojo> create(@Valid @RequestBody UnitMeasurementDto dto) {

    return ResponseEntityGeneric.statusPOST(
            unitMeasurementService.create(dto)
    );

  }

  @Operation(summary = "Edición del registro")
  @PutMapping("/{id}")
  public ResponseEntity<UnitMeasurementPojo> update(@PathVariable Integer id,
      @Valid @RequestBody UnitMeasurementDto dto) {

    return ResponseEntityGeneric.statusPUT(
            unitMeasurementService.update(id, dto)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el identificador")
  @GetMapping("/{id}")
  public ResponseEntity<UnitMeasurementPojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
        unitMeasurementService.getById(id)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el código identificador")
  @GetMapping("/getByCode")
  public ResponseEntity<UnitMeasurementPojo> getByCode(@RequestParam String code) {

      return ResponseEntityGeneric.statusGET(
          unitMeasurementService.getByCode(code)
      );

  }

  @Operation(summary = "Eliminación del registro por el identificador")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    unitMeasurementService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Eliminación del registro por el identificador")
  @DeleteMapping("/disabled/{id}")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {

    unitMeasurementService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Paginador y buscador de registros por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<UnitMeasurementPojo>> pageable(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String name

  ) {

    return ResponseEntityGeneric.statusGET(
        unitMeasurementService.pageable(
                page,
                size,
                sortField,
                sortOrder,
                UnitMeasurementFilter.builder()
                        .code(code)
                        .name(name)
                        .build()
        )
    );

  }

}
