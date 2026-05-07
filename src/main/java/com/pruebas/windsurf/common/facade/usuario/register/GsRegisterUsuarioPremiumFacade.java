package com.pruebas.windsurf.common.facade.usuario.register;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.common.facade.usuario.register.mapper.RegisterUsuarioPremiumFacadeMapper;
import com.pruebas.windsurf.common.facade.usuario.register.model.UsuarioPremiumCdo;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mock implementation of {@link RegisterUsuarioPremiumFacade}.
 *
 * <p>Simulates an external registration system: maps the incoming domain object to {@code Cdo},
 * fills in the fields that the external system would assign ({@code id}, {@code fechaAlta},
 * {@code estado}), and maps the resulting {@code Cdo} back to a domain object that is returned
 * to the use case.
 *
 * <p>Lives in the same package as the parent interface, replicating the corporate library
 * convention {@code AccountsFacade} / {@code GsAccountsFacade} (here the differentiator is the
 * {@code Gs} prefix on the implementation).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GsRegisterUsuarioPremiumFacade implements RegisterUsuarioPremiumFacade {

  /** Status assigned by the (simulated) registration system to every new user. */
  private static final String ESTADO_ACTIVO = "ACTIVO";

  private final RegisterUsuarioPremiumFacadeMapper mapper;

  @Override
  public UsuarioPremium registrar(UsuarioPremium usuario) {
    log.debug("Mock facade: registering premium user with email {}", usuario.getEmail());

    UsuarioPremiumCdo cdoIn = mapper.toCdo(usuario);

    UsuarioPremiumCdo cdoOut =
        UsuarioPremiumCdo.builder()
            .id(UUID.randomUUID().toString())
            .nombre(cdoIn.getNombre())
            .email(cdoIn.getEmail())
            .plan(cdoIn.getPlan())
            .fechaAlta(LocalDateTime.now())
            .estado(ESTADO_ACTIVO)
            .build();

    return mapper.toDomain(cdoOut);
  }
}
