package org.example.cupon_api;

import org.example.cupon_api.application.GetByIdUseCase;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestGetByIdUseCase {


    @Mock
    RepositoryCuponOutPort repositoryCuponOutPort;

    @InjectMocks
    GetByIdUseCase useCase;


    @Test
    void shouldFindCouponByIdSuccessfully() {

        UUID id = UUID.randomUUID();

        Cupon cupon = Cupon.builder()
                .id(id)
                .codigo("ABC123")
                .activado(true)
                .build();

        when(repositoryCuponOutPort.buscarId(id)).thenReturn(Optional.of(cupon));

        Cupon resultado = useCase.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("ABC123", resultado.getCodigo());

        verify(repositoryCuponOutPort).buscarId(id);
    }

    @Test
    void shouldThrowExceptionWhenCouponIdIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> useCase.findById(null));

        assertEquals("El id no puede ser nulo", exception.getMessage());

        verify(repositoryCuponOutPort, never())
                .buscarId(any());
    }


}
