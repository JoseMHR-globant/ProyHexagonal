package com.pruebas.windsurf.common.facade.producto.get;

import com.pruebas.windsurf.common.domain.producto.Producto;
import java.util.List;

/**
 * Contrato de la facade encargada de obtener productos del "sistema externo".
 *
 * <p>Vive en {@code common/facade/producto/get/} siguiendo la convención de la librería
 * corporativa, donde las facades se agrupan por área y operación (por ejemplo,
 * {@code common/facade/account/get/}). De esta forma cualquier feature puede reutilizarla.
 *
 * <p>En este proyecto de pruebas, la implementación mockea los datos en memoria. En un
 * microservicio real, aquí se esconderían las llamadas a Feign clients, JPA, cachés, etc.,
 * exponiendo al usecase únicamente modelos de dominio.
 */
public interface GetProductosFacade {

  /**
   * Obtiene el listado de productos desde el sistema externo (simulado).
   *
   * @return lista de productos de dominio.
   */
  List<Producto> obtenerProductos();
}
