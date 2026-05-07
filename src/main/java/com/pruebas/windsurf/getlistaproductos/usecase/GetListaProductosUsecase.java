package com.pruebas.windsurf.getlistaproductos.usecase;

import com.pruebas.windsurf.common.domain.producto.Producto;
import java.util.List;

/**
 * Contrato del usecase encargado de obtener el listado de productos.
 *
 * <p>Pertenece a la capa de aplicación: solo conoce modelos de dominio y nunca Dto ni Cdo.
 * Su implementación orquesta las llamadas a la(s) facade(s) necesarias para resolver el caso
 * de uso.
 */
public interface GetListaProductosUsecase {

  /**
   * Ejecuta el caso de uso y devuelve el listado de productos del dominio.
   *
   * @return lista de productos disponibles.
   */
  List<Producto> execute();
}
