package com.gestion.almacenes.app.presentation.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductTypePojo extends AuditablePojo {
private Integer id;
private String code;
private String name;
private String description;
private String action;
}
