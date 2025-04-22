package com.gestion.almacenes.app.domain.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Detalle de ordenes
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_product_detail")
@Schema( name = "Entity OrderProductDetail (Detalle de Orden)")
@AllArgsConstructor
public class OrderProductDetail extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "order_product_id")
  private OrderProduct orderProduct;

  @ManyToOne
  @JoinColumn(name = "stock_id")
  private Stock stock;

  @Column(columnDefinition = "NUMERIC(18,4)")
  private Double quantity;

  private String codeProduct;

  private LocalDate expirationDateProduct;

  @Column(columnDefinition = "NUMERIC(18,4)")
  @Schema(description = "Precio para el ingreso o la salida")
  private BigDecimal price;

}