package com.pruebas.windsurf.common.domain.usuario;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain model representing a premium user registered in the system.
 *
 * <p>Lives in {@code common/domain/usuario/} replicating the convention of the corporate
 * library (domain models grouped by functional area inside {@code common/}).
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPremium implements Serializable {

  private static final long serialVersionUID = 1L;

  /** Unique identifier of the premium user (assigned by the registration system). */
  private String id;

  /** Full name of the user. */
  private String nombre;

  /** Email address of the user. */
  private String email;

  /** Plan code subscribed by the user (e.g. {@code BASIC}, {@code GOLD}, {@code PLATINUM}). */
  private String plan;

  /** Registration timestamp assigned by the registration system. */
  private LocalDateTime fechaAlta;

  /** Subscription status assigned by the registration system (e.g. {@code ACTIVO}). */
  private String estado;
}
