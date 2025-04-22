package com.gestion.almacenes.app.presentation.dtos;

import com.gestion.almacenes.app.domain.entities.TestType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Schema( name = "DTO Test (DTO pruebas) ")
public class TestDto {

    private Integer id;
    private String code;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private LocalDateTime dateRegister;
    private Integer year;
    private BigDecimal price;
    private Boolean disabled;
    private TestType testType;
}
