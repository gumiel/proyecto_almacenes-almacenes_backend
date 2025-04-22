package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.OrderProductTypeDto;
import com.gestion.almacenes.app.presentation.filters.OrderProductTypeFilter;
import com.gestion.almacenes.app.presentation.pojos.OrderProductTypePojo;
import com.gestion.almacenes.app.domain.services.OrderProductTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orderProductType")
@Tag(name = "OrderTypeController", description = "Gestión de Tipo de ordenes. Estos tipos son las ordenes que puede tener por Ejemplo Orden de Ingreso a almacen, Orden de Salida del almacen")
public class OrderProductTypeController {

  private final OrderProductTypeService orderProductTypeService;

  @Operation(summary = "Obtener todos los registros")
  @GetMapping
  public ResponseEntity<List<OrderProductTypePojo>> getAll() {

    return ResponseEntityGeneric.statusGET(
        orderProductTypeService.getAll()
    );

  }

  @Operation(summary = "Creación del registro")
  @PostMapping
  public ResponseEntity<OrderProductTypePojo> create(@Valid @RequestBody OrderProductTypeDto dto) {

    return ResponseEntityGeneric.statusPOST(
            orderProductTypeService.create(dto)
    );

  }

  @Operation(summary = "Edición del registro")
  @PutMapping("/{id}")
  public ResponseEntity<OrderProductTypePojo> update(@PathVariable Integer id,
      @Valid @RequestBody OrderProductTypeDto dto) {

    return ResponseEntityGeneric.statusPUT(
            orderProductTypeService.update(id, dto)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el identificador")
  @GetMapping("/{id}")
  public ResponseEntity<OrderProductTypePojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
        orderProductTypeService.getById(id)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el código identificador")
  @GetMapping("/getByCode")
  public ResponseEntity<OrderProductTypePojo> getByCode(@RequestParam String code) {

      return ResponseEntityGeneric.statusGET(
          orderProductTypeService.getByCode(code)
      );

  }

  @Operation(summary = "Eliminación del registro por el identificador")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    orderProductTypeService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Deshabilitar del registro por el identificador")
  @DeleteMapping("/disabled/{id}")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {

    orderProductTypeService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Paginador y buscador de registros por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<OrderProductTypePojo>> getAllPagination(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String action
  ) {
    OrderProductTypeFilter filter = OrderProductTypeFilter.builder()
            .id(id)
            .code(code)
            .name(name)
            .description(description)
            .action(action)
            .search(search)            .build();
    return ResponseEntityGeneric.statusGET(
        orderProductTypeService.pageable(page, size, sortField, sortOrder, filter)
    );

  }


}
