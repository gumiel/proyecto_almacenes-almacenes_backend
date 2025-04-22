package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.ProductDto;
import com.gestion.almacenes.app.presentation.filters.ProductFilter;
import com.gestion.almacenes.app.presentation.pojos.ProductPojo;
import com.gestion.almacenes.app.persistence.projections.ProductProjection;
import com.gestion.almacenes.app.domain.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
@Tag(name = "ProductController", description = "Gestión de productos. Los productos son los unicos que podran ingresar o salir a un almacen")
public class ProductController {

  private final ProductService productService;

  @Operation(summary = "Obtener todos los registros")
  @GetMapping
  public ResponseEntity<List<ProductPojo>> getAll() {

    return ResponseEntityGeneric.statusGET(
        productService.getAll()
    );

  }

  @Operation(summary = "Creación del registro")
  @PostMapping
  public ResponseEntity<ProductPojo> create(@Valid @RequestBody ProductDto dto) {

    return ResponseEntityGeneric.statusPOST(
        productService.create(dto)
    );

  }

  @Operation(summary = "Edición del registro")
  @PutMapping("/{id}")
  public ResponseEntity<ProductPojo> update(@PathVariable Integer id,
      @Valid @RequestBody ProductDto dto) {

    return ResponseEntityGeneric.statusPUT(
        productService.update(id, dto)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el identificador")
  @GetMapping("/{id}")
  public ResponseEntity<ProductPojo> getById(@PathVariable Integer id) {

    return ResponseEntityGeneric.statusGET(
        productService.getById(id)
    );

  }

  @Operation(summary = "Obtención de los datos del registro por el código identificador")
  @GetMapping("/getByCode")
  public ResponseEntity<ProductPojo> getByCode(@RequestParam String code) {

      return ResponseEntityGeneric.statusGET(
          productService.getByCode(code)
      );

  }

  @Operation(summary = "Eliminación del registro por el identificador")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    productService.delete(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Deshabilitar del registro por el identificador")
  @DeleteMapping("/disabled/{id}")
  public ResponseEntity<Void> disabled(@PathVariable Integer id) {

    productService.disabled(id);
    return ResponseEntityGeneric.statusDELETE();

  }

  @Operation(summary = "Paginador y buscador de registros por atributos")
  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<ProductPojo>> pageable(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "5") int size,
          @RequestParam(defaultValue = "id") String sortField,
          @RequestParam(defaultValue = "asc") String sortOrder,
          @RequestParam(required = false) String search,
          @RequestParam(required = false) Integer id,
          @RequestParam(required = false) String code,
          @RequestParam(required = false) String name,
          @RequestParam(required = false) String description,
          @RequestParam(required = false) Integer unitMeasurementId,
          @RequestParam(required = false) BigDecimal approximateUnitPrice
  ) {

    return ResponseEntityGeneric.statusGET(
            productService.pageable(page, size, sortField, sortOrder, ProductFilter.builder()
                    .id(id)
                    .code(code)
                    .name(name)
                    .description(description)
                    .unitMeasurementId(unitMeasurementId)
                    .approximateUnitPrice(approximateUnitPrice)
                    .search(search)
                    .build())
    );

  }

  @Operation(summary = "Paginador y buscador de registros por atributos")
  @GetMapping("/search")
  public ResponseEntity<PagePojo<ProductProjection>> search(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) String term
  ) {

    return ResponseEntityGeneric.statusGET(
        productService.search(page, size, sortField,
        sortOrder, term)
    );

  }

}
