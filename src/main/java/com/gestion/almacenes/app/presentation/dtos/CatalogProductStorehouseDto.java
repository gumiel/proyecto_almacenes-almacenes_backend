package com.gestion.almacenes.app.presentation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema( name = "DTO CatalogProductStorehouseDto (DTO Catalago de productos por almacen)")
public class CatalogProductStorehouseDto {

    private Integer id;

    @Schema(description = "Identificador de Catalogo de productos por almacen")
    private Integer catalogProductStorehouseId;

    @Schema(description = "Identificador de Almacen")
    @NotNull( message = "{field} Identificador de Almacen {is.required}" )
    private Integer storehouseId;

    @Schema(description = "Identificador de Producto")
    @NotNull( message = "{field} Identificador de Producto {is.required}" )
    private Integer productId;

}