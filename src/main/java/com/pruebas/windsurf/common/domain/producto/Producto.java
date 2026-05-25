package com.pruebas.windsurf.common.domain.producto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo de dominio que representa un producto dentro del negocio.
 *
 * <p>Vive en {@code common/domain/producto/} replicando la convención de la librería corporativa,
 * donde los modelos de dominio se agrupan por área funcional dentro de {@code common}, de forma
 * que sean reutilizables por cualquier feature.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable {

  private static final long serialVersionUID = 1L;

  /** Identificador único del producto. */
  private String id;

  /** Nombre comercial del producto. */
  private String nombre;

  /** Precio de venta del producto. */
  private BigDecimal precio;

  /** Categoría de agrupación del producto. */
  private String categoria;

  /** Fecha de creación o registro del producto. */
  private LocalDate fecha;
}
