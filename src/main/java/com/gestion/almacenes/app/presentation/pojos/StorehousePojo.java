
package com.gestion.almacenes.app.presentation.pojos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema( name = "POJO StoreHousePojo (POJO Almacen) ")
public class StorehousePojo extends AuditablePojo {

  private Integer id;
  private String code;
  private String name;
  private String description;
  private Integer storehouseTypeId;
  private String storehouseTypeCode;
  private String storehouseTypeName;

}
