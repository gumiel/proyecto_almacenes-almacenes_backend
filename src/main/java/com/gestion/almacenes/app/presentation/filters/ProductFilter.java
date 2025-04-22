package com.gestion.almacenes.app.presentation.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@ToString
@Getter
public class ProductFilter {
private Integer id;
private String code;
private String name;
private String description;
private Integer unitMeasurementId;
private BigDecimal approximateUnitPrice;
private String search;
}
