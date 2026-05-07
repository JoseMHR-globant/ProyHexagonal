package com.pruebas.windsurf.postusuariopremium.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.common.facade.usuario.register.RegisterUsuarioPremiumFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test for {@link GsPostUsuarioPremiumUsecase}. Verifies that the use case normalises the
 * input domain user (trims the name, lowercases the email) before delegating to the facade, and
 * that the facade result is returned unchanged.
 */
@ExtendWith(MockitoExtension.class)
class GsPostUsuarioPremiumUsecaseTest {

  @Mock private RegisterUsuarioPremiumFacade facade;

  @InjectMocks private GsPostUsuarioPremiumUsecase usecase;

  @Test
  void execute_normalizaCamposYDelegaEnFacade() {
    // given: input has whitespace and mixed-case email
    UsuarioPremium input =
        UsuarioPremium.builder()
            .nombre("  Ada Lovelace  ")
            .email("  ADA@Example.COM  ")
            .plan("GOLD")
            .build();

    UsuarioPremium registered =
        UsuarioPremium.builder()
            .id("abc-123")
            .nombre("Ada Lovelace")
            .email("ada@example.com")
            .plan("GOLD")
            .estado("ACTIVO")
            .build();

    when(facade.registrar(org.mockito.ArgumentMatchers.any())).thenReturn(registered);

    // when
    UsuarioPremium result = usecase.execute(input);

    // then: facade received the normalised user
    ArgumentCaptor<UsuarioPremium> captor = ArgumentCaptor.forClass(UsuarioPremium.class);
    org.mockito.Mockito.verify(facade).registrar(captor.capture());

    UsuarioPremium passedToFacade = captor.getValue();
    assertThat(passedToFacade.getNombre()).isEqualTo("Ada Lovelace");
    assertThat(passedToFacade.getEmail()).isEqualTo("ada@example.com");
    assertThat(passedToFacade.getPlan()).isEqualTo("GOLD");

    // and the facade result is returned unchanged
    assertThat(result).isEqualTo(registered);
  }

  @Test
  void execute_camposNulos_noLanzaExcepcion() {
    // given: input with null name and email
    UsuarioPremium input = UsuarioPremium.builder().plan("BASIC").build();
    UsuarioPremium registered = UsuarioPremium.builder().id("xyz").plan("BASIC").build();

    when(facade.registrar(org.mockito.ArgumentMatchers.any())).thenReturn(registered);

    // when
    UsuarioPremium result = usecase.execute(input);

    // then: no NPE, facade returns the registered user
    assertThat(result).isEqualTo(registered);
  }
}
