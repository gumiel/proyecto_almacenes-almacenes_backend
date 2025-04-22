package com.gestion.almacenes.app.domain.enums;

public enum OrderProductTypeActionEnum {
  RECEIPT("ingresos"), // Ingresos
  DISPATCH("salidas"); // Salidas
  private final String value;
  OrderProductTypeActionEnum(String value) {
    this.value = value;
  }
  public String value() {
    return value;
  }
}
