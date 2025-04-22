package com.gestion.almacenes.app.presentation.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CatalogProductStorehousePojo extends AuditablePojo {
private Integer id;
private Integer storehouseId;
private Integer productId;
}
