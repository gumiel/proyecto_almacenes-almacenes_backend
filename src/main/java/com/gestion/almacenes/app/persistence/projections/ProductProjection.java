package com.gestion.almacenes.app.persistence.projections;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public interface ProductProjection {

  Integer getId();
  @Schema(description = "Código del producto")
  String getCode();
  @Schema(description = "nombre del producto")
  String getName();
  @Schema(description = "Descripcion del producto")
  String getDescription();
  @Schema(description = "Precio estimado que puede valer el producto")
  BigDecimal getApproximateUnitPrice();
}
