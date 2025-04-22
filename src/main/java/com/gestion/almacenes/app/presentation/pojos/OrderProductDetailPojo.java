package com.gestion.almacenes.app.presentation.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductDetailPojo {
private Integer id;
private Integer orderProductId;
private Integer stockId;
private Double quantity;
private String codeProduct;
private LocalDate expirationDateProduct;
private BigDecimal price;
}
