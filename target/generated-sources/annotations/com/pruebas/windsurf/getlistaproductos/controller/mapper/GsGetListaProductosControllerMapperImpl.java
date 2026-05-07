package com.pruebas.windsurf.getlistaproductos.controller.mapper;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.getlistaproductos.controller.model.response.ProductoResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-06T16:59:26+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class GsGetListaProductosControllerMapperImpl implements GsGetListaProductosControllerMapper {

    @Override
    public ProductoResponseDto toDto(Producto producto) {
        if ( producto == null ) {
            return null;
        }

        ProductoResponseDto.ProductoResponseDtoBuilder productoResponseDto = ProductoResponseDto.builder();

        productoResponseDto.id( producto.getId() );
        productoResponseDto.nombre( producto.getNombre() );
        productoResponseDto.precio( producto.getPrecio() );
        productoResponseDto.categoria( producto.getCategoria() );

        return productoResponseDto.build();
    }

    @Override
    public List<ProductoResponseDto> toDtoList(List<Producto> productos) {
        if ( productos == null ) {
            return null;
        }

        List<ProductoResponseDto> list = new ArrayList<ProductoResponseDto>( productos.size() );
        for ( Producto producto : productos ) {
            list.add( toDto( producto ) );
        }

        return list;
    }
}
