package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.OrderProductDetailDto;
import com.gestion.almacenes.app.presentation.filters.OrderProductDetailFilter;
import com.gestion.almacenes.app.presentation.pojos.OrderProductDetailPojo;
import com.gestion.almacenes.app.domain.services.OrderProductDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orderProductDetail")
@Tag(name = "OrderProductDetailController", description = "Gestión de los detalles de una orden. Los detalles son los datos items que tendra la orden")
public class OrderProductDetailController {

  private final OrderProductDetailService orderProductDetailService;

  @Operation(summary = "Obtener todos los detalles de todas las ordenes")
  @GetMapping
  public ResponseEntity<List<OrderProductDetailPojo>> getAll() {

    return ResponseEntityGeneric.statusGET(
        orderProductDetailService.getAll()
    );

  }

  @Operation(summary = "Creación del detalle de una orden")
  @PostMapping
  public ResponseEntity<OrderProductDetailPojo> create(@Valid @RequestBody OrderProductDetailDto dto) {

    return ResponseEntityGeneric.statusPOST(
            orderProductDetailService.create(dto)
    );

  }

  @Operation(summary = "Creación del detalle de una orden de forma global en una lista de detalles")
  @PostMapping("/createList")
  public ResponseEntity<Void> createList(@Valid @RequestBody List<OrderProductDetailDto> orderProductDetailDtos) {

    orderProductDetailService.createList(orderProductDetailDtos);
    return ResponseEntityGeneric.statusPOST();

  }

  @Operation(summary = "Edición del detalle de una orden")
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Integer id,
      @Valid @RequestBody OrderProductDetailDto dto) {

    orderProductDetailService.update(id, dto);
    return ResponseEntityGeneric.statusPUT();

  }

  @Operation(summary = "Obtención del detalle de una orden")
  @GetMapping("/{id}")
  public ResponseEntity<OrderProductDetailPojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
        orderProductDetailService.getById(id)
    );

  }

  @Operation(summary = "Eliminación del detalle de una orden")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    orderProductDetailService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Eliminación del detalle de una orden")
  @DeleteMapping("/disabled/{id}")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {

    orderProductDetailService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  /*@Operation(summary = "Paginador y buscador de los detalles de las ordenes por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<OrderProductDetailPojo>> getAllPagination(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String name
  ) {

    return ResponseEntityGeneric.statusGET(
        orderProductDetailService.getByPageAndFilters(page, size, sortField, sortOrder, code, name)
    );

  }*/

  @Operation(summary = "Paginador y buscador de los detalles de las ordenes por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<OrderProductDetailPojo>> search(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "5") int size,
          @RequestParam(defaultValue = "id") String sortField,
          @RequestParam(defaultValue = "asc") String sortOrder,
          @RequestParam(required = false) String search,
          @RequestParam(required = false) Integer id,
          @RequestParam(required = false) Integer orderProductId,
          @RequestParam(required = false) Integer stockId,
          @RequestParam(required = false) Double quantity,
          @RequestParam(required = false) String codeProduct,
          @RequestParam(required = false) LocalDate expirationDateProduct,
          @RequestParam(required = false) BigDecimal price
  ) {

    OrderProductDetailFilter filter = OrderProductDetailFilter.builder()
            .id(id)
            .orderProductId(orderProductId)
            .stockId(stockId)
            .quantity(quantity)
            .codeProduct(codeProduct)
            .expirationDateProduct(expirationDateProduct)
            .price(price)
            .search(search)            .build();

    return ResponseEntityGeneric.statusGET(
        orderProductDetailService.search(page, size, sortField, sortOrder, filter)
    );

  }

}
