package org.example.cupon_api.application;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.in.PublicarCuponInPort;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static java.util.Objects.isNull;

@Transactional
@RequiredArgsConstructor
@Component
public class PublicarCuponUseCase  implements PublicarCuponInPort {

    private final RepositoryCuponOutPort repositoryCuponOutPort;



    @Transactional
    public Cupon publicar(UUID uuid) {

        if (isNull(uuid)){
            throw new IllegalArgumentException("EL id es nulo");
        }

        var cupon = repositoryCuponOutPort.buscarId(uuid).
                orElseThrow(() -> new IllegalArgumentException("El cupon no se encontro"));

        if (cupon.isPublicado()){
            throw new IllegalArgumentException("El cupon esta publicado");
        }

        if (!cupon.isActivado()){
            throw  new IllegalArgumentException("El cupon no esta activo");
        }

        if (cupon.getFecha_Vencimiento().isBefore(LocalDate.now())){
            throw  new IllegalArgumentException("El cupon esta vencido");
        }

        if (cupon.getDescuento().compareTo(BigDecimal.valueOf(0.15)) <0){
            throw  new IllegalArgumentException("El descuento del cupon es menor del 15%");
        }

        cupon.setPublicado(true);
        // cupon.setFechaPublicacion(LocalDate.now());

        return repositoryCuponOutPort.guardarCupon(cupon);

    }
}
