package org.example.cupon_api.domain.port.in;

import org.example.cupon_api.domain.model.Cupon;

import java.time.LocalDate;
import java.util.UUID;

public interface ExtenderVencimientoInPort {

    public Cupon extenderFecha(UUID id, LocalDate nuevaFecha);

    }
