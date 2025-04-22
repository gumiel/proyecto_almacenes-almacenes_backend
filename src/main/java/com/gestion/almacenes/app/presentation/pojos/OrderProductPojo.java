package com.gestion.almacenes.app.presentation.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductPojo {
private Integer id;
private String code;
private LocalDate registrationDate;
private LocalTime registrationTime;
private Integer storehouseId;
private String description;
private Integer orderProductTypeId;
private String status;
private Integer supplierId;
}
