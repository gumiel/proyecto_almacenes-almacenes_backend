package com.gestion.almacenes.app.presentation.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@ToString
@Getter
public class OrderProductFilter {
private Integer id;
private String code;
private LocalDate registrationDate;
private LocalTime registrationTime;
private Integer storehouseId;
private String description;
private Integer orderProductTypeId;
private String status;
private Integer supplierId;
private String search;
}
