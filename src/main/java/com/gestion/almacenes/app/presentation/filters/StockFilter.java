package com.gestion.almacenes.app.presentation.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@ToString
@Getter
public class StockFilter {
private Integer id;
private Integer storehouseId;
private Integer productId;
private Double quantityInStock;
private Double minimumStock;
private Double maximumStock;
private Boolean stockAlert;
private Boolean validExpirationDate;
private BigDecimal unitPrice;
private String search;
}
