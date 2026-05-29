package org.example.cupon_api.application;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.in.ExtenderVencimientoInPort;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

import static java.util.Objects.isNull;

@Transactional
@Component
@RequiredArgsConstructor
public class ExtenderVencimientoUseCase implements ExtenderVencimientoInPort {


    private final RepositoryCuponOutPort repositoryCuponOutPort;


    @Transactional
    public Cupon extenderFecha(UUID id, LocalDate nuevaFecha){

        if (isNull(id)) {
            throw new IllegalArgumentException("Id Invalido");
        }

        if (isNull(nuevaFecha)){
            throw new IllegalArgumentException("Fecha Invalida");
        }

        if (nuevaFecha.isAfter(LocalDate.now().plusDays(90))){
            throw  new IllegalArgumentException("La fecha no se puede extender mas de 90 dias");
        }

        var cupon = repositoryCuponOutPort.buscarId(id).
                orElseThrow(() -> new IllegalArgumentException("Id no encontrado"));

        if (cupon.isRedimido()){
            throw new IllegalArgumentException("El cupon esta redimido");
        }

        if (nuevaFecha.isEqual(cupon.getFecha_Vencimiento())){
            throw new IllegalArgumentException("La fecha no puede ser igual a la anterior");
        }

        if (cupon.getFecha_Vencimiento().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("El cupon esta vencido");
        }

//        if (cupon.isPublicado()){
//            cupon.setFechaModificasion(LocalDate.now());
//        }

        if (!nuevaFecha.isAfter(cupon.getFecha_Vencimiento())){
            throw  new IllegalArgumentException("La fecha debe ser posterior a la actual");
        }

        cupon.setFecha_Vencimiento(nuevaFecha);


        return repositoryCuponOutPort.guardarCupon(cupon);

    }
}
