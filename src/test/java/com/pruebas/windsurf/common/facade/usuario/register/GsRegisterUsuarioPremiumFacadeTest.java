package com.pruebas.windsurf.common.facade.usuario.register;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pruebas.windsurf.common.domain.usuario.UsuarioPremium;
import com.pruebas.windsurf.common.facade.usuario.register.mapper.RegisterUsuarioPremiumFacadeMapper;
import com.pruebas.windsurf.common.facade.usuario.register.model.UsuarioPremiumCdo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test for {@link GsRegisterUsuarioPremiumFacade}. Verifies that the mock facade simulates
 * the external registration system: it maps the incoming domain user to Cdo, generates the
 * fields assigned by the external system (id, fechaAlta, estado), and maps the resulting Cdo
 * back to a domain user.
 */
@ExtendWith(MockitoExtension.class)
class GsRegisterUsuarioPremiumFacadeTest {

  @Mock private RegisterUsuarioPremiumFacadeMapper mapper;

  @InjectMocks private GsRegisterUsuarioPremiumFacade facade;

  @Test
  void registrar_simulaSistemaExternoYDevuelveDominioEnriquecido() {
    // given
    UsuarioPremium input =
        UsuarioPremium.builder()
            .nombre("Ada Lovelace")
            .email("ada@example.com")
            .plan("GOLD")
            .build();

    UsuarioPremiumCdo cdoIn =
        UsuarioPremiumCdo.builder()
            .nombre("Ada Lovelace")
            .email("ada@example.com")
            .plan("GOLD")
            .build();

    UsuarioPremium registered =
        UsuarioPremium.builder()
            .id("generated")
            .nombre("Ada Lovelace")
            .email("ada@example.com")
            .plan("GOLD")
            .estado("ACTIVO")
            .build();

    when(mapper.toCdo(input)).thenReturn(cdoIn);
    when(mapper.toDomain(any(UsuarioPremiumCdo.class))).thenReturn(registered);

    // when
    UsuarioPremium result = facade.registrar(input);

    // then: domain returned is what the mapper produced
    assertThat(result).isEqualTo(registered);

    // and the Cdo passed to mapper.toDomain has been enriched with id/fechaAlta/estado
    ArgumentCaptor<UsuarioPremiumCdo> captor = ArgumentCaptor.forClass(UsuarioPremiumCdo.class);
    verify(mapper).toDomain(captor.capture());

    UsuarioPremiumCdo enrichedCdo = captor.getValue();
    assertThat(enrichedCdo.getId()).isNotBlank();
    assertThat(enrichedCdo.getFechaAlta()).isNotNull();
    assertThat(enrichedCdo.getEstado()).isEqualTo("ACTIVO");
    // and user-provided fields are preserved
    assertThat(enrichedCdo.getNombre()).isEqualTo("Ada Lovelace");
    assertThat(enrichedCdo.getEmail()).isEqualTo("ada@example.com");
    assertThat(enrichedCdo.getPlan()).isEqualTo("GOLD");
  }
}
