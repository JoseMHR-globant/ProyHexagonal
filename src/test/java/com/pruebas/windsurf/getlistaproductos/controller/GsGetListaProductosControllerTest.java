package com.pruebas.windsurf.getlistaproductos.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.getlistaproductos.controller.mapper.GetListaProductosControllerMapper;
import com.pruebas.windsurf.getlistaproductos.controller.model.response.ProductoResponseDto;
import com.pruebas.windsurf.getlistaproductos.controller.model.response.ProductosListResponseDto;
import com.pruebas.windsurf.getlistaproductos.usecase.GetListaProductosUsecase;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit test for {@link GsGetListaProductosController}. Verifies that the controller delegates
 * correctly to the use case and the mapper, and that it returns HTTP 200 with the expected body.
 */
@ExtendWith(MockitoExtension.class)
class GsGetListaProductosControllerTest {

  @Mock private GetListaProductosUsecase usecase;
  @Mock private GetListaProductosControllerMapper mapper;

  @InjectMocks private GsGetListaProductosController controller;

  @Test
  void getListaProductos_delegaEnUsecaseYMapper_devuelveOk() {
    // given
    Producto producto =
        Producto.builder()
            .id("PROD-001")
            .nombre("Producto Hola Mundo")
            .precio(new BigDecimal("19.99"))
            .categoria("DEMO")
            .build();
    List<Producto> productos = List.of(producto);

    ProductosListResponseDto expectedDto =
        ProductosListResponseDto.builder()
            .productos(
                List.of(
                    ProductoResponseDto.builder()
                        .id("PROD-001")
                        .nombre("Producto Hola Mundo")
                        .precio(new BigDecimal("19.99"))
                        .categoria("DEMO")
                        .build()))
            .build();

    when(usecase.execute()).thenReturn(productos);
    when(mapper.toResponseDto(productos)).thenReturn(expectedDto);

    // when
    ResponseEntity<ProductosListResponseDto> response = controller.getListaProductos();

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expectedDto);

    verify(usecase).execute();
    verify(mapper).toResponseDto(productos);
  }
}
