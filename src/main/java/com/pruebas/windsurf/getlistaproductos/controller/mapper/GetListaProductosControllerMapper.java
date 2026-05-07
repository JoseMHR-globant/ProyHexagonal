package com.pruebas.windsurf.getlistaproductos.controller.mapper;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.getlistaproductos.controller.model.response.ProductoResponseDto;
import com.pruebas.windsurf.getlistaproductos.controller.model.response.ProductosListResponseDto;
import java.util.List;

/**
 * Parent mapper contract translating between the {@link Producto} domain model and the
 * controller-layer DTOs.
 *
 * <p>This file declares the abstract translation contract. The concrete implementation —
 * annotated with {@code @Mapper} so MapStruct generates the bean — is
 * {@link GsGetListaProductosControllerMapper}, following the corporate library convention
 * (parent interface + {@code Gs} concrete interface).
 */
public interface GetListaProductosControllerMapper {

  /**
   * Converts a domain {@link Producto} into its response Dto representation.
   *
   * @param producto domain model to convert.
   * @return Dto ready to be serialised as part of the HTTP response.
   */
  ProductoResponseDto toDto(Producto producto);

  /**
   * Converts a list of domain products into a list of response Dtos.
   *
   * @param productos list of domain products.
   * @return equivalent list of Dtos.
   */
  List<ProductoResponseDto> toDtoList(List<Producto> productos);

  /**
   * Builds the wrapper {@link ProductosListResponseDto} from a list of domain products.
   *
   * @param productos list of domain products.
   * @return wrapper Dto holding the list of products already mapped.
   */
  default ProductosListResponseDto toResponseDto(List<Producto> productos) {
    return ProductosListResponseDto.builder().productos(toDtoList(productos)).build();
  }
}
