package com.pruebas.windsurf.common.facade.producto.get.mapper;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.common.facade.producto.get.model.ProductoCdo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-06T16:59:25+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class GsGetProductosFacadeMapperImpl implements GsGetProductosFacadeMapper {

    @Override
    public Producto toDomain(ProductoCdo cdo) {
        if ( cdo == null ) {
            return null;
        }

        Producto.ProductoBuilder producto = Producto.builder();

        producto.id( cdo.getId() );
        producto.nombre( cdo.getNombre() );
        producto.precio( cdo.getPrecio() );
        producto.categoria( cdo.getCategoria() );

        return producto.build();
    }

    @Override
    public List<Producto> toDomainList(List<ProductoCdo> cdos) {
        if ( cdos == null ) {
            return null;
        }

        List<Producto> list = new ArrayList<Producto>( cdos.size() );
        for ( ProductoCdo productoCdo : cdos ) {
            list.add( toDomain( productoCdo ) );
        }

        return list;
    }
}
