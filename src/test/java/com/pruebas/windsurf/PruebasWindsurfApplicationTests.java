package com.pruebas.windsurf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test de arranque del contexto Spring. Si este test pasa, toda la aplicación (incluidos los
 * mappers generados por MapStruct y el cableado Spring) está correctamente configurada.
 */
@SpringBootTest
class PruebasWindsurfApplicationTests {

  @Test
  void contextLoads() {
    // Verifica que el ApplicationContext arranca sin errores.
  }
}
