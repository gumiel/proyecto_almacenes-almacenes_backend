package com.gestion.almacenes.app.presentation.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class SupplierFilter {
private Integer id;
private Integer registerDateId;
private String supplierCode;
private String supplierPhoneNumber;
private String supplierCelNumber;
private String companyName;
private String address;
private String companyDescription;
private Boolean enable;
private String ownerNames;
private String ownerSurname;
private String email;
private String search;
}
