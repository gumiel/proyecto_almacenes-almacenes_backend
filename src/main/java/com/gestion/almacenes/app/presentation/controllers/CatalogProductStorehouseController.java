package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.CatalogProductStorehouseDto;
import com.gestion.almacenes.app.presentation.filters.CatalogProductStorehouseFilter;
import com.gestion.almacenes.app.presentation.pojos.CatalogProductStorehousePojo;
import com.gestion.almacenes.app.domain.services.CatalogProductStorehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/catalogProductStorehouse")
@Tag(name = "CatalogProductStorehouseController", description = "Gestión de los productos en los almacenes (Relacion de las tablas producto y almacen).")
public class CatalogProductStorehouseController {

    private final CatalogProductStorehouseService catalogProductStorehouseService;

    @Operation(summary = "Obtener todos los registros")
    @GetMapping
    public ResponseEntity<List<CatalogProductStorehousePojo>> getAll() {

        return ResponseEntityGeneric.statusGET(
            catalogProductStorehouseService.getAll()
        );

    }

    @Operation(summary = "Creación del registro")
    @PostMapping
    public ResponseEntity<CatalogProductStorehousePojo> create(@Valid @RequestBody CatalogProductStorehouseDto dto) {

        return ResponseEntityGeneric.statusPOST(
                catalogProductStorehouseService.create(dto)
        );

    }

    @Operation(summary = "Edición del registro")
    @PutMapping("/{id}")
    public ResponseEntity<CatalogProductStorehousePojo> update(@PathVariable Integer id, @Valid @RequestBody CatalogProductStorehouseDto dto) {

        return ResponseEntityGeneric.statusPUT(
                catalogProductStorehouseService.update(id, dto)
        );

    }

    @Operation(summary = "Obtención de los datos del registro por el identificador")
    @GetMapping("/{id}")
    public ResponseEntity<CatalogProductStorehousePojo> getById(@PathVariable Integer id) {

        return ResponseEntityGeneric.statusGET(
            catalogProductStorehouseService.getById(id)
        );

    }

    @Operation(summary = "Eliminación del registro por el identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        catalogProductStorehouseService.delete(id);
        return ResponseEntityGeneric.statusDELETE();

    }

    @Operation(summary = "Eliminación del registro por el identificador")
    @DeleteMapping("/disabled/{id}")
    public ResponseEntity<Void> disabled(@PathVariable Integer id) {

        catalogProductStorehouseService.disabled(id);
        return ResponseEntityGeneric.statusDELETE();

    }

    @Operation(summary = "Eliminación del registro por el identificador")
    @DeleteMapping("/deleteByProductAndStorehouse")
    public ResponseEntity<Void> deleteByProductAndStorehouse(@RequestBody CatalogProductStorehouseDto dto) {

        catalogProductStorehouseService.deleteByProductAndStorehouse(dto);
        return ResponseEntityGeneric.statusDELETE();

    }

    @Operation(summary = "Paginador y buscador de registros por atributos")
    @GetMapping("/pageable")
    public ResponseEntity<PagePojo<CatalogProductStorehousePojo>> getAllPagination(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortOrder,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Integer id,
        @RequestParam(required = false) Integer storehouseId,
        @RequestParam(required = false) Integer productId
    ) {

        CatalogProductStorehouseFilter filter = CatalogProductStorehouseFilter.builder()
                .id(id)
                .storehouseId(storehouseId)
                .productId(productId)
                .search(search)            .build();

        return ResponseEntityGeneric.statusGET(
            catalogProductStorehouseService.pageable(page, size, sortField, sortOrder, filter)
        );

    }

    @Operation(summary = "Agrega todos los productos al catalogo de un almacén.")
    @PostMapping("/addAllProductsToStorehouse")
    public ResponseEntity<Void> addAllProductsToStorehouse(@Valid @RequestBody CatalogProductStorehouseDto dto) {

        catalogProductStorehouseService.addAllProductsToStorehouse(dto.getStorehouseId());
        return ResponseEntityGeneric.statusPOST();

    }

}
