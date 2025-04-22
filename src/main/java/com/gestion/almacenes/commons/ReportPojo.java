
package com.gestion.almacenes.commons;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;

@Data
@NoArgsConstructor
public class ReportPojo {
  private String fileName;
  private ByteArrayInputStream stream;
  private int length;
  public ReportPojo(String fileName, ByteArrayInputStream stream, int length) {
    this.fileName = fileName;
    this.stream = stream;
    this.length = length;
  }
}
