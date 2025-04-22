package com.gestion.almacenes.app.presentation.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class StockPojo extends AuditablePojo {
    private Integer id;
    private Double quantityInStock;
    private Double minimumStock;
    private Double maximumStock;
    private Boolean stockAlert;
    private Boolean validExpirationDate;
    private BigDecimal unitPrice;
    private Integer storehouseId;
    private String storehouseName;
    private Integer productId;
    private String productName;
}
