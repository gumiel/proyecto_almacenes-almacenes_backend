package com.gestion.almacenes.app.presentation.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductPojo extends AuditablePojo {
private Integer id;
private String code;
private String name;
private String description;
private Integer unitMeasurementId;
private BigDecimal approximateUnitPrice;
}
