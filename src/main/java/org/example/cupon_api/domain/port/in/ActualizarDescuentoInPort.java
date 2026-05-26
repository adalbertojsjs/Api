package org.example.cupon_api.domain.port.in;

import org.example.cupon_api.domain.model.Cupon;

import java.math.BigDecimal;
import java.util.UUID;

public interface ActualizarDescuentoInPort {


    public Cupon actualizarDescunto(UUID id, BigDecimal descuento);

    }
