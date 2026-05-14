package org.example.cupon_api.infraestructure.mapper;

import org.example.cupon_api.domain.model.Cupon;
import org.example.cupon_api.infraestructure.entity.CuponEntity;
import org.springframework.stereotype.Component;


@Component
public class CuponMapper {


    public CuponEntity toEntity(Cupon dominio) {
        if (dominio == null) {
            return null;
        }

        CuponEntity entidad = new CuponEntity();
        entidad.setId(dominio.getId());
        entidad.setCodigo(dominio.getCodigo());
        entidad.setDescripcion(dominio.getDescripcion());
        entidad.setActivado(dominio.isActivado());
        entidad.setDescuento(dominio.getDescuento());
        entidad.setFecha_Vencimiento(dominio.getFecha_Vencimiento());
        entidad.setPublicado(dominio.isPublicado());

        return entidad;
    }

    public  Cupon toDomain(CuponEntity entidad) {
        if (entidad == null) {
            return null;
        }

        return Cupon.builder()
                .id(entidad.getId())
                .codigo(entidad.getCodigo())
                .descripcion(entidad.getDescripcion())
                .activado(entidad.isActivado())
                .descuento(entidad.getDescuento())
                .fecha_Vencimiento(entidad.getFecha_Vencimiento())
                .publicado(entidad.isPublicado())
                .build();
    }
}
