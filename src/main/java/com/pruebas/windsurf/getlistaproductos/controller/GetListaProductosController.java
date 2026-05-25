package com.pruebas.windsurf.getlistaproductos.controller;

import com.pruebas.windsurf.getlistaproductos.controller.model.response.ProductosListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller interface for retrieving a list of products.
 *
 * @author Team Name
 * @since 1.0.0
 */
@Tag(name = "Productos", description = "Operaciones sobre el listado de productos de prueba")
@RequestMapping("/v1/productos")
public interface GetListaProductosController {

  /**
   * Retrieves the complete list of products.
   *
   * @return the response entity containing the list of products
   */
  @Operation(
      summary = "Lista todos los productos",
      description = "Devuelve el listado completo de productos mockeados en la facade.")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ProductosListResponseDto> getListaProductos();
}
