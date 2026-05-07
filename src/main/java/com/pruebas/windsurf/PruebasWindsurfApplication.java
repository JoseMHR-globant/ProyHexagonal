package com.pruebas.windsurf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación MC-Pruebas-Windsurf.
 *
 * <p>Microservicio autosuficiente usado para pruebas y experimentos personales. Sigue una
 * arquitectura hexagonal simplificada: controller -&gt; usecase -&gt; facade, donde la facade
 * simula la respuesta del "exterior" en lugar de llamar a un servicio real.
 */
@SpringBootApplication
public class PruebasWindsurfApplication {

  /**
   * Arranca la aplicación Spring Boot.
   *
   * @param args argumentos de línea de comandos.
   */
  public static void main(String[] args) {
    SpringApplication.run(PruebasWindsurfApplication.class, args);
  }
}
