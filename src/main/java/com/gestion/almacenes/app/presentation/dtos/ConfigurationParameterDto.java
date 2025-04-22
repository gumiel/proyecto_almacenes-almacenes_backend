package com.gestion.almacenes.app.presentation.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConfigurationParameterDto {
private Integer id;
private String code;
private String value;
private String description;
}
