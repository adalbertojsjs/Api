package org.example.cupon_api;


import org.example.cupon_api.application.CreateCuponUseCase;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestCreateCuponUseCase {

    @Mock
    RepositoryCuponOutPort repositoryCuponOutPort;

    @InjectMocks
    CreateCuponUseCase useCase;

    @Test
    void shouldCreateCouponSuccessfully(){

        var cupon = Cupon.
                builder().
                codigo("AHGDGjjj{q@").
                descripcion("random3333").
                descuento(BigDecimal.valueOf(0.9)).
                fecha_Vencimiento(LocalDate.of(2026, 5, 13)).
                publicado(false).
                build();

        when(repositoryCuponOutPort.guardarCupon(any())).thenReturn(cupon);

        useCase.crearCupon(cupon);

        ArgumentCaptor<Cupon> captor = ArgumentCaptor.forClass(Cupon.class);

        verify(repositoryCuponOutPort).guardarCupon(captor.capture());

        Cupon cupon1 = captor.getValue();

        assertEquals("random3333", cupon1.getDescripcion());
        assertEquals(BigDecimal.valueOf(0.9),cupon1.getDescuento());
        assertEquals(LocalDate.of(2026, 5, 13), cupon1.getFecha_Vencimiento());

        verify(repositoryCuponOutPort).guardarCupon(cupon1);
    }


    @Test
    void shouldThrowExceptionWhenCodigoIsNull(){

        var cupon = Cupon.
                builder().
                codigo(null).
                descripcion("random3333").
                descuento(BigDecimal.valueOf(0.9)).
                fecha_Vencimiento(LocalDate.of(2026, 5, 13)).
                publicado(false).
                build();

       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
               ()-> useCase.crearCupon(cupon) );

       assertEquals("El codigo no puede ser nulo", exception.getMessage());

       verify(repositoryCuponOutPort, never()).guardarCupon(any());
    }


    @Test
    void shouldThrowExceptionWhenCouponCodeIsDuplicated(){

        var cupon = Cupon.
                builder().
                codigo("ABC123").
                descripcion("random3333").
                descuento(BigDecimal.valueOf(0.9)).
                fecha_Vencimiento(LocalDate.of(2026, 5, 13)).
                publicado(false).
                build();

        when(repositoryCuponOutPort.existsByCodigo("ABC123"))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.crearCupon(cupon)
        );

        assertEquals("No se permite duplicado", exception.getMessage());

        verify(repositoryCuponOutPort).existsByCodigo("ABC123");
    }


    @Test
    void shouldThrowExceptionWhenExpirationDateIsNull(){

        var cupon = Cupon.
                builder().
                codigo("ABC1eeeee").
                fecha_Vencimiento(null).
                build();


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.crearCupon(cupon));

        assertEquals("El cupón no tiene una fecha de vencimiento asignada.", exception.getMessage());

        verify(repositoryCuponOutPort, never()).guardarCupon(any());
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsLessThanMinimum() {

        var cupon = Cupon.
                builder().
                codigo("ABC1eeeee").
                descuento(BigDecimal.valueOf(0.2)).
                fecha_Vencimiento(LocalDate.of(2026, 5, 13)).
                build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.crearCupon(cupon)
        );

        assertEquals("El descuento mínimo es 0.5", exception.getMessage());
    }


    @Test
    void shouldThrowExceptionWhenExpirationDateIsInThePast() {


        var cupon = Cupon.
                builder().
                codigo("ABC1eeeee").
                descuento(BigDecimal.valueOf(0.7)).
                fecha_Vencimiento(LocalDate.of(2026, 2, 13)).
                build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.crearCupon(cupon)
        );

        assertEquals(
                "La fecha de expiración no puede estar en el pasado",
                exception.getMessage()
        );
    }

    @Test
    void shouldProcessCouponCodeSuccessfully() {

        var cupon = Cupon.builder()
                .codigo("ab@12#cd$")
                .descripcion("coupon")
                .descuento(BigDecimal.valueOf(1.0))
                .fecha_Vencimiento(LocalDate.of(2026, 5, 13))
                .build();

        when(repositoryCuponOutPort.existsByCodigo(anyString()))
                .thenReturn(false);

        useCase.crearCupon(cupon);

        ArgumentCaptor<Cupon> captor = ArgumentCaptor.forClass(Cupon.class);

        verify(repositoryCuponOutPort).guardarCupon(captor.capture());

        Cupon cuponGuardado = captor.getValue();

        assertEquals("AB12CD", cuponGuardado.getCodigo());
    }

    @Test
    void shouldThrowExceptionWhenCouponCodeHasLessThanSixCharacters() {

        var cupon = Cupon.builder()
                .codigo("a@1$")
                .descripcion("coupon")
                .descuento(BigDecimal.valueOf(1.0))
                .fecha_Vencimiento(LocalDate.of(2026, 5, 13))
                .build();


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.crearCupon(cupon)
        );

        assertEquals("El código del cupón debe tener al menos 6 caracteres.", exception.getMessage());

        verify(repositoryCuponOutPort, never()).guardarCupon(any());
    }
}
