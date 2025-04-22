package com.gestion.almacenes.app.presentation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Schema( name = "DTO OrderProductDetailDto (DTO Detalle de Orden) ")
public class OrderProductDetailDto {

  private Integer id;

  @Schema(description = "Relacionador que identifica el stock.")
  private Integer stockId;

  @NotNull(message = "{field} Identificador de producto {is.required}")
  @Schema(description = "Relacionador que identifica el producto")
  private Integer productId;

  @NotNull(message = "{field} cantidad {is.required}")
  @Min(0)
  @Schema(description = "Cantidad que se esta solicitando")
  private Double quantity;

  @NotNull(message = "{field} Identificador de Orden {is.required}")
  @Schema(description = "Relacionador que identifica la orden de producto")
  private Integer orderProductId;

  @Schema(description = "Codigo del producto que ingresa o sale de almacen")
  private String codeProduct;

  @Schema(description = "Fecha de expiración del producto que ingresa o sale de almacen")
  private LocalDate expirationDateProduct;

  @Schema(description = "Precio del producto")
  private BigDecimal price;

}
