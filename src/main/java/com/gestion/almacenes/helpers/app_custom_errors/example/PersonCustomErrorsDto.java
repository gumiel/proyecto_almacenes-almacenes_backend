package com.gestion.almacenes.helpers.app_custom_errors.example;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonCustomErrorsDto {
    @Schema(description = "campo código", example = "CODE-1")
    private String code;
    String name;
    Integer year;
    LocalDate registerDate;
}
