package com.pruebas.windsurf.getlistaproductos.controller.model.response;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto de respuesta que representa un producto individual devuelto por la API.
 *
 * <p>Vive únicamente en la capa de controller. Se construye a partir del modelo de dominio
 * {@link com.pruebas.windsurf.getlistaproductos.domain.Producto} mediante el mapper
 * correspondiente.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDto implements Serializable {

  private static final long serialVersionUID = 1L;

  /** Identificador único del producto. */
  private String id;

  /** Nombre comercial del producto. */
  private String nombre;

  /** Precio de venta del producto. */
  private BigDecimal precio;

  /** Categoría de agrupación del producto. */
  private String categoria;
}
