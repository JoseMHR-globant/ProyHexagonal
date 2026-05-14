---
trigger: always_on
description: Project-local architecture standards for MC-Pruebas-Windsurf. Always read the referenced documents before working on this codebase.
---

# MC-Pruebas-Windsurf — Project Rules Pointer

This project ships its **own copy** of the architecture standards and skills, so the same conventions apply on every machine without depending on user-level Windsurf memories.

## Source of truth

- **Global rules**: `.docs/windsurf/memories/global_rules.md`
- **Skills folder**: `.docs/windsurf/skills/<skill-name>/SKILL.md`

Available skills in this project:

- `flyway` — SQL migrations
- `javadoc` — Javadoc documentation standards
- `mappers` — MapStruct dual-interface convention (parent + `Gs` concrete)
- `newyaml` — Spring Boot YAML configuration loading
- `openapi` — OpenAPI/Swagger documentation in DTOs and Controllers
- `pmd` — PMD ruleset enforced during build
- `unittesting` — Unit testing with Mockito + AssertJ + Instancio

## Required behaviour

Whenever Cascade is asked to read, modify or create code in this repository:

1. **Always read** `.docs/windsurf/memories/global_rules.md` at the start of the task to load the architecture and naming conventions.
2. **Consult the matching `SKILL.md`** before producing the corresponding artefact:
   - Creating/modifying a mapper → read `.docs/windsurf/skills/mappers/SKILL.md` first.
   - Writing a unit test → read `.docs/windsurf/skills/unittesting/SKILL.md` first.
   - Adding/modifying a DTO or controller documentation → read `.docs/windsurf/skills/openapi/SKILL.md`.
   - Working with YAML config → read `.docs/windsurf/skills/newyaml/SKILL.md`.
   - Touching SQL migrations → read `.docs/windsurf/skills/flyway/SKILL.md`.
   - Adding/Editing Javadoc → read `.docs/windsurf/skills/javadoc/SKILL.md`.
   - Reviewing PMD violations / build failures → read `.docs/windsurf/skills/pmd/SKILL.md`.
3. **Apply the conventions** from those documents to every code change (naming with `Gs` prefix on impls, hexagonal package layout, mapper dual-interface, `@Pattern` validators on every string input, AAA tests with `@ExtendWith(MockitoExtension.class)`, etc.).

## Project-specific simplifications

This is a **standalone playground** without corporate dependencies. Therefore, when a rule or skill mentions the following, treat it as **not applicable** here:

- `@TargetMultiBean`, `BankIdConstant.DCP`, multi-bank generics on mappers — there is no multi-bank scenario.
- `<base-package>.error.commons.exception` — use standard Spring/Jakarta exceptions instead.
- `mb-api-build-tools` PMD ruleset — not wired in this project.
- Authorization annotations (`@Access.EndUser`, etc.) — not wired in this project.
- Feign clients / JPA — facades **mock** their data in memory.

Everything else from the global rules and skills **does apply** verbatim: hexagonal layering, naming suffixes, `Gs` prefix on impls, models-by-layer, mapper conventions (parent interface + `Gs` concrete with `@Mapper`), `@Pattern` validation on every string input, Javadoc in English, AAA test pattern.
