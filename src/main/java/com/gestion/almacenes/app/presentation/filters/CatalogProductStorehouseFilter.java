package com.gestion.almacenes.app.presentation.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class CatalogProductStorehouseFilter {
private Integer id;
private Integer storehouseId;
private Integer productId;
private String search;
}
