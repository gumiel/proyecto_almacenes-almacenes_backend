package com.gestion.almacenes.app.presentation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Schema( name = "DTO OrderProductDto (DTO Orden de productos) ")
public class OrderProductDto {

  private Integer id;

  private Integer orderProductId;

  @Size(max = 30, message = "{field} Código {cannot.have.more.than} 30 {characters}")
  @Schema(description = "Codigo de la orden que puede ser generado automaticamente o manualmente.")
  private String code;

  @Schema(description = "Fecha de registro de la orden.")
  private LocalDate registrationDate;
  @Schema(description = "Hora de registro de la orden.")
  private LocalTime registrationTime;

  @Size(max = 500, message = "{field} Descripción {cannot.have.more.than} 500 {characters}")
  @Schema(description = "Descripcion de la orden.")
  private String description;

  @NotNull(message = "{field} Identificador de Almacen {is.required}")
  @Schema(description = "Relacionador que identifica el almacen.")
  private Integer storehouseId;

  @NotNull(message = "{field} Identificador del tipo de orden {is.required}")
  @Schema(description = "Relacionador que identifica el tipo de orden.")
  private Integer orderProductTypeId;

  @Schema(description = "Relacionador del Identificador de proveedor.")
  private Integer supplierId;

  private String status;

}
