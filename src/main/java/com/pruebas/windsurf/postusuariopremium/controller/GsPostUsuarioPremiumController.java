package com.pruebas.windsurf.postusuariopremium.controller;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.postusuariopremium.controller.mapper.PostUsuarioPremiumControllerMapper;
import com.pruebas.windsurf.postusuariopremium.controller.model.request.UsuarioPremiumRequestDto;
import com.pruebas.windsurf.postusuariopremium.controller.model.response.UsuarioPremiumResponseDto;
import com.pruebas.windsurf.postusuariopremium.usecase.PostUsuarioPremiumUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST implementation of {@link PostUsuarioPremiumController}.
 *
 * <p>Wires the use case and the mapper, mapping {@code Dto -> Domain} for the input and
 * {@code Domain -> Dto} for the output, and delegating the business logic to the use case.
 */
@RestController
@Validated
@RequiredArgsConstructor
public class GsPostUsuarioPremiumController implements PostUsuarioPremiumController {

  private final PostUsuarioPremiumUsecase usecase;
  private final PostUsuarioPremiumControllerMapper mapper;

  @Override
  public ResponseEntity<UsuarioPremiumResponseDto> postUsuarioPremium(
      @Valid @RequestBody UsuarioPremiumRequestDto request) {
    UsuarioPremium toRegister = mapper.toDomain(request);
    UsuarioPremium registered = usecase.execute(toRegister);
    return ResponseEntity.ok(mapper.toDto(registered));
  }
}
