package com.pruebas.windsurf.postusuariopremium.usecase;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.common.facade.usuario.register.RegisterUsuarioPremiumFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Concrete implementation of {@link PostUsuarioPremiumUsecase}.
 *
 * <p>Normalises the incoming domain user (trims and lowercases the email) and delegates the
 * registration to the facade. This is the natural place to add business validations (duplicate
 * checks, plan eligibility, etc.) without polluting the controller or the facade.
 *
 * <p>Lives in the same package as the parent interface, replicating the corporate library
 * convention {@code GetListPlanInfoUseCase} / {@code GsGetListPlanInfoUseCase} (here the
 * differentiator is the {@code Gs} prefix on the implementation).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GsPostUsuarioPremiumUsecase implements PostUsuarioPremiumUsecase {

  private final RegisterUsuarioPremiumFacade facade;

  @Override
  public UsuarioPremium execute(UsuarioPremium usuario) {
    log.debug("Executing usecase postUsuarioPremium for plan {}", usuario.getPlan());
    UsuarioPremium normalised = normalise(usuario);
    return facade.registrar(normalised);
  }

  /**
   * Normalises user-provided fields before delegating to the facade.
   *
   * <p>Trims whitespace from the name and lowercases the email so the registration system
   * stores them in a canonical form.
   *
   * @param usuario domain user as received from the controller layer.
   * @return new domain user with normalised fields.
   */
  private UsuarioPremium normalise(UsuarioPremium usuario) {
    return UsuarioPremium.builder()
        .nombre(usuario.getNombre() == null ? null : usuario.getNombre().trim())
        .email(usuario.getEmail() == null ? null : usuario.getEmail().trim().toLowerCase())
        .plan(usuario.getPlan())
        .build();
  }
}
