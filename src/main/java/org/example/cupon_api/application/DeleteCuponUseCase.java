package org.example.cupon_api.application;

import lombok.RequiredArgsConstructor;
import org.example.cupon_api.domain.port.in.DeleteCuponInport;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.springframework.stereotype.Component;

import java.util.UUID;


@RequiredArgsConstructor
@Component
public class DeleteCuponUseCase implements DeleteCuponInport {

    private final RepositoryCuponOutPort repositoryCuponOutPort;

    @Override
    public void eliminarCupon(UUID id) {
        if(id == null){
            throw  new IllegalArgumentException("El id del cupon no puede ser nulo");
        }

        var cupon = repositoryCuponOutPort.buscarId(id)
                .orElseThrow(() -> new IllegalArgumentException("El id no fue encontrado"));

        if (!cupon.isActivado()) {
            throw new IllegalStateException("El id no fue encontrado");
        }

        cupon.setActivado(false);

        repositoryCuponOutPort.guardarCupon(cupon);
    }
}
