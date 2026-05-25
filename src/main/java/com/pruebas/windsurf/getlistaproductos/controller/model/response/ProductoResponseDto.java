package com.pruebas.windsurf.getlistaproductos.controller.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@Schema(description = "Response DTO representing a single product returned by the API")
public class ProductoResponseDto implements Serializable {

  private static final long serialVersionUID = 1L;

  /** Identificador único del producto. */
  @Schema(description = "Unique product identifier", example = "PROD-001")
  private String id;

  /** Nombre comercial del producto. */
  @Schema(description = "Commercial name of the product", example = "Producto Hola Mundo")
  private String nombre;

  /** Precio de venta del producto. */
  @Schema(description = "Sale price of the product", example = "19.99")
  private BigDecimal precio;

  /** Categoría de agrupación del producto. */
  @Schema(description = "Product category", example = "DEMO")
  private String categoria;

  /** Fecha de creación o registro del producto. */
  @Schema(description = "Creation or registration date of the product", example = "2024-01-15")
  private LocalDate fecha;
}
