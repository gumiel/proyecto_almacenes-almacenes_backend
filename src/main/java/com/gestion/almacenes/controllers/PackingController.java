package com.gestion.almacenes.controllers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.dtos.PackingDto;
import com.gestion.almacenes.entities.Packing;
import com.gestion.almacenes.services.PackingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/packing")
@Tag(name = "PackingController")
public class PackingController {

  private final PackingService packingService;

  @GetMapping
  public ResponseEntity<List<Packing>> getAll() {
    List<Packing> packings = packingService.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(packings);
  }

  @PostMapping
  public ResponseEntity<Packing> create(@Valid @RequestBody PackingDto dto) {
    Packing packingSaved = packingService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(packingSaved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Packing> update(@PathVariable Integer id,
      @Valid @RequestBody PackingDto dto) {
    Packing packingUpdated = packingService.update(id, dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(packingUpdated);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Packing> getById(@PathVariable Integer id) {
    Packing packing = packingService.getById(id);
    return ResponseEntity.status(HttpStatus.OK).body(packing);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    packingService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<Packing>> getFiltered(
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String name) {
    List<Packing> packingListFiltered = packingService.getFiltered(code, name);

    return ResponseEntity.status(HttpStatus.OK).body(packingListFiltered);
  }

  @GetMapping("/pageable")
  public ResponseEntity<PagePojo<Packing>> getAllPagination(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortField,
      @RequestParam(defaultValue = "asc") String sortOrder,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String name
  ) {
    PagePojo<Packing> packingPagePojoFiltered = packingService.getByPageAndFilters(page, size,
        sortField, sortOrder, code, name);
    return ResponseEntity.status(HttpStatus.OK).body(packingPagePojoFiltered);
  }

}
