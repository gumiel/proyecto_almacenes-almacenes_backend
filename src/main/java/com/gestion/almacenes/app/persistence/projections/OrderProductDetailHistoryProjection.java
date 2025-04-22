package com.gestion.almacenes.app.persistence.projections;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderProductDetailHistoryProjection {
    @Schema(description = "Identificador de histórico")
    Integer getId(); // Identificador de Cotización

    @Schema(description = "Identificador de la orden")
    Integer getOrderProductId();
    @Schema(description = "Identificador de detalle")
    Integer getOrderProductDetailId();
    @Schema(description = "Identificador de Stock")
    Integer getStockId();
    @Schema(description = "Fecha del registro del historico")
    LocalDateTime getDateTimeRegister();
    @Schema(description = "Cantidad registrada en el ingreso o salida")
    BigDecimal getQuantity();
    @Schema(description = "Precio para el ingreso o la salida")
    BigDecimal getPrice();
    @Schema(description = "Precio promedio de ingreso o salida (#2 Segundo calculo)")
    BigDecimal getAveragePrice();
    @Schema(description = "Saldo fisico (#1 Primer calculo)")
    BigDecimal getPhysicalBalance();
    @Schema(description = "Saldo valorado (#3 Tercer calculo)")
    BigDecimal getValuedBalance();
    @Schema(description = "Codigo del Almacen")
    String getStorehouseCode();
    @Schema(description = "Nombre del almacen")
    String getStorehouseName();
    @Schema(description = "Codigo de la orden")
    String getOrderProductCode();
    @Schema(description = "Descripción de la orden")
    String getOrderProductDescription();
    @Schema(description = "Precio ingresado en el detalle de la orden")
    BigDecimal getOrderProductDetailPrice();
    @Schema(description = "Cantidad ingresada en el detalle de la orden")
    BigDecimal getOrderProductDetailQuantity();
    @Schema(description = "Accion de la orden que puede ser: RECEIPT = Ingreso a almacen  o DISPATCH = Salida de almacen")
    String getOrderProductTypeAction();

}
