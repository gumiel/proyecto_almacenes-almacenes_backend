package com.gestion.almacenes.helpers.app_custom_errors.example;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Tag(name = "PersonCustomErrors", description = "Ejemplo del uso de la librería custom errors")
@RestController
@RequestMapping("/personCustomErrors")
public class PersonCustomErrorsController {
    private final PersonCustomErrorsServiceImpl personCustomErrorsService;

    @Operation(summary = "Ejemplo de POST para mensajes de error")
    @PostMapping
    public ResponseEntity<PersonCustomErrorsDto> create(
            @Valid @RequestBody PersonCustomErrorsDto personCustomErrorsDto
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                personCustomErrorsService.create(personCustomErrorsDto)
        );
    }
}
