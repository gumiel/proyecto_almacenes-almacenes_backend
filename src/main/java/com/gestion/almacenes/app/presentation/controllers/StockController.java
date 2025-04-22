package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ReportPojo;
import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.StockDto;
import com.gestion.almacenes.app.presentation.filters.StockFilter;
import com.gestion.almacenes.app.presentation.pojos.ProductPojo;
import com.gestion.almacenes.app.presentation.pojos.StockPojo;
import com.gestion.almacenes.app.domain.services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/stock")
@Tag(name = "StockController", description = "Gestión de la Existencia de productos por almacen. Es solo para el caso de que un almacen ya tenga un stock al iniciar el sistema, los datos de ingreso o salida se gestionan por las Ordenes")
public class StockController {

  private final StockService stockService;

  @Operation(summary = "Obtener todos los registros")
  @GetMapping
  public ResponseEntity<List<StockPojo>> getAll() {

    return ResponseEntityGeneric.statusGET(stockService.getAll());

  }

  @Operation(summary = "Creación del registro")
  @PostMapping
  public ResponseEntity<StockPojo> create(@Valid @RequestBody StockDto dto) {

    return ResponseEntityGeneric.statusPOST(
            stockService.create(dto)
    );

  }

  @Operation(summary = "Edición del registro")
  @PutMapping("/{id}")
  public ResponseEntity<StockPojo> update(@PathVariable Integer id, @Valid @RequestBody StockDto dto) {

    return ResponseEntityGeneric.statusPUT(
            stockService.update(id, dto)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el identificador")
  @GetMapping("/{id}")
  public ResponseEntity<StockPojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(stockService.getById(id));

  }

  @Operation(summary = "Obtención del stock segun el identificador de producto y almacen")
  @GetMapping("/getStockByStorehouseIdAndProductId")
  public ResponseEntity<StockPojo> getStockByStorehouseIdAndProductId(
          @RequestParam(defaultValue = "0") int storehouseId,
          @RequestParam(defaultValue = "0") int productId ) {

    return ResponseEntityGeneric.statusGET(
            stockService.getStockByStorehouseIdAndProductId(storehouseId, productId)
    );

  }

  @Operation(summary = "Eliminación del registro por el identificador")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    stockService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Eliminación del registro por el identificador")
  @DeleteMapping("/disabled/{id}")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {

    stockService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Paginador y buscador de registros por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<StockPojo>> pageable(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) Integer storehouseId,
      @RequestParam(required = false) Integer productId,
      @RequestParam(required = false) Double quantityInStock,
      @RequestParam(required = false) Double minimumStock,
      @RequestParam(required = false) Double maximumStock,
      @RequestParam(required = false) Boolean stockAlert,
      @RequestParam(required = false) Boolean validExpirationDate,
      @RequestParam(required = false) BigDecimal unitPrice
  ) {

    StockFilter filter = StockFilter.builder()
            .id(id)
            .storehouseId(storehouseId)
            .productId(productId)
            .quantityInStock(quantityInStock)
            .minimumStock(minimumStock)
            .maximumStock(maximumStock)
            .stockAlert(stockAlert)
            .validExpirationDate(validExpirationDate)
            .unitPrice(unitPrice)
            .search(search)
            .build();

    return ResponseEntityGeneric.statusGET(
        stockService.search(page, size, sortField,sortOrder, filter)
    );

  }


  @Operation(summary = "Reporte por almacen")
  @GetMapping("/reportByStorehouse")
  public ResponseEntity<ReportPojo> reportByStorehouse(
          @RequestParam(required = false) Integer storehouseId
  ) throws JRException, SQLException, IOException {

    return ResponseEntityGeneric.statusGET(
        stockService.reportByStorehouse(storehouseId)
    );

  }

  @Operation(summary = "Obtención del productos segun el almacen tomando en cuenta el stock")
  @GetMapping("/getProductByStorehouseId")
  public ResponseEntity<PagePojo<ProductPojo>> getProductByStorehouseId(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "5") int size,
          @RequestParam(defaultValue = "id") String sortField,
          @RequestParam(defaultValue = "asc") String sortOrder,
          @RequestParam(required = false) Integer storehouseId,
          @RequestParam(required = false) String term
  ) {

    return ResponseEntityGeneric.statusGET(
        stockService.getProductByStorehouseId(page, size, sortField, sortOrder, storehouseId, term)
    );

  }

}
