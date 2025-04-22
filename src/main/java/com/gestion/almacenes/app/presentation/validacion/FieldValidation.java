package com.gestion.almacenes.app.presentation.validacion;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class FieldValidation {
  String type;
  String messageGeneric;
  String messageCustom;
}
