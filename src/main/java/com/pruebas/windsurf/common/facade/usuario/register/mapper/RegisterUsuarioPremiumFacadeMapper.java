package com.pruebas.windsurf.common.facade.usuario.register.mapper;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.common.facade.usuario.register.model.UsuarioPremiumCdo;

/**
 * Parent mapper contract translating between {@link UsuarioPremium} (internal domain) and
 * {@link UsuarioPremiumCdo} (external system representation).
 *
 * <p>The concrete implementation — annotated with {@code @Mapper} so MapStruct generates the
 * bean — is {@link GsRegisterUsuarioPremiumFacadeMapper}, following the corporate library
 * convention (parent interface + {@code Gs} concrete interface).
 */
public interface RegisterUsuarioPremiumFacadeMapper {

  /**
   * Converts a domain user into its Cdo representation, ready to be sent to the external system.
   *
   * @param usuario domain user to convert.
   * @return equivalent Cdo.
   */
  UsuarioPremiumCdo toCdo(UsuarioPremium usuario);

  /**
   * Converts a Cdo returned by the external system into the domain model used by the use case.
   *
   * @param cdo Cdo received from the external system.
   * @return equivalent domain user.
   */
  UsuarioPremium toDomain(UsuarioPremiumCdo cdo);
}
