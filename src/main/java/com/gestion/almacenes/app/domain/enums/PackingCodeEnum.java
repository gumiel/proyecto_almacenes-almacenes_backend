package com.gestion.almacenes.app.domain.enums;

public enum PackingCodeEnum {
  NA("n/a");
  private final String code;
  PackingCodeEnum(String code) {
    this.code = code;
  }
  public String getCode() {
    return code;
  }
}
