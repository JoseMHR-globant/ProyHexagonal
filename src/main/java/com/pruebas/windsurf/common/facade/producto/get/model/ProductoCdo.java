package com.pruebas.windsurf.common.facade.producto.get.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client Data Object que representa un producto tal y como lo devolvería un servicio externo
 * (por ejemplo un Feign client contra otro microservicio).
 *
 * <p>Vive únicamente en la capa de facade. En este proyecto de pruebas se construye con datos
 * mockeados en memoria; en un microservicio real sería la representación JSON del cliente HTTP
 * o la entidad JPA de una base de datos.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCdo implements Serializable {

  private static final long serialVersionUID = 1L;

  /** Identificador del producto devuelto por el "sistema externo". */
  private String id;

  /** Nombre comercial del producto. */
  private String nombre;

  /** Precio de venta del producto. */
  private BigDecimal precio;

  /** Categoría del producto. */
  private String categoria;
}
