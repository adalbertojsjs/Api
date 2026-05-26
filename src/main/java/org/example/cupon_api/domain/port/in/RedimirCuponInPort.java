package org.example.cupon_api.domain.port.in;

import org.example.cupon_api.domain.model.Cupon;

import java.util.UUID;

public interface RedimirCuponInPort {


    public Cupon redimir(UUID id);

    }
