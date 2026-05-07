package com.pruebas.windsurf.getlistaproductos.controller.model.response;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto de respuesta que envuelve el listado de productos devueltos por el endpoint
 * {@code GET /v1/productos}.
 *
 * <p>Siguiendo el patrón de las librerías corporativas, los endpoints que devuelven colecciones
 * exponen un objeto envolvente en lugar de un array desnudo, permitiendo añadir metadatos
 * (paginación, totales, etc.) sin romper la compatibilidad.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductosListResponseDto implements Serializable {

  private static final long serialVersionUID = 1L;

  /** Listado de productos devueltos por la API. */
  private List<ProductoResponseDto> productos;
}
