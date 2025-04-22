package com.gestion.almacenes.app.presentation.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SupplierPojo extends AuditablePojo {
private Integer id;
private LocalDateTime registerDate;
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
}
