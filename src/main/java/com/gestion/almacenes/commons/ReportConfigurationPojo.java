package com.gestion.almacenes.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportConfigurationPojo {
  private String code;
  Map<String, Object> params;
}
