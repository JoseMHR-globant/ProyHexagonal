package com.pruebas.windsurf.common.facade.producto.get.mapper;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.common.facade.producto.get.model.ProductoCdo;
import java.util.List;

/**
 * Parent mapper contract translating between {@link ProductoCdo} (external system representation)
 * and {@link Producto} (internal domain model).
 *
 * <p>The concrete implementation — annotated with {@code @Mapper} so MapStruct generates the
 * bean — is {@link GsGetProductosFacadeMapper}, following the corporate library convention
 * (parent interface + {@code Gs} concrete interface).
 */
public interface GetProductosFacadeMapper {

  /**
   * Converts a Cdo returned by the external system into a domain product.
   *
   * @param cdo object received from the (mocked) external service.
   * @return equivalent domain product.
   */
  Producto toDomain(ProductoCdo cdo);

  /**
   * Converts a list of Cdos into a list of domain products.
   *
   * @param cdos list of external objects.
   * @return list of domain products.
   */
  List<Producto> toDomainList(List<ProductoCdo> cdos);
}
