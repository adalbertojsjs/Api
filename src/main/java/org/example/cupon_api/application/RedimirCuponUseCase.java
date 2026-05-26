package org.example.cupon_api.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.in.RedimirCuponInPort;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

import static java.util.Objects.isNull;


@Transactional
@RequiredArgsConstructor
@Component
public class RedimirCuponUseCase implements RedimirCuponInPort {


    private final RepositoryCuponOutPort repositoryCuponOutPort;


    @Transactional
    public Cupon redimir(UUID id){

        if (isNull(id)){
            throw new RuntimeException();
        }

        var cupon = repositoryCuponOutPort.buscarId(id)
                .orElseThrow();

        if (cupon.getFecha_Vencimiento().isBefore(LocalDate.now())){
            throw new RuntimeException("EL cupon esta vencido");
        }

        if (!cupon.isActivado()){
            throw new RuntimeException("EL cupon no esta activo");
        }

        if (cupon.isRedimido()){
            throw new RuntimeException("Ya esta redimido");
        }

        cupon.setRedimido(true);

        // Si luego creo el atributo fechaRedencion
        // cupon.setFechaRedencion(LocalDate.now());

        var cuponSaved = repositoryCuponOutPort.guardarCupon(cupon);

        return cuponSaved;
    }
}
