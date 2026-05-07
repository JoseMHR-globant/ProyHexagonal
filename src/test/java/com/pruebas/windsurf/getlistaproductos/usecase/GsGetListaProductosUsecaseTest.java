package com.pruebas.windsurf.getlistaproductos.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.common.facade.producto.get.GetProductosFacade;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test for {@link GsGetListaProductosUsecase}. Verifies that the use case business logic
 * (alphabetical ordering by name) is applied correctly on top of what the facade returns.
 */
@ExtendWith(MockitoExtension.class)
class GsGetListaProductosUsecaseTest {

  @Mock private GetProductosFacade facade;

  @InjectMocks private GsGetListaProductosUsecase usecase;

  @Test
  void execute_devuelveProductosOrdenadosPorNombre() {
    // given: facade returns products in non-alphabetical order
    Producto productoZ =
        Producto.builder()
            .id("PROD-Z")
            .nombre("Zeta")
            .precio(new BigDecimal("10.00"))
            .categoria("DEMO")
            .build();
    Producto productoA =
        Producto.builder()
            .id("PROD-A")
            .nombre("Alfa")
            .precio(new BigDecimal("20.00"))
            .categoria("DEMO")
            .build();
    Producto productoM =
        Producto.builder()
            .id("PROD-M")
            .nombre("Mike")
            .precio(new BigDecimal("30.00"))
            .categoria("DEMO")
            .build();

    when(facade.obtenerProductos()).thenReturn(List.of(productoZ, productoA, productoM));

    // when
    List<Producto> result = usecase.execute();

    // then: usecase sorts by name
    assertThat(result)
        .extracting(Producto::getNombre)
        .containsExactly("Alfa", "Mike", "Zeta");
  }

  @Test
  void execute_listaVacia_devuelveListaVacia() {
    // given
    when(facade.obtenerProductos()).thenReturn(List.of());

    // when
    List<Producto> result = usecase.execute();

    // then
    assertThat(result).isEmpty();
  }
}
