package org.example.cupon_api.domain.port.in;

import java.util.UUID;

public interface DeleteCuponInport {


    void eliminarCupon(UUID codigo);
}
