package com.pruebas.windsurf.common.facade.usuario.register.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client Data Object representing a premium user as it would be returned by the external
 * registration system (Feign client, JPA entity, etc.).
 *
 * <p>Lives only inside the facade layer. In this experimental project the {@code Cdo} is
 * generated in memory by the mock facade; in a real microservice it would be the JSON
 * representation returned by an HTTP client or the JPA entity.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPremiumCdo implements Serializable {

  private static final long serialVersionUID = 1L;

  /** Identifier returned by the external system. */
  private String id;

  /** Full name of the user. */
  private String nombre;

  /** Email address of the user. */
  private String email;

  /** Plan code subscribed by the user. */
  private String plan;

  /** Registration timestamp returned by the external system. */
  private LocalDateTime fechaAlta;

  /** Subscription status returned by the external system. */
  private String estado;
}
