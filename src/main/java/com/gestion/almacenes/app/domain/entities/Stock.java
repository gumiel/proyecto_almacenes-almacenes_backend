package com.gestion.almacenes.app.domain.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stock")
@Schema( name = "Entity Stock (Stock de producto)")
@Builder
@AllArgsConstructor
public class Stock extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "storehouse_id")
  private Storehouse storehouse;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  @Column(nullable = false, columnDefinition = "NUMERIC(18, 4)")
  private Double quantityInStock;
  @Column(nullable = false, columnDefinition = "NUMERIC(18, 4)")
  private Double minimumStock;
  @Column(nullable = false, columnDefinition = "NUMERIC(18, 4)")
  private Double maximumStock;
  @Column(nullable = false)
  private Boolean stockAlert;
  private Boolean validExpirationDate;

  @Column(columnDefinition = "NUMERIC(18,4)")
  @Schema(description = "Precio unitario actual")
  private BigDecimal unitPrice;

}
