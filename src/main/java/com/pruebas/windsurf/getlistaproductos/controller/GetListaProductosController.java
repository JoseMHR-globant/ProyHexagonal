package com.pruebas.windsurf.getlistaproductos.controller;

import com.pruebas.windsurf.getlistaproductos.controller.model.response.ProductosListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller interface that declares the contract of the {@code GET /v1/productos} endpoint.
 *
 * <p>Following the corporate library convention, the controller layer is split into a parent
 * interface (this file, holding the OpenAPI contract and routing metadata) and a {@code Gs}
 * implementation class that provides the wiring (use case, mapper) and the actual logic.
 *
 * <p>One endpoint per controller, so this interface defines a single operation.
 */
@Tag(name = "Productos", description = "Operaciones sobre el listado de productos de prueba")
@RequestMapping("/v1/productos")
public interface GetListaProductosController {

  /**
   * Returns the full list of available products.
   *
   * @return HTTP 200 response containing the list of products as DTOs.
   */
  @Operation(
      summary = "Lista todos los productos",
      description = "Devuelve el listado completo de productos mockeados en la facade.")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ProductosListResponseDto> getListaProductos();
}
