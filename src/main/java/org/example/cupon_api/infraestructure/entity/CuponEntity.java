package org.example.cupon_api.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cupon")
public class CuponEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String codigo;

    private String descripcion;

    private boolean activado;

    private BigDecimal descuento;

    private LocalDate fecha_Vencimiento;

    private boolean publicado;

    private boolean redimido;

}
