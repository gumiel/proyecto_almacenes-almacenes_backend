package com.gestion.almacenes.app.domain.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_product")
@Schema( name = "Entity OrderProduct (Orden)")
@AllArgsConstructor
public class OrderProduct extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String code;

  private LocalDate registrationDate;
  private LocalTime registrationTime;

  @ManyToOne
  @JoinColumn(name = "storehouse_id")
  private Storehouse storehouse;

  @Column(length = 500)
  private String description;

  @ManyToOne
  @JoinColumn(name = "order_product_type_id")
  private OrderProductType orderProductType;

  private String status;

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  private Supplier supplier;

}