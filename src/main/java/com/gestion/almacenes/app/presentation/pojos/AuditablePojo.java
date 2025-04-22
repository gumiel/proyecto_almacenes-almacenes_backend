package com.gestion.almacenes.app.presentation.pojos;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AuditablePojo {
  private LocalDateTime createdDate;
  private String createdBy;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;
  private String lastModifiedBy;
  private Boolean active = true;

}