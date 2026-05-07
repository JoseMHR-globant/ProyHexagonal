package com.pruebas.windsurf.common.facade.usuario.register;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;

/**
 * Contract of the facade in charge of registering a premium user in the (simulated) external
 * system.
 *
 * <p>In this experimental project the implementation mocks the registration in memory. In a real
 * microservice this is where Feign clients or JPA repositories would live, exposing only domain
 * models to the use case layer.
 */
public interface RegisterUsuarioPremiumFacade {

  /**
   * Registers a new premium user in the external system.
   *
   * <p>The input domain object is expected to carry user-provided data (name, email, plan). The
   * returned domain object additionally contains the data assigned by the registration system
   * ({@code id}, {@code fechaAlta}, {@code estado}).
   *
   * @param usuario domain object with the data to register.
   * @return enriched domain object as it has been persisted by the external system.
   */
  UsuarioPremium registrar(UsuarioPremium usuario);
}
