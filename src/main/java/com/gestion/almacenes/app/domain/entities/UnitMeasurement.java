package com.gestion.almacenes.app.domain.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "unit_measurement")
@Schema( name = "Entity UnitMeasurement (Unidad de medida)")
public class UnitMeasurement extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 30, nullable = false)
  private String code;

  @Column(length = 100, nullable = false)
  private String name;

}
