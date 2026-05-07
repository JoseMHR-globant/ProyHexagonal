package com.pruebas.windsurf.postusuariopremium.controller.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request Dto for the {@code POST /v1/usuarios-premium} endpoint.
 *
 * <p>Every string field is validated with a restrictive {@code @Pattern} regex to prevent log
 * injection, header injection and other security vulnerabilities, as required by the global
 * project rules.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload to register a new premium user")
public class UsuarioPremiumRequestDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "Full name of the user", example = "Ada Lovelace")
  @NotBlank
  @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\-']{1,100}$")
  private String nombre;

  @Schema(description = "Email address of the user", example = "ada@example.com")
  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")
  private String email;

  @Schema(
      description = "Plan code subscribed by the user",
      example = "GOLD",
      allowableValues = {"BASIC", "GOLD", "PLATINUM"})
  @NotBlank
  @Pattern(regexp = "^(BASIC|GOLD|PLATINUM)$")
  private String plan;
}
