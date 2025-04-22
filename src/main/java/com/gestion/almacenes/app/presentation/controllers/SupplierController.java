package com.gestion.almacenes.app.presentation.controllers;

import com.gestion.almacenes.commons.ResponseEntityGeneric;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.SupplierDto;
import com.gestion.almacenes.app.presentation.filters.SupplierFilter;
import com.gestion.almacenes.app.presentation.pojos.SupplierPojo;
import com.gestion.almacenes.app.domain.services.SupplierService;
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
@RequestMapping("/supplier")
@Tag(name = "SupplierController", description = "Gestion de los proveedores")
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Obtener todos los registros")
    @GetMapping
    public ResponseEntity<List<SupplierPojo>> getAll() {

        return ResponseEntityGeneric.statusGET(
            supplierService.getAll()
        );

    }

    @Operation(summary = "Creación del registro")
    @PostMapping
    public ResponseEntity<SupplierPojo> create(@Valid @RequestBody SupplierDto dto) {

        return ResponseEntityGeneric.statusPOST(
                supplierService.create(dto)
        );

    }

    @Operation(summary = "Edición del registro")
    @PutMapping("/{id}")
    public ResponseEntity<SupplierPojo> update(@PathVariable Integer id, @Valid @RequestBody SupplierDto dto) {

        return ResponseEntityGeneric.statusPUT(
                supplierService.update(id, dto)
        );

    }

    @Operation(summary = "Obtención de los datos del registro por el identificador")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierPojo> getById(@PathVariable Integer id) {

        return ResponseEntityGeneric.statusGET(
            supplierService.getById(id)
        );

    }
    
    @Operation(summary = "Obtención de los datos del registro por el código identificador")
    @GetMapping("/getByCode")
    public ResponseEntity<SupplierPojo> getBySupplierCode(@RequestParam String code) {

        return ResponseEntityGeneric.statusGET(
            supplierService.getBySupplierCode(code)
        );

    }

    @Operation(summary = "Eliminación del registro por el identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        supplierService.delete(id);
        return ResponseEntityGeneric.statusDELETE();

    }

    @Operation(summary = "Deshabilitar del registro por el identificador")
    @DeleteMapping("/disabled/{id}")
    public ResponseEntity<Void> disabled(@PathVariable Integer id) {

        supplierService.disabled(id);
        return ResponseEntityGeneric.statusDELETE();

    }

    @Operation(summary = "Paginador y buscador de registros por atributos")
    @GetMapping("/pageable")
    public ResponseEntity<PagePojo<SupplierPojo>> pageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer registerDateId,
            @RequestParam(required = false) String supplierCode,
            @RequestParam(required = false) String supplierPhoneNumber,
            @RequestParam(required = false) String supplierCelNumber,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String companyDescription,
            @RequestParam(required = false) Boolean enable,
            @RequestParam(required = false) String ownerNames,
            @RequestParam(required = false) String ownerSurname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String search
            ) {

        SupplierFilter filter = SupplierFilter.builder()
                .id(id)
                .registerDateId(registerDateId)
                .supplierCode(supplierCode)
                .supplierPhoneNumber(supplierPhoneNumber)
                .supplierCelNumber(supplierCelNumber)
                .companyName(companyName)
                .address(address)
                .companyDescription(companyDescription)
                .enable(enable)
                .ownerNames(ownerNames)
                .ownerSurname(ownerSurname)
                .email(email)
                .search(search)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(
                supplierService.pageable(page, size, sortField, sortOrder, filter));

    }
}