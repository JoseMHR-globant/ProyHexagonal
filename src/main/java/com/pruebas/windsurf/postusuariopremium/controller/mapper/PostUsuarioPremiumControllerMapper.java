package com.pruebas.windsurf.postusuariopremium.controller.mapper;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.postusuariopremium.controller.model.request.UsuarioPremiumRequestDto;
import com.pruebas.windsurf.postusuariopremium.controller.model.response.UsuarioPremiumResponseDto;

/**
 * Parent mapper contract translating between the {@link UsuarioPremium} domain model and the
 * controller-layer DTOs (both request and response).
 *
 * <p>The concrete implementation — annotated with {@code @Mapper} so MapStruct generates the
 * bean — is {@link GsPostUsuarioPremiumControllerMapper}, following the corporate library
 * convention (parent interface + {@code Gs} concrete interface).
 */
public interface PostUsuarioPremiumControllerMapper {

  /**
   * Converts the incoming request Dto into a domain user, ready to be passed to the use case.
   *
   * @param dto request payload received from the client.
   * @return domain user populated with the user-provided data.
   */
  UsuarioPremium toDomain(UsuarioPremiumRequestDto dto);

  /**
   * Converts the domain user (already enriched by the facade) into its response Dto.
   *
   * @param domain domain user to convert.
   * @return Dto ready to be serialised as part of the HTTP response.
   */
  UsuarioPremiumResponseDto toDto(UsuarioPremium domain);
}
