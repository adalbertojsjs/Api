package org.example.cupon_api.domain.port.in;

import org.example.cupon_api.domain.model.Cupon;

public interface CreateCuponInPort {

    Cupon crearCupon(Cupon cupon);
}
