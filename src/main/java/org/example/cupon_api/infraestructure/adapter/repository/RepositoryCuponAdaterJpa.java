package org.example.cupon_api.infraestructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.domain.port.out.RepositoryCuponOutPort;
import org.example.cupon_api.infraestructure.mapper.CuponMapper;
import org.example.cupon_api.infraestructure.repository.RepositoryCuponJpa;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class RepositoryCuponAdaterJpa implements RepositoryCuponOutPort {

    private  final RepositoryCuponJpa repositoryCuponJpa;
    private final CuponMapper mapper;

    @Override
    public Optional<Cupon> buscarId(UUID uuid) {

        return repositoryCuponJpa.findById(uuid).map(mapper::toDomain);
    }

    @Override
    public Cupon guardarCupon(Cupon cupon) {
       var cuponEntity = repositoryCuponJpa.save(mapper.toEntity(cupon));
       var cuponDomain = mapper.toDomain(cuponEntity);

       return cuponDomain;

    }

    @Override
    public boolean existsByCodigo(String codigo) {

        return repositoryCuponJpa.existsByCodigo(codigo);
    }
}
