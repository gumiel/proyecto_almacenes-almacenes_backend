package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.persistence.projections.OrderProductDetailHistoryProjection;
import com.gestion.almacenes.app.domain.services.OrderProductDetailHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orderProductDetailHistory")
@Tag(name = "OrderProductDetailController", description = "Gestión de los históricos de detalles de una orden")
public class OrderProductDetailHistoryController {

  private final OrderProductDetailHistoryService orderProductDetailHistoryService;

  @Operation(summary = "Obtención del histórico de detalle de una orden")
  @GetMapping("/{id}")
  public ResponseEntity<List<OrderProductDetailHistoryProjection>> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
        orderProductDetailHistoryService.getAllByStockId(id)
    );

  }

  @Operation(summary = "Obtención del histórico de detalle de una orden")
  @GetMapping("/pageableByStockId")
  public ResponseEntity<PagePojo<OrderProductDetailHistoryProjection>> pageable(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) Integer stockId
  ) {

    return ResponseEntityGeneric.statusGET(
        orderProductDetailHistoryService.pageableByStockId(page, size, sortField, sortOrder, stockId)
    );

  }


}
