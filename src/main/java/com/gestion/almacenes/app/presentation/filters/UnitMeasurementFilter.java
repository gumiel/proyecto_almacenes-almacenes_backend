package com.gestion.almacenes.app.presentation.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class UnitMeasurementFilter {
private Integer id;
private String code;
private String name;
private String search;
}
