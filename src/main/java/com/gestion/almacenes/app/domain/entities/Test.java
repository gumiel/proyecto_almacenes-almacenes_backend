package com.gestion.almacenes.app.domain.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "test_type")
@Schema( name = "Entity TestType para hacer pruebas de tipos (Tipos de Pruebas)")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 30, nullable = false)
    private String code;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 500, nullable = false)
    private String description;
    private LocalDate date;
    private LocalTime time;
    private LocalDateTime dateRegister;
    private Integer year;
    private BigDecimal price;
    private Boolean disabled;
    @ManyToOne
    @JoinColumn(name = "type_type_id")
    private TestType testType;
}
