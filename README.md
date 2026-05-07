# MC-Pruebas-Windsurf

Microservicio **autosuficiente** Spring Boot 3 para pruebas y experimentos personales.
No depende de ninguna librería interna corporativa. Solo Spring Boot, Lombok, MapStruct y Springdoc.

> ✅ **Sigue las mismas Global Rules y Skills que las librerías corporativas** (arquitectura hexagonal, naming `Gs` para impls, mappers dual-interface, `@Pattern` en inputs, AAA tests, etc.). Ver sección [Rules y Skills del proyecto](#rules-y-skills-del-proyecto).

## Arquitectura

Arquitectura hexagonal replicando el patrón de las librerías corporativas:

```
 HTTP Request
     |
     v
 [ Controller ]  <-- Dto (entrada/salida)              ── feature package
     |   ControllerMapper: Dto <-> Domain              (parent + Gs concrete)
     v
 [ Usecase   ]  <-- Domain (modelo de negocio)         ── feature package
     |   FacadeMapper: Domain <-> Cdo                  (parent + Gs concrete)
     v
 [ Facade    ]  <-- Cdo (modelo del "cliente externo") ── common package (reusable)
     |
     v
 Mock en memoria (en un microservicio real iría un Feign/JPA aquí)
```

### Convención de paquetes

- **`common/`**: artefactos reutilizables por cualquier feature (modelos de dominio, facades, mappers de facade, Cdos, configs, constantes...). Igual que en la librería corporativa.
- **`<feature>/`** (p. ej. `getlistaproductos/`, `postusuariopremium/`): solo lo específico del caso de uso (controller, usecase, DTOs y sus mappers). El feature **consume** lo que vive en `common/`.

### Modelos por capa

| Capa | Modelos permitidos |
|------|--------------------|
| **Controller** | `Dto` + Domain |
| **Usecase** | Domain solo |
| **Facade** | Domain + `Cdo` |

### Convención `Gs` para implementaciones

Toda interfaz (`Controller`, `Usecase`, `Facade`, `Mapper`) tiene su implementación con prefijo **`Gs`** en el **mismo paquete**, replicando la convención de la librería corporativa:

| Tipo | Interfaz | Implementación |
|------|----------|----------------|
| Controller | `XxxController` | `GsXxxController` (clase `@RestController`) |
| Usecase | `XxxUsecase` | `GsXxxUsecase` (clase `@Service`) |
| Facade | `XxxFacade` | `GsXxxFacade` (clase `@Component`) |
| Mapper | `XxxMapper` (parent, sin anotaciones) | `GsXxxMapper extends XxxMapper {}` (interfaz con `@Mapper`) |

Para los mappers, MapStruct genera `GsXxxMapperImpl` automáticamente; el cuerpo de `GsXxxMapper` queda vacío salvo que necesite añadir `@Mapping` específicos.

## Estructura del proyecto

```
src/main/java/com/pruebas/windsurf/
├── PruebasWindsurfApplication.java
│
├── common/                                              ── reutilizable por cualquier feature
│   ├── domain/
│   │   ├── producto/Producto.java
│   │   └── usuario/UsuarioPremium.java
│   └── facade/
│       ├── producto/get/                                ── facade reutilizable: GET productos
│       │   ├── GetProductosFacade.java                  (interfaz)
│       │   ├── GsGetProductosFacade.java                (impl MOCK en memoria)
│       │   ├── mapper/
│       │   │   ├── GetProductosFacadeMapper.java        (parent)
│       │   │   └── GsGetProductosFacadeMapper.java      (Gs concrete con @Mapper)
│       │   └── model/ProductoCdo.java
│       │
│       └── usuario/register/                            ── facade reutilizable: REGISTER usuario
│           ├── RegisterUsuarioPremiumFacade.java        (interfaz)
│           ├── GsRegisterUsuarioPremiumFacade.java      (impl MOCK en memoria)
│           ├── mapper/
│           │   ├── RegisterUsuarioPremiumFacadeMapper.java
│           │   └── GsRegisterUsuarioPremiumFacadeMapper.java
│           └── model/UsuarioPremiumCdo.java
│
├── getlistaproductos/                                   ── feature: GET /v1/productos
│   ├── controller/
│   │   ├── GetListaProductosController.java            (interfaz: @Tag, @RequestMapping, @GetMapping)
│   │   ├── GsGetListaProductosController.java          (@RestController impl)
│   │   ├── mapper/
│   │   │   ├── GetListaProductosControllerMapper.java   (parent)
│   │   │   └── GsGetListaProductosControllerMapper.java (Gs concrete con @Mapper)
│   │   └── model/response/
│   │       ├── ProductoResponseDto.java
│   │       └── ProductosListResponseDto.java
│   └── usecase/
│       ├── GetListaProductosUsecase.java                (interfaz)
│       └── GsGetListaProductosUsecase.java              (@Service impl)
│
└── postusuariopremium/                                  ── feature: POST /v1/usuarios-premium
    ├── controller/
    │   ├── PostUsuarioPremiumController.java            (interfaz)
    │   ├── GsPostUsuarioPremiumController.java          (@RestController impl)
    │   ├── mapper/
    │   │   ├── PostUsuarioPremiumControllerMapper.java
    │   │   └── GsPostUsuarioPremiumControllerMapper.java
    │   └── model/
    │       ├── request/UsuarioPremiumRequestDto.java    (con @Pattern en cada String)
    │       └── response/UsuarioPremiumResponseDto.java
    └── usecase/
        ├── PostUsuarioPremiumUsecase.java               (interfaz)
        └── GsPostUsuarioPremiumUsecase.java             (@Service impl)
```

## Endpoints incluidos

### 1. `GET /v1/productos`

Lista productos hardcodeados en la facade mock, ordenados alfabéticamente por el usecase.

```bash
curl http://localhost:8080/mc-pruebas-windsurf/v1/productos
```

```json
{
  "productos": [
    { "id": "PROD-002", "nombre": "Producto Experimento", "precio": 49.50, "categoria": "DEMO" },
    { "id": "PROD-001", "nombre": "Producto Hola Mundo", "precio": 19.99, "categoria": "DEMO" }
  ]
}
```

### 2. `POST /v1/usuarios-premium`

Registra un usuario premium. La facade mock simula el sistema externo: genera un `id` (UUID), `fechaAlta` (now) y `estado=ACTIVO`.

```bash
curl -X POST http://localhost:8080/mc-pruebas-windsurf/v1/usuarios-premium \
  -H "Content-Type: application/json" \
  -d '{ "nombre": "Ada Lovelace", "email": "ada@example.com", "plan": "GOLD" }'
```

```json
{
  "id": "9c8a6f2b-2a55-4c0c-8e8f-1d3c8b7a6e2f",
  "nombre": "Ada Lovelace",
  "email": "ada@example.com",
  "plan": "GOLD",
  "fechaAlta": "2026-05-06T16:30:00",
  "estado": "ACTIVO"
}
```

> El `plan` debe ser uno de `BASIC`, `GOLD`, `PLATINUM` (validado con `@Pattern`).
> Cualquier otro valor o un email/nombre con caracteres no permitidos devuelve `400 Bad Request`.

## Cómo arrancarlo

Requisitos: **Java 17+** y **Maven 3.8+**.

```bash
mvn spring-boot:run
```

O empaquetar y ejecutar el JAR:

```bash
mvn clean package
java -jar target/mc-pruebas-windsurf.jar
```

### Swagger UI

`http://localhost:8080/mc-pruebas-windsurf/swagger-ui.html`

## Tests

```bash
mvn test
```

Tests unitarios por capa para los dos flujos: controller (mocks de usecase + mapper), usecase (mock de facade) y facade (mock del mapper). Más un `@SpringBootTest` que valida que el contexto arranca y que MapStruct/Spring están bien cableados.

## Cómo añadir una nueva feature

1. **Si necesitas un dominio o facade nuevos** (porque ningún feature anterior los tiene):
   - `common/domain/<area>/XxxModel.java` con el dominio.
   - `common/facade/<area>/<operacion>/` con `XxxFacade` (interfaz) + `GsXxxFacade` (impl), su `mapper/` (parent + `Gs`) y su `model/<Cdo>`.
2. **Crear el paquete del feature** `<verbo><Recurso>/` con:
   - `controller/` con la interfaz + `Gs` impl, mapper (parent + `Gs`) y DTOs en `model/request|response/`.
   - `usecase/` con interfaz + `Gs` impl en el mismo paquete, invocando a la(s) facade(s) de `common/`.
3. **Reutilizar antes que duplicar**: si ya existe una facade en `common/` que devuelve los datos que necesitas, consúmela desde el usecase en lugar de crear una nueva.
4. **Validar todo input** con `@Pattern` (en DTOs, `@PathVariable`, `@RequestParam`, `@RequestHeader`).

## Rules y Skills del proyecto

Este repositorio incluye su **propia copia** de las Global Rules y Skills para que las convenciones se apliquen en cualquier máquina sin depender de las memories a nivel de usuario.

### Ubicación

| Recurso | Ruta |
|---------|------|
| Global Rules | `.docs/windsurf/memories/global_rules.md` |
| Skills | `.docs/windsurf/skills/<skill-name>/SKILL.md` |
| Pointer activo en Windsurf | `.windsurf/rules/project-rules.md` (`trigger: always_on`) |

### Skills disponibles

| Skill | Cuándo se usa |
|-------|----------------|
| `javadoc` | Crear o modificar Javadoc en cualquier fichero. |
| `mappers` | Crear o modificar mappers MapStruct (convención parent + `Gs` concrete). |
| `unittesting` | Escribir o modificar tests unitarios (Mockito + AssertJ + Instancio, AAA). |
| `openapi` | Crear o modificar `Dto` o controllers (anotaciones `@Schema`, `@Operation`). |
| `pmd` | Diagnosticar fallos de PMD durante el build. |
| `newyaml` | Tocar configuración YAML (`config/`, `application.yml`). |
| `flyway` | Crear o revisar migraciones SQL. |

### Cómo se cargan en Windsurf

`.windsurf/rules/project-rules.md` está marcado con `trigger: always_on`, así Cascade lo carga automáticamente al abrir el repositorio. Ese fichero le dice al agente que **lea `.docs/windsurf/memories/global_rules.md` al inicio de cada tarea** y consulte el `SKILL.md` correspondiente antes de producir cada artefacto.

> Si clonas este proyecto en otro ordenador, las rules y los skills viajan **dentro del repo**, no es necesario tener las memories de usuario configuradas.

### Simplificaciones aplicadas a este proyecto

Este es un playground autocontenido **sin dependencias corporativas externas**. Por tanto, las siguientes piezas que aparecen en las rules **no aplican aquí**:

- `@TargetMultiBean`, `BankIdConstant.DCP`, generics multi-bank en mappers (no hay multi-bank).
- `<base-package>.error.commons.exception` (se usan excepciones estándar de Spring/Jakarta).
- PMD ruleset `mb-api-build-tools` (no cableado).
- Anotaciones de autorización (`@Access.EndUser`, etc., no cableadas).
- Feign clients / JPA (las facades **mockean** los datos en memoria).

Todo lo demás de las rules y skills sí aplica: layout hexagonal, sufijos de naming, prefijo `Gs` en impls, mapper dual-interface, `@Pattern` en cada string de entrada, Javadoc en inglés, AAA en tests.
