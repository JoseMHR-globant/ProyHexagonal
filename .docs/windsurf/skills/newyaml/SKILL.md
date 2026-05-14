---
name: newyaml
description: Standards for Spring Boot property loading and YAML configuration. Use when creating, modifying, or migrating YAML configuration files in config/ or src/main/resources/.
---

# Spring Boot Property Loading

## 1. Current Solution

### 1.1. General Spring Boot Mechanism

Spring Boot loads properties following a **precedence order** (from lowest to highest priority):

1. `application.yml` (inside the JAR — `src/main/resources/`)
2. `application-{profile}.yml` (inside the JAR — `src/main/resources/`)
3. `config/{env}.yml` (outside the JAR — `config/` directory)
4. `config/{country}/{env}.yml` (outside the JAR — `config/` directory)

> **Key rule**: Higher-priority properties **override** lower-priority ones. Properties not redefined are **inherited** from the previous level.

---

### 1.2. File Structure

```
{microservice}/
├── src/main/resources/
│   ├── application.yml              ← (1) Global base (inside the JAR)
│   └── application-default.yml      ← (2) Profile "default" (local development)
│
└── config/                          ← (3-4) External configuration (outside the JAR)
    ├── demo.yml                     ← Environment: demo
    ├── dev.yml                      ← Environment: dev
    ├── stg.yml                      ← Environment: stg
    ├── prod.yml                     ← Environment: prod
    ├── {country}/                   ← Country (e.g.: de, es, ar, mx)
    │   ├── demo.yml
    │   ├── dev.yml
    │   ├── stg.yml
    │   └── prod.yml
    └── {country}-cc/                ← Country + Contact Center
        ├── demo.yml
        ├── dev.yml
        ├── stg.yml
        └── prod.yml
```

---

### 1.3. Loading Chain per Environment

#### 1.3.1. Local Development (`-Dspring.profiles.active=default`)

Only the internal JAR files are loaded:

```
application.yml  →  application-default.yml
     (base)              (local override)
```

`application-default.yml` typically overrides:
- `deployment.country`, `deployment.environment`
- Local datasource (PostgreSQL `localhost`)
- Local Redis (`localhost:6379`)
- `server.port`
- `database.connection.auth.iam: false`

#### 1.3.2. Deployed Environment (e.g.: `demo` + country)

In deployment, the platform activates the environment and country profiles. The loading chain is:

```
application.yml                ← (1) Global base: all default properties
       ↓
config/{env}.yml               ← (2) Environment override: deployment.environment,
       ↓                              Redis TLS, logging, client base-urls
config/{country}/{env}.yml     ← (3) Country override: deployment.country,
                                      deployment.country-core, country-specific configuration
```

#### 1.3.3. Deployed Environment with Contact Center

```
application.yml                ← (1) Global base
       ↓
config/{env}.yml               ← (2) Environment override
       ↓
config/{country}-cc/{env}.yml  ← (3) Country+CC override: deployment.country-cc-extension,
                                      Contact Center specific configuration
```

---

### 1.4. What Each Level Defines

#### Level 1 — `application.yml` (global base)

| Category | Property Examples |
|----------|-----------------|
| **Project** | `project.artifactId`, `project.version` |
| **Spring Core** | Flyway, JPA, Redis (defaults), Feign timeouts, thread pool |
| **Security** | `commons.authentication`, `commons.authorization` |
| **OpenAPI** | `springdoc`, `openapi.servers` |
| **Business** | Microservice-specific properties |
| **Redis Cache** | TTLs and prefixes for all caches |
| **Logging** | Base levels (e.g.: `ROOT: warn`) |

#### Level 2 — `config/{env}.yml` (per environment)

| Environment | Typical Properties Overridden |
|-------------|-------------------------------|
| **demo** | `deployment.environment: demo`, Redis TLS + pool sizes, logging level, **`client.*.base-url`** (external service URLs) |
| **dev** | `deployment.environment: dev`, reduced Redis pool, dev-specific URLs |
| **stg** | `deployment.environment: stg`, reduced Redis pool, stg-specific URLs |
| **prod** | `deployment.environment: prod`, Redis TLS + pool sizes, logging mask patterns (sensitive data), production-specific configuration |

> **Note**: Generally `demo.yml` is the most complete file at the environment level, as it defines most `client.*.base-url` entries. Other environments inherit or receive URLs via platform environment variables.

#### Level 3 — `config/{country}/{env}.yml` (per country)

| Type | Typical Properties Overridden |
|------|-------------------------------|
| **{country}/{env}** | `deployment.country`, `deployment.country-core`, UI configuration, country-specific business rules |
| **{country}-cc/{env}** | All of the above + `deployment.country-cc-extension: -cc`, full Contact Center configuration (filters, UI, etc.) |

---

### 1.5. Placeholder Resolution

Many properties use **placeholders** that are resolved in cascade:

```yaml
# Defined in config/{country}/{env}.yml
deployment:
  country: de
  country-core: es
  environment: demo

# Defined in config/{env}.yml — resolved with the values above
client:
  example-service:
    base-url: "https://example-${deployment.country-core}${deployment.country-cc-extension:}.${deployment.environment}.ok-cloud.net"
    # → https://example-es.demo.ok-cloud.net          (without CC)
    # → https://example-es-cc.demo.ok-cloud.net       (with CC)
```

#### Placeholder Syntax

| Syntax | Description |
|--------|-------------|
| `${property.name}` | Substitutes with the property value. Fails if it doesn't exist. |
| `${property.name:default}` | Substitutes with the value, or uses `default` if it doesn't exist. |
| `${property.name:}` | Substitutes with the value, or empty string if it doesn't exist. |
| `${ENV_VAR:fallback}` | Substitutes with OS environment variable, or `fallback`. |

---

### 1.6. Complete Resolution Example

For a deployment in **demo** with country **de** (without CC):

| Step | File | Action |
|------|------|--------|
| 1 | `application.yml` | Defines base properties. Does not define `client.*.base-url`. |
| 2 | `config/demo.yml` | Defines `client.example.base-url: "https://svc-${deployment.country-core}${deployment.country-cc-extension:}.stg.ok-cloud.net"` |
| 3 | `config/de/demo.yml` | Defines `deployment.country-core: es` |
| 4 | Resolution | `${deployment.country-core}` → `es`, `${deployment.country-cc-extension:}` → `""` |
| **Result** | | `https://svc-es.stg.ok-cloud.net` |

---

### 1.7. Precedence Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    HIGHEST PRIORITY                       │
│                                                         │
│  ┌───────────────────────────────────────────────────┐  │
│  │  config/{country}/{env}.yml                       │  │
│  │  (deployment.country, country-core, country biz)  │  │
│  └───────────────────────┬───────────────────────────┘  │
│                          │ inherits + overrides          │
│  ┌───────────────────────▼───────────────────────────┐  │
│  │  config/{env}.yml                                 │  │
│  │  (deployment.environment, client base-urls,       │  │
│  │   Redis TLS, logging)                             │  │
│  └───────────────────────┬───────────────────────────┘  │
│                          │ inherits + overrides          │
│  ┌───────────────────────▼───────────────────────────┐  │
│  │  application.yml (src/main/resources/)            │  │
│  │  (ALL base properties: Spring, business,          │  │
│  │   cache, security, OpenAPI, thread pools)         │  │
│  └───────────────────────────────────────────────────┘  │
│                                                         │
│                    LOWEST PRIORITY                        │
└─────────────────────────────────────────────────────────┘
```

---

### 1.8. Best Practices

- **Do not duplicate properties**: If a property doesn't change between environments, define it only in `application.yml`.
- **Use placeholders**: Prefer `${deployment.country}` over hardcoded values to keep configuration DRY.
- **Default values**: Use `${property:default}` for optional properties (e.g.: `${deployment.country-cc-extension:}`).
- **Sensitive data**: Never include passwords or tokens in YMLs. Use environment variables: `${SERVICE_USER_PASSWORD:REPLACEME}`.
- **Minimal country files**: The `config/{country}/{env}.yml` files should contain only what is strictly necessary for that country/environment. Everything else is inherited.

---

## 2. New YAML

### 2.1. General Principle

The behavior of `application.yml` and `application-{profile}.yml` inside the JAR (`src/main/resources/`) **does not change**. The difference lies in the `config/` directory structure, which is reorganized under a new `parameters/` folder.

---

### 2.2. File Structure

```
{microservice}/
├── src/main/resources/
│   ├── application.yml              ← (unchanged) Global base inside the JAR
│   └── application-{profile}.yml    ← (unchanged) Profile-specific inside the JAR
│
└── config/
    └── parameters/
        ├── enabled.cfg              ← List of profiles (separated by -) using New YAML
        ├── common.yaml              ← Properties common to ALL environments
        ├── common-cc.yaml           ← Properties common to ALL environments (CC)
        ├── demo.yaml                ← Demo-specific properties
        ├── demo-cc.yaml             ← Demo-specific properties (CC)
        ├── dev.yaml                 ← Dev-specific properties
        ├── dev-cc.yaml              ← Dev-specific properties (CC)
        ├── stg.yaml                 ← Stg-specific properties
        ├── stg-cc.yaml              ← Stg-specific properties (CC)
        ├── prod.yaml                ← Prod-specific properties
        ├── prod-cc.yaml             ← Prod-specific properties (CC)
        │
        └── {level-1}/               ← First profile level (e.g.: cb)
            ├── common.yaml           ← Common to all environments at level 1
            ├── common-cc.yaml        ← Common to all environments at level 1 (CC)
            ├── demo.yaml             ← Demo-specific at level 1
            ├── demo-cc.yaml
            ├── dev.yaml
            ├── dev-cc.yaml
            ├── stg.yaml
            ├── stg-cc.yaml
            ├── prod.yaml
            ├── prod-cc.yaml
            │
            ├── {level-2}/            ← Second level (e.g.: de, es)
            │   ├── common.yaml       ← Common to all environments at level 2
            │   ├── common-cc.yaml
            │   ├── demo.yaml
            │   ├── demo-cc.yaml
            │   ├── dev.yaml
            │   ├── dev-cc.yaml
            │   ├── stg.yaml
            │   ├── stg-cc.yaml
            │   ├── prod.yaml
            │   └── prod-cc.yaml
            │
            └── {level-2}/            ← Another second level under the same level 1
                ├── common.yaml
                ├── ...
                └── prod-cc.yaml
```

> **Naming convention**: The profile in `d.cfg` uses `-` as a level separator. In the filesystem, each segment separated by `-` becomes a subdirectory.
> - `enabled.cfg: cb-de` → filesystem: `parameters/cb/de/`
> - `enabled.cfg: cb-es` → filesystem: `parameters/cb/es/`
> - Up to **2 levels** of nesting are supported.
> - There are not always 2 levels. If the profile has a single level (e.g.: `mx`), only `parameters/mx/` exists without subfolders.

---

### 2.3. Key Components

#### `enabled.cfg`

File containing the **list of profiles/countries** that use the New YAML loading method. Only profiles listed in this file will activate loading from `parameters/`.

The `-` separator in the profile name translates to subdirectories in the filesystem:

| `enabled.cfg` | Filesystem Path |
|--------------|-----------------|
| `cb-de` | `parameters/cb/de/` |
| `cb-es` | `parameters/cb/es/` |
| `mx` | `parameters/mx/` (single level) |

#### `common.yaml` (`parameters/` root)

Contains properties **common to all environments** (demo, dev, stg, prod). Avoids duplicating configuration that repeats across each environment YAML.

#### `common-cc.yaml` (`parameters/` root)

Contains properties **common to all environments for Contact Center**. Only loaded in the CC chain.

#### `{env}.yaml` (`parameters/` root)

Contains **environment-specific properties** that are not common. Equivalent to `config/{env}.yml` in the current solution.

#### `{env}-cc.yaml` (`parameters/` root)

Contains **environment-specific properties for Contact Center**. Only loaded in the CC chain.

#### Profile/Country Folders (`parameters/{level-1}/` and `parameters/{level-1}/{level-2}/`)

Each profile level applies **the same resolution logic** as the `parameters/` root:

| File | Purpose |
|------|---------|
| `common.yaml` | Properties common to all environments **at that level** |
| `common-cc.yaml` | Properties common to all environments **at that level** (CC) |
| `{env}.yaml` | Environment-specific properties **at that level** |
| `{env}-cc.yaml` | Environment-specific properties **at that level** (CC) |

**Key rules:**
- Up to **2 levels** of nesting are supported (not always 2).
- Each level inherits properties from previous levels and can override them.
- Priority is incremental: root < level-1 < level-2.
- If a YAML file would be **empty** (no properties to define), it is better **not to create it**. Empty files add clutter without providing any value.
- Only place properties in `common.yaml` if they exist in **ALL** environments (demo, dev, stg, prod). If a property only exists in some environments, it must go in each specific `{env}.yaml` file.

> **Change from the current solution**: Separate `{country}-cc/` folders no longer exist. Contact Center configuration is integrated within each level as `-cc.yaml` files.

---

### 2.4. Loading Chain

**Standard channel** (example with 2-level profile: `cb-de` → `cb/de/`):

```
application.yml                                    ← (1) Global base (JAR)
       ↓
application-{profile}.yml                          ← (2) Profile inside the JAR
       ↓
config/parameters/common.yaml                      ← (3) Common to all environments
       ↓
config/parameters/{env}.yaml                       ← (4) Environment-specific
       ↓
config/parameters/{level-1}/common.yaml            ← (5) Level 1 common
       ↓
config/parameters/{level-1}/{env}.yaml             ← (6) Environment-specific at level 1
       ↓
config/parameters/{level-1}/{level-2}/common.yaml  ← (7) Level 2 common
       ↓
config/parameters/{level-1}/{level-2}/{env}.yaml   ← (8) Environment-specific at level 2
```

> For single-level profiles (e.g.: `mx`), steps (7) and (8) do not exist.

**Contact Center** (example with 2-level profile: `cb-de` → `cb/de/`):

```
application.yml                                                ← (1) Global base (JAR)
       ↓
application-{profile}.yml                                      ← (2) Profile inside the JAR
       ↓
config/parameters/common.yaml                                  ← (3) Common to all environments
       ↓
config/parameters/common-cc.yaml                               ← (4) Common to all environments CC
       ↓
config/parameters/{env}.yaml                                   ← (5) Environment-specific
       ↓
config/parameters/{env}-cc.yaml                                ← (6) Environment-specific CC
       ↓
config/parameters/{level-1}/common.yaml                        ← (7) Level 1 commons
       ↓
config/parameters/{level-1}/common-cc.yaml                     ← (8) Level 1 commons CC
       ↓
config/parameters/{level-1}/{env}.yaml                         ← (9) Environment-specific at level 1
       ↓
config/parameters/{level-1}/{env}-cc.yaml                      ← (10) Environment-specific at level 1 CC
       ↓
config/parameters/{level-1}/{level-2}/common.yaml              ← (11) Level 2 commons
       ↓
config/parameters/{level-1}/{level-2}/common-cc.yaml           ← (12) Level 2 commons CC
       ↓
config/parameters/{level-1}/{level-2}/{env}.yaml               ← (13) Environment-specific at level 2
       ↓
config/parameters/{level-1}/{level-2}/{env}-cc.yaml            ← (14) Environment-specific at level 2 CC
```

> The CC chain includes **all** standard channel steps plus the `-cc` files interleaved at each level.
> For single-level profiles (e.g.: `mx`), steps (11)–(14) do not exist.

---

### 2.5. Advantages over the Current Solution

| Aspect | Current Solution | New YAML |
|--------|-----------------|----------|
| **Common properties** | Repeated in each `{env}.yml` | Defined once in `common.yaml` |
| **Profile activation** | Implicit by folder existence | Explicit via `enabled.cfg` |
| **Duplication** | High between environments of the same country | Minimal thanks to `common.yaml` per level |
| **Maintenance** | Changing a common property requires editing N files | Change in a single `common.yaml` |
| **Contact Center** | Separate `{country}-cc/` folder with its own YAMLs | `-cc` files interleaved at each level |

---

### 2.6. Precedence Diagram

**Standard channel** (2-level profile):

```
┌──────────────────────────────────────────────────────────────┐
│                      HIGHEST PRIORITY                         │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  parameters/{level-1}/{level-2}/{env}.yaml   (level 2) │  │
│  └───────────────────────┬────────────────────────────────┘  │
│                          │ inherits + overrides               │
│  ┌───────────────────────▼────────────────────────────────┐  │
│  │  parameters/{level-1}/{level-2}/common.yaml (level 2)  │  │
│  └───────────────────────┬────────────────────────────────┘  │
│                          │ inherits + overrides               │
│  ┌───────────────────────▼────────────────────────────────┐  │
│  │  parameters/{level-1}/{env}.yaml             (level 1) │  │
│  └───────────────────────┬────────────────────────────────┘  │
│                          │ inherits + overrides               │
│  ┌───────────────────────▼────────────────────────────────┐  │
│  │  parameters/{level-1}/common.yaml           (level 1)  │  │
│  └───────────────────────┬────────────────────────────────┘  │
│                          │ inherits + overrides               │
│  ┌───────────────────────▼────────────────────────────────┐  │
│  │  parameters/{env}.yaml                         (root)  │  │
│  └───────────────────────┬────────────────────────────────┘  │
│                          │ inherits + overrides               │
│  ┌───────────────────────▼────────────────────────────────┐  │
│  │  parameters/common.yaml                       (root)   │  │
│  └───────────────────────┬────────────────────────────────┘  │
│                          │ inherits + overrides               │
│  ┌───────────────────────▼────────────────────────────────┐  │
│  │  application.yml (src/main/resources/)          (JAR)  │  │
│  └────────────────────────────────────────────────────────┘  │
│                                                              │
│                      LOWEST PRIORITY                          │
└──────────────────────────────────────────────────────────────┘
```

**Contact Center** (2-level profile):

```
┌──────────────────────────────────────────────────────────────────┐
│                        HIGHEST PRIORITY                           │
│                                                                  │
│  ┌────────────────────────────────────────────────────────────┐  │
│  │  parameters/{l1}/{l2}/{env}-cc.yaml           (level 2 CC) │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/{l1}/{l2}/{env}.yaml                 (level 2) │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/{l1}/{l2}/common-cc.yaml         (level 2 CC)  │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/{l1}/{l2}/common.yaml               (level 2)  │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/{l1}/{env}-cc.yaml                (level 1 CC) │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/{l1}/{env}.yaml                      (level 1) │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/{l1}/common-cc.yaml              (level 1 CC)  │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/{l1}/common.yaml                    (level 1)  │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/{env}-cc.yaml                       (root CC)  │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/{env}.yaml                             (root)  │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/common-cc.yaml                     (root CC)   │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  parameters/common.yaml                           (root)   │  │
│  └───────────────────────┬────────────────────────────────────┘  │
│                          │ inherits + overrides                   │
│  ┌───────────────────────▼────────────────────────────────────┐  │
│  │  application.yml (src/main/resources/)              (JAR)  │  │
│  └────────────────────────────────────────────────────────────┘  │
│                                                                  │
│                        LOWEST PRIORITY                            │
└──────────────────────────────────────────────────────────────────┘
```

> `{l1}` = `{level-1}`, `{l2}` = `{level-2}` (abbreviated for space).
