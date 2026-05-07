package com.pruebas.windsurf.getlistaproductos.usecase;

import com.pruebas.windsurf.common.domain.producto.Producto;
import com.pruebas.windsurf.common.facade.producto.get.GetProductosFacade;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Concrete implementation of {@link GetListaProductosUsecase}.
 *
 * <p>Orchestrates the call to the facade and applies the business logic associated with the use
 * case. In this example the logic is limited to sorting the products alphabetically by name, but
 * this is the natural place to add filters, business validations, multi-facade aggregations, etc.
 *
 * <p>Lives in the same package as the parent interface, replicating the convention
 * {@code AccountsFacade} / {@code GsAccountsFacade} of the corporate library (here the
 * differentiator is the {@code Gs} prefix on the implementation).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GsGetListaProductosUsecase implements GetListaProductosUsecase {

  private final GetProductosFacade facade;

  /**
   * Retrieves the products from the facade and returns them sorted alphabetically by name.
   *
   * @return list of domain products, sorted alphabetically by name.
   */
  @Override
  public List<Producto> execute() {
    log.debug("Executing usecase getListaProductos");
    List<Producto> productos = facade.obtenerProductos();
    return productos.stream()
        .sorted(Comparator.comparing(Producto::getNombre, Comparator.nullsLast(String::compareTo)))
        .toList();
  }
}
