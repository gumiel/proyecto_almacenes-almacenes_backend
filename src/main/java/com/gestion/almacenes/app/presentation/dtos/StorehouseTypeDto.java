package com.gestion.almacenes.app.presentation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema( name = "DTO StorehouseTypeDto (DTO Tipo de almacen) ")
public class StorehouseTypeDto {

  private Integer id;

  @NotNull(message = "{field} Código {is.required}")
  @NotBlank(message = "{field} Código {cant.be.empty}")
  @Size(max = 30, message = "{field} Código {cannot.have.more.than} 30 {characters}")
  @Schema(description = "Codigo del tipo de almacen.")
  private String code;

  @NotNull(message = "{field} Nombre {is.required}")
  @NotBlank(message = "{field} Nombre {cant.be.empty}")
  @Size(max = 100, message = "{field} Nombre {cannot.have.more.than} 100 {characters}")
  @Schema(description = "Nombre del tipo de almacen.")
  private String name;
}
