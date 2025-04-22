package com.gestion.almacenes.app.presentation.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@ToString
@Getter
public class OrderProductDetailFilter {
private Integer id;
private Integer orderProductId;
private Integer stockId;
private Double quantity;
private String codeProduct;
private LocalDate expirationDateProduct;
private BigDecimal price;
private String search;
}
