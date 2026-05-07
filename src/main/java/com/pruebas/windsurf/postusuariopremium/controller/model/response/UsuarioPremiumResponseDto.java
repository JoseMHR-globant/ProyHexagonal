package com.pruebas.windsurf.postusuariopremium.controller.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response Dto for the {@code POST /v1/usuarios-premium} endpoint.
 *
 * <p>Returned to the client after a successful registration. Includes the data assigned by the
 * (mocked) external system: {@code id}, {@code fechaAlta} and {@code estado}.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representation of a registered premium user")
public class UsuarioPremiumResponseDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "Unique identifier", example = "9c8a6f2b-2a55-4c0c-8e8f-1d3c8b7a6e2f")
  private String id;

  @Schema(description = "Full name of the user", example = "Ada Lovelace")
  private String nombre;

  @Schema(description = "Email address of the user", example = "ada@example.com")
  private String email;

  @Schema(description = "Plan code subscribed", example = "GOLD")
  private String plan;

  @Schema(description = "Registration timestamp", example = "2026-05-06T16:30:00")
  private LocalDateTime fechaAlta;

  @Schema(description = "Subscription status", example = "ACTIVO")
  private String estado;
}
