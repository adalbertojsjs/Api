package org.example.cupon_api.application;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.in.ActualizarDescuentoInPort;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static java.util.Objects.isNull;


@Transactional
@Component
@RequiredArgsConstructor
public class ActualizarDescuentoUseCase implements ActualizarDescuentoInPort {


    private final RepositoryCuponOutPort repositoryCuponOutPort;


    @Transactional
    public Cupon actualizarDescunto(UUID id, BigDecimal descuento){

        if (isNull(id)){
            throw  new IllegalArgumentException("");
        }

        var cupon = repositoryCuponOutPort.buscarId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id no encontrado"));

        if (descuento.compareTo(BigDecimal.valueOf(0.10)) < 0
                || descuento.compareTo(BigDecimal.valueOf(0.50)) >0){
            throw  new IllegalArgumentException("Descuento Invalido");
        }

        if (cupon.isRedimido()){
           throw  new IllegalArgumentException("El Cupon ya fue redimido");
        }

        if (cupon.getFecha_Vencimiento().isBefore(LocalDate.now())){
            throw  new IllegalArgumentException("El cupon esta vencido");
        }

        if (cupon.isPublicado()){
            if (descuento.compareTo(cupon.getDescuento()) < 0){
                throw  new IllegalArgumentException("No se puede disminuir el descuento");
            }
        }

            cupon.setDescuento(descuento);
          //  cupon.setFachaModificacion(LocalDateTime.now());


       var cuponSaved = repositoryCuponOutPort.guardarCupon(cupon);

        return cuponSaved;

    }

}
