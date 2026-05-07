package com.pruebas.windsurf.common.facade.producto.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.common.facade.producto.get.mapper.GetProductosFacadeMapper;
import com.pruebas.windsurf.common.facade.producto.get.model.ProductoCdo;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test for {@link GsGetProductosFacade}. Verifies that the facade builds the mock with the
 * expected products and delegates the Domain mapping to the injected mapper.
 */
@ExtendWith(MockitoExtension.class)
class GsGetProductosFacadeTest {

  @Mock private GetProductosFacadeMapper mapper;

  @InjectMocks private GsGetProductosFacade facade;

  @Test
  void obtenerProductos_devuelveListaMockMapeadaADominio() {
    // given
    Producto productoMockeado =
        Producto.builder()
            .id("PROD-001")
            .nombre("Producto Hola Mundo")
            .precio(new BigDecimal("19.99"))
            .categoria("DEMO")
            .build();
    when(mapper.toDomainList(anyList())).thenReturn(List.of(productoMockeado));

    // when
    List<Producto> result = facade.obtenerProductos();

    // then: result is what the mapper returned
    assertThat(result).containsExactly(productoMockeado);

    // and the facade has passed exactly the 2 hardcoded products to the mapper
    @SuppressWarnings("unchecked")
    ArgumentCaptor<List<ProductoCdo>> captor = ArgumentCaptor.forClass(List.class);
    verify(mapper).toDomainList(captor.capture());

    assertThat(captor.getValue())
        .hasSize(2)
        .extracting(ProductoCdo::getId)
        .containsExactly("PROD-001", "PROD-002");
    assertThat(captor.getValue())
        .extracting(ProductoCdo::getNombre)
        .containsExactly("Producto Hola Mundo", "Producto Experimento");
  }
}
