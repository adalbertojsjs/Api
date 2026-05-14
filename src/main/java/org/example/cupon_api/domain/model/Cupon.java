package org.example.cupon_api.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Cupon{

    private UUID id;

    private String codigo;

    private String descripcion;

    private boolean activado;

    private BigDecimal descuento;

    private LocalDate fecha_Vencimiento;

    private boolean publicado;

    private boolean redimido;
}
