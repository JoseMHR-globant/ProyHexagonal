package com.pruebas.windsurf.getlistaproductos.controller;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.getlistaproductos.controller.mapper.GetListaProductosControllerMapper;
import com.pruebas.windsurf.getlistaproductos.controller.model.response.ProductosListResponseDto;
import com.pruebas.windsurf.getlistaproductos.usecase.GetListaProductosUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST implementation of {@link GetListaProductosController}.
 *
 * <p>Wires the use case and the mapper, delegating the business logic to the use case and
 * translating between Domain and Dto via the mapper. {@code @Validated} enables method-level
 * Jakarta validation (used for {@code @Pattern} on parameters when present).
 */
@RestController
@Validated
@RequiredArgsConstructor
public class GsGetListaProductosController implements GetListaProductosController {

  private final GetListaProductosUsecase usecase;
  private final GetListaProductosControllerMapper mapper;

  @Override
  public ResponseEntity<ProductosListResponseDto> getListaProductos() {
    List<Producto> productos = usecase.execute();
    return ResponseEntity.ok(mapper.toResponseDto(productos));
  }
}
