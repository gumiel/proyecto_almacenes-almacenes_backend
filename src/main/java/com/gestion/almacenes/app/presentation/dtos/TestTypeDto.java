package com.gestion.almacenes.app.presentation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema( name = "DTO TestType (DTO tipo de pruebas) ")
public class TestTypeDto {

    private Integer id;
    private String code;
    private String name;

}
