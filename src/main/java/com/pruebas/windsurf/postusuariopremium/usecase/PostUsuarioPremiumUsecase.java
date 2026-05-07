package com.pruebas.windsurf.postusuariopremium.usecase;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;

/**
 * Use case contract in charge of registering a premium user.
 *
 * <p>Belongs to the application layer: it only knows domain models, never DTOs nor CDOs. Its
 * implementation orchestrates the call(s) to the facade(s) needed to fulfil the use case.
 */
public interface PostUsuarioPremiumUsecase {

  /**
   * Executes the use case: registers the given premium user and returns the registered domain
   * object enriched with the data assigned by the (mocked) external system.
   *
   * @param usuario domain user with the data submitted by the client.
   * @return registered domain user with id, registration timestamp and status assigned.
   */
  UsuarioPremium execute(UsuarioPremium usuario);
}
