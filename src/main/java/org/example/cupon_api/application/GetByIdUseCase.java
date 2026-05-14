package org.example.cupon_api.application;

import lombok.RequiredArgsConstructor;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.in.GetByIdInPort;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetByIdUseCase implements GetByIdInPort {

    private final RepositoryCuponOutPort repositoryCuponOutPort;

    @Override
    public Cupon findById(UUID uuid) {

        if (uuid == null){
            throw  new IllegalArgumentException("El id no puede ser nulo");
        }

        var cupon = repositoryCuponOutPort.buscarId(uuid)
                .filter(Cupon::isActivado)
                .orElseThrow(() -> new IllegalArgumentException("El id no fue encontrado"));

        return cupon;
    }
}
