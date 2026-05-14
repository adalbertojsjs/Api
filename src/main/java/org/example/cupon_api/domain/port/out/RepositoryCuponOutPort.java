package org.example.cupon_api.domain.port.out;

import org.example.cupon_api.domain.model.Cupon;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryCuponOutPort {

    Optional<Cupon> buscarId(UUID uuid);

    Cupon guardarCupon(Cupon cupon);

    boolean existsByCodigo(String codigo);

}
