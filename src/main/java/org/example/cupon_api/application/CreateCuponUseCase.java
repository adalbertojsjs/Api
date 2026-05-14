package org.example.cupon_api.application;

import lombok.RequiredArgsConstructor;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.in.CreateCuponInPort;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;


@RequiredArgsConstructor
@Component
public class CreateCuponUseCase implements CreateCuponInPort {

    private final RepositoryCuponOutPort repositoryCuponOutPort;


    @Override
    public Cupon crearCupon(Cupon cupon) {

        if(cupon.getCodigo() == null){
            throw  new IllegalArgumentException("El codigo no puede ser nulo");
        }

        if (cupon.getFecha_Vencimiento() == null) {
            throw new IllegalArgumentException("El cupón no tiene una fecha de vencimiento asignada.");

        }
            if (cupon.getDescuento().compareTo(BigDecimal.valueOf(0.5)) < 0) {
            throw new IllegalArgumentException("El descuento mínimo es 0.5");
        }
        if (cupon.getFecha_Vencimiento().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de expiración no puede estar en el pasado");
        }

        var codigo6 = procesarCodigoCupon(cupon.getCodigo());

        if (repositoryCuponOutPort.existsByCodigo(codigo6)) {
            throw new IllegalArgumentException("No se permite duplicado");
        }

        var cuponRegistrado =  Cupon.builder()
                .codigo(codigo6)
                .descripcion(cupon.getDescripcion())
                .descuento(cupon.getDescuento())
                .fecha_Vencimiento(cupon.getFecha_Vencimiento())
                .publicado(cupon.isPublicado())
                .activado(true)
                .redimido(false)
                .build();



        return repositoryCuponOutPort.guardarCupon(cuponRegistrado);

    }

            private String procesarCodigoCupon(String codigo) {

                String codigoLimpio = codigo.replaceAll("[^a-zA-Z0-9]", "");

                if (codigoLimpio.length() < 6){
                    throw new IllegalArgumentException("El código del cupón debe tener al menos 6 caracteres.");

                }

                return codigoLimpio.substring(0, 6).toUpperCase().trim();
            }
    }

