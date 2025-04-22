package com.gestion.almacenes.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentPojo {
   private String name;
   private String file;
   private String typeFile;
}

