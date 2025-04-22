package com.gestion.almacenes.app.presentation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Schema( name = "DTO StockDto (DTO Stock de producto) ")
public class StockDto {

  private Integer id;

  @NotNull
  @Schema(description = "Relacionador que identifica al almacen.")
  private Integer storehouseId;
  @NotNull
  @Schema(description = "Relacionador que identifica al producto.")
  private Integer productId;
  @Min(0)
  @NotNull
  @Schema(description = "Monto actual en stock.")
  private Double quantityInStock;
  @Min(0)
  @NotNull
  @Schema(description = "Monto minimo que puede tener el almacen en stock.")
  private Double minimumStock;
  @Min(0)
  @NotNull
  @Schema(description = "Monto maximo que puede tener el almacen en stock.")
  private Double maximumStock;
  @NotNull
  @Schema(description = "Indica si tendra alertas cuando los montos minimos o maximos sobre pasen.")
  private Boolean stockAlert;
  @Min(0)
  @NotNull
  @Schema(description = "El precio unitario para la salida del almacen")
  private BigDecimal unitPrice;

  private Boolean validExpirationDate;

}
