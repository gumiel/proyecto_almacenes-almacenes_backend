package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.OrderProductDto;
import com.gestion.almacenes.app.presentation.filters.OrderProductFilter;
import com.gestion.almacenes.app.presentation.pojos.OrderProductPojo;
import com.gestion.almacenes.app.domain.services.OrderProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orderProduct")
@Tag(name = "OrderProductController", description = "Gestion de Ordenes para ingresos o salidas de almacen. Segun las ordenes que se realizan pueden ser del tipo de ingreso o salida y estas son las unicas que gestionaran los movimientos que tendra los almacenes")
public class OrderProductController {

  private final OrderProductService orderProductService;

  @Operation(summary = "Obtener todos los registros")
  @GetMapping
  public ResponseEntity<List<OrderProductPojo>> getAll() {

    return ResponseEntityGeneric.statusGET(
        orderProductService.getAll()
    );

  }

  @Operation(summary = "Creación del registro para una orden de ingreso o salida")
  @PostMapping
  public ResponseEntity<OrderProductPojo> create(@Valid @RequestBody OrderProductDto dto) {

    return ResponseEntityGeneric.statusPOST(
            orderProductService.create(dto)
    );

  }

  @Operation(summary = "Edición del registro para una orden de ingreso o salida")
  @PutMapping("/{id}")
  public ResponseEntity<OrderProductPojo> update(@PathVariable Integer id,
      @Valid @RequestBody OrderProductDto dto) {

    return ResponseEntityGeneric.statusPUT(
            orderProductService.update(id, dto)
    );

  }

  @Operation(summary = "Obtención de los datos de la orden por el identificador")
  @GetMapping("/{id}")
  public ResponseEntity<OrderProductPojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
            orderProductService.getById(id)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el código identificador")
  @GetMapping("/{code}")
  public ResponseEntity<OrderProductPojo> getById(@PathVariable String code) {

      return ResponseEntityGeneric.statusGET(
              orderProductService.getByCode(code)
      );

  }

  @Operation(summary = "Eliminación del registro para una orden de ingreso o salida")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    orderProductService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Eliminación del registro para una orden de ingreso o salida")
  @DeleteMapping("/disabled/{id}")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {

    orderProductService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Paginador y buscador de ordenes por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<OrderProductPojo>> getAllPagination(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) LocalDate registrationDate,
      @RequestParam(required = false) LocalTime registrationTime,
      @RequestParam(required = false) Integer storehouseId,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) Integer orderProductTypeId,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) Integer supplierId
  ) {

    OrderProductFilter filter = OrderProductFilter.builder()
            .id(id)
            .code(code)
            .registrationDate(registrationDate)
            .registrationTime(registrationTime)
            .storehouseId(storehouseId)
            .description(description)
            .orderProductTypeId(orderProductTypeId)
            .status(status)
            .supplierId(supplierId)
            .search(search)            .build();

    return ResponseEntityGeneric.statusGET(
        orderProductService.pageable(page, size, sortField, sortOrder, filter)
    );

  }

  @Operation(summary = "Ejecuta una orden de ingreso o salida para que aumente o disminuya el stock del almacen")
  @PutMapping("/executeOrderProduct/{id}")
  public ResponseEntity<OrderProductPojo> executeOrderProduct(@PathVariable Integer id) {

    OrderProductDto dto = new OrderProductDto();
    dto.setOrderProductId(id);

    return ResponseEntityGeneric.statusPUT(
            orderProductService.executeOrderProduct(dto)
    );

  }

}
