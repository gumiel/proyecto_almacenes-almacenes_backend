package com.gestion.almacenes.app.presentation.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class ConfigurationParameterFilter {
private Integer id;
private String code;
private String value;
private String description;
private String search;
}
