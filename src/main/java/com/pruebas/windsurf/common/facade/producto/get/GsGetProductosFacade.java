package com.pruebas.windsurf.common.facade.producto.get;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.common.facade.producto.get.mapper.GetProductosFacadeMapper;
import com.pruebas.windsurf.common.facade.producto.get.model.ProductoCdo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mock implementation of {@link GetProductosFacade}.
 *
 * <p>In a real microservice this component would call a Feign client, a JPA repository, a cache,
 * etc. For this experimental project we generate a fixed in-memory list and map it from
 * {@link ProductoCdo} (external representation) to {@link Producto} (domain), faithfully
 * reproducing the flow that a microservice with real integration would have.
 *
 * <p>Lives in the same package as the parent interface, replicating the corporate library
 * convention {@code AccountsFacade} / {@code GsAccountsFacade} (here the differentiator is the
 * {@code Gs} prefix on the implementation).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GsGetProductosFacade implements GetProductosFacade {

  private final GetProductosFacadeMapper mapper;

  @Override
  public List<Producto> obtenerProductos() {
    log.debug("Mock facade: returning fixed product list");
    List<ProductoCdo> cdos =
        List.of(
            ProductoCdo.builder()
                .id("PROD-001")
                .nombre("Producto Hola Mundo")
                .precio(new BigDecimal("19.99"))
                .categoria("DEMO")
                .fecha(LocalDate.of(2024, 1, 15))
                .build(),
            ProductoCdo.builder()
                .id("PROD-002")
                .nombre("Producto Experimento")
                .precio(new BigDecimal("49.50"))
                .categoria("DEMO")
                .fecha(LocalDate.of(2024, 3, 22))
                .build());
    return mapper.toDomainList(cdos);
  }
}
