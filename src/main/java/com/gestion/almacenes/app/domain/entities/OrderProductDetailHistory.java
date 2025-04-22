package com.gestion.almacenes.app.domain.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Detalle de ordenes
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_product_detail_history")
@Schema( name = "Entity OrderProductDetailHistory (Historial del detalle de Orden)")
@AllArgsConstructor
public class OrderProductDetailHistory extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "order_product_id")
  private OrderProduct orderProduct;

  @ManyToOne
  @JoinColumn(name="order_product_detail_id")
  private OrderProductDetail orderProductDetail;

  @ManyToOne
  @JoinColumn(name = "stock_id")
  private Stock stock;

  private LocalDateTime dateTimeRegister;

  @Column(columnDefinition = "NUMERIC(18,4)")
  private BigDecimal quantity;

  @Column(columnDefinition = "NUMERIC(18,4)")
  @Schema(description = "Precio para el ingreso o la salida")
  private BigDecimal price;

  @Column(columnDefinition = "NUMERIC(18,4)")
  @Schema(description = "Precio promedio de ingreso o salida (#2 Segundo calculo)")
  private BigDecimal averagePrice;

  @Column(columnDefinition = "NUMERIC(18,4)")
  @Schema(description = "Saldo fisico (#1 Primer calculo) ")
  private BigDecimal physicalBalance;

  @Column(columnDefinition = "NUMERIC(18,4)")
  @Schema(description = "Saldo valorado (#3 Tercer calculo)")
  private BigDecimal valuedBalance;
}