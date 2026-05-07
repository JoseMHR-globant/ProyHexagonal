package com.pruebas.windsurf.postusuariopremium.controller;

import com.pruebas.windsurf.postusuariopremium.controller.model.request.UsuarioPremiumRequestDto;
import com.pruebas.windsurf.postusuariopremium.controller.model.response.UsuarioPremiumResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller interface that declares the contract of the {@code POST /v1/usuarios-premium}
 * endpoint.
 *
 * <p>Following the corporate library convention, the controller layer is split into a parent
 * interface (this file, holding the OpenAPI contract and routing metadata) and a {@code Gs}
 * implementation class that provides the wiring (use case, mapper) and the actual logic.
 *
 * <p>One endpoint per controller, so this interface defines a single operation.
 */
@Tag(name = "Usuarios Premium", description = "Operaciones sobre el alta de usuarios premium")
@RequestMapping("/v1/usuarios-premium")
public interface PostUsuarioPremiumController {

  /**
   * Registers a new premium user in the (mocked) external system.
   *
   * @param request payload containing the user data to register.
   * @return HTTP 200 response containing the registered user enriched with id, registration
   *     timestamp and subscription status.
   */
  @Operation(
      summary = "Registra un nuevo usuario premium",
      description =
          "Crea un nuevo usuario premium en el sistema (mockeado). Devuelve el usuario "
              + "enriquecido con id, fecha de alta y estado de suscripción.")
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<UsuarioPremiumResponseDto> postUsuarioPremium(
      @Valid @RequestBody UsuarioPremiumRequestDto request);
}
