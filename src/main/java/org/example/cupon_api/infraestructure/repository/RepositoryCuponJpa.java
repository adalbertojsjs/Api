package org.example.cupon_api.infraestructure.repository;

import org.example.cupon_api.infraestructure.entity.CuponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositoryCuponJpa extends JpaRepository<CuponEntity, UUID> {

    boolean existsByCodigo(String codigo);

}

