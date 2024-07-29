package com.gestion.almacenes.commons.exception.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorListResponse {

  private int code;
  private String status;
  private String path;
  private List<FieldErrorModel> errorList;

}

