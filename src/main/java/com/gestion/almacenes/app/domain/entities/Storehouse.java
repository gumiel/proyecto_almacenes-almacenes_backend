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
@Table(name = "storehouse")
@Schema( name = "Entity Storehouse (Almacen)")
public class Storehouse extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(length = 30, nullable = false)
  private String code;
  @Column(length = 100, nullable = false)
  private String name;
  @Column(length = 500, nullable = false)
  private String description;
  @ManyToOne
  @JoinColumn(name = "storehouse_type_id")
  private StorehouseType storehouseType;

}
