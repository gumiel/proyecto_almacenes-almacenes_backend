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
@Table(name = "catalog_product_storehouse")
@Schema( name = "Entity_CatalogProductStorehouse", description = "Tabla con la relación entre los productos y los almacenes.")
public class  CatalogProductStorehouse extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "storehouse_id")
  private Storehouse storehouse;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

}