package com.pruebas.windsurf.postusuariopremium.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.postusuariopremium.controller.mapper.PostUsuarioPremiumControllerMapper;
import com.pruebas.windsurf.postusuariopremium.controller.model.request.UsuarioPremiumRequestDto;
import com.pruebas.windsurf.postusuariopremium.controller.model.response.UsuarioPremiumResponseDto;
import com.pruebas.windsurf.postusuariopremium.usecase.PostUsuarioPremiumUsecase;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit test for {@link GsPostUsuarioPremiumController}. Verifies that the controller maps the
 * incoming Dto to Domain, delegates to the use case, maps the result back to Dto, and returns
 * HTTP 200 with the expected body.
 */
@ExtendWith(MockitoExtension.class)
class GsPostUsuarioPremiumControllerTest {

  @Mock private PostUsuarioPremiumUsecase usecase;
  @Mock private PostUsuarioPremiumControllerMapper mapper;

  @InjectMocks private GsPostUsuarioPremiumController controller;

  @Test
  void postUsuarioPremium_mapeaDtoADominioYDevuelveOk() {
    // given
    UsuarioPremiumRequestDto request =
        UsuarioPremiumRequestDto.builder()
            .nombre("Ada Lovelace")
            .email("ada@example.com")
            .plan("GOLD")
            .build();

    UsuarioPremium toRegister =
        UsuarioPremium.builder().nombre("Ada Lovelace").email("ada@example.com").plan("GOLD").build();

    UsuarioPremium registered =
        UsuarioPremium.builder()
            .id("abc-123")
            .nombre("Ada Lovelace")
            .email("ada@example.com")
            .plan("GOLD")
            .fechaAlta(LocalDateTime.of(2026, 5, 6, 16, 30))
            .estado("ACTIVO")
            .build();

    UsuarioPremiumResponseDto expected =
        UsuarioPremiumResponseDto.builder()
            .id("abc-123")
            .nombre("Ada Lovelace")
            .email("ada@example.com")
            .plan("GOLD")
            .fechaAlta(LocalDateTime.of(2026, 5, 6, 16, 30))
            .estado("ACTIVO")
            .build();

    when(mapper.toDomain(request)).thenReturn(toRegister);
    when(usecase.execute(toRegister)).thenReturn(registered);
    when(mapper.toDto(registered)).thenReturn(expected);

    // when
    ResponseEntity<UsuarioPremiumResponseDto> response = controller.postUsuarioPremium(request);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expected);

    verify(mapper).toDomain(request);
    verify(usecase).execute(toRegister);
    verify(mapper).toDto(registered);
  }
}
