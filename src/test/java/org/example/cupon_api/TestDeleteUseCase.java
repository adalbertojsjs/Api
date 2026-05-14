package org.example.cupon_api;


import org.example.cupon_api.application.DeleteCuponUseCase;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestDeleteUseCase {

    @Mock
    RepositoryCuponOutPort repositoryCuponOutPort;

    @InjectMocks
    DeleteCuponUseCase useCase;


    @Test
    void shouldDeleteCouponSuccessfully() {

        UUID id = UUID.randomUUID();

        Cupon cupon = Cupon.builder()
                .id(id)
                .activado(true)
                .build();

        when(repositoryCuponOutPort.buscarId(id))
                .thenReturn(Optional.of(cupon));

        useCase.eliminarCupon(id);

        ArgumentCaptor<Cupon> captor =
                ArgumentCaptor.forClass(Cupon.class);

        verify(repositoryCuponOutPort).guardarCupon(captor.capture());

        Cupon cuponActualizado = captor.getValue();

        assertFalse(cuponActualizado.isActivado());
    }


    @Test
    void shouldThrowExceptionWhenCouponIdIsNull() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.eliminarCupon(null)
        );

        assertEquals("El id del cupon no puede ser nulo", exception.getMessage()
        );

        verify(repositoryCuponOutPort, never())
                .guardarCupon(any());
    }


    @Test
    void shouldThrowExceptionWhenCouponIdDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(repositoryCuponOutPort.buscarId(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> useCase.eliminarCupon(id)
        );

        assertEquals("El id no fue encontrado", exception.getMessage()
        );

        verify(repositoryCuponOutPort, never())
                .guardarCupon(any());
    }


    @Test
    void shouldThrowExceptionWhenCouponIsAlreadyDisabled() {

        UUID id = UUID.randomUUID();

        Cupon cupon = Cupon.builder()
                .id(id)
                .activado(false)
                .build();

        when(repositoryCuponOutPort.buscarId(id))
                .thenReturn(Optional.of(cupon));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> useCase.eliminarCupon(id)
        );

        assertEquals("El id no fue encontrado", exception.getMessage()
        );

        verify(repositoryCuponOutPort, never())
                .guardarCupon(any());
    }
}
