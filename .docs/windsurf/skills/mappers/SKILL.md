---
name: mappers
description: Standards for MapStruct mappers in Java microservices (Controller and Facade layers). Use when creating or modifying mapper interfaces.
---

# Mapper Standards (MapStruct)

> Mappers exist **only** at boundary layers: Controller (`Dto` ↔ Domain) and Facade (Domain ↔ `Cdo`/Entity). Usecases never use mappers.

---

## 1. Naming and Layout

| Element | Pattern | Example |
|---------|---------|---------|
| Generic parent interface | `{Verb}{Resource}{Layer}Mapper` | `PostActivateThirdPartyBenefitControllerResponseMapper` |
| DCP concrete implementation | `Gs{Verb}{Resource}{Layer}Mapper` | `GsPostActivateThirdPartyBenefitControllerResponseMapper` |
| Layer suffix (controller, request) | `ControllerRequestMapper` | — |
| Layer suffix (controller, response) | `ControllerResponseMapper` | — |
| Layer suffix (facade) | `FacadeMapper` | — |

The parent interface lives next to the concrete one and declares the contract with **generic type parameters** (alphabetical order, bound by domain/DTO superclasses). The `Gs` interface binds those generics to the DCP-specific concrete types.

---

## 2. Required Annotations on the `Gs` Interface

```java
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
@TargetMultiBean(bankId = BankIdConstant.DCP)
public interface GsXxxMapper extends XxxMapper<...> {}
```

| Annotation | Purpose |
|------------|---------|
| `@Mapper(componentModel = SPRING)` | MapStruct generates a Spring bean (`XxxMapperImpl`) |
| `unmappedTargetPolicy = ReportingPolicy.IGNORE` | Avoids build failures for fields the mapper intentionally does not map |
| `@TargetMultiBean(bankId = BankIdConstant.DCP)` | Tells MultiBean which bean to inject for the DCP bank |

> Omit `unmappedTargetPolicy` only when **every** target field is mapped (rare). The default `WARN`/`ERROR` will break the build otherwise.

---

## 3. When to Redeclare Methods (KEY RULE)

The parent generic interface already declares the mapping methods. The MapStruct annotation processor **automatically generates** the concrete `XxxMapperImpl` from the type parameters bound in `extends`.

### ❌ Forbidden — redundant override

Do NOT redeclare a method in the `Gs` interface if it adds **nothing** (no `@Mapping`, no custom logic, no different signature):

```java
// ❌ WRONG — the @Override adds nothing, MapStruct already generates it
public interface GsPostActivateThirdPartyBenefitControllerResponseMapper
    extends PostActivateThirdPartyBenefitControllerResponseMapper<
        GsActivateBenefitResponse, GsPostActivateThirdPartyBenefitResponseDto> {

  /**
   * Maps the domain activation response to the response DTO.
   *
   * @param response the domain activation response
   * @return the mapped response DTO
   */
  @Override
  GsPostActivateThirdPartyBenefitResponseDto mapToDto(GsActivateBenefitResponse response);
}
```

### ✅ Correct — empty body when no customization

```java
// ✅ RIGHT — concrete types fixed in `extends`, MapStruct does the rest
public interface GsPostActivateThirdPartyBenefitControllerResponseMapper
    extends PostActivateThirdPartyBenefitControllerResponseMapper<
        GsActivateBenefitResponse, GsPostActivateThirdPartyBenefitResponseDto> {}
```

### ✅ Correct — redeclare ONLY to add `@Mapping` or custom abstract method

```java
// ✅ RIGHT — the @Override exists because of @Mapping(target=..., source=...)
public interface GsActivateBenefitFacadeMapper
    extends ActivateBenefitFacadeMapper<GsActivateBenefitResponse> {

  @Override
  @Mapping(target = "url", source = "redirectURL.url")
  GsActivateBenefitResponse map(GsWrapperActivateContractBenefitCdo cdo);
}
```

### Decision matrix

| Situation | Action |
|-----------|--------|
| Direct field mapping (same names) | Empty body `{}` |
| Different field names, nested paths | Redeclare with `@Mapping(target=..., source=...)` |
| Need a `default` helper method | Add it (no `@Override` needed for new methods) |
| Need to wire `@Mapping` between unrelated fields | Redeclare with `@Mapping(...)` |

---

## 4. Field Mapping with `@Mapping`

```java
@Override
@Mapping(target = "url", source = "redirectURL.url")
@Mapping(target = "planName", source = "cmsPlanContent.planName")
GsResponse map(GsWrapperCdo cdo);
```

- ✅ Use dotted notation for nested source paths (`redirectURL.url`)
- ✅ Use `expression = "java(...)"` only for trivial expressions; prefer `default` methods for anything more complex
- ❌ Do NOT use `@Mapping(ignore = true)` — rely on `unmappedTargetPolicy = IGNORE` instead
- ❌ Do NOT add `@Mapping` annotations that match the default behaviour (same name, same type)

---

## 5. Layer-Specific Rules

### 5.1 Controller mappers

- Path: `{feature}/controller/mapper/`
- Direction: **bidirectional** (`Dto` ↔ Domain), usually two interfaces:
  - `{Feature}ControllerRequestMapper` — `mapToDomain(Dto) → Domain`
  - `{Feature}ControllerResponseMapper` — `mapToDto(Domain) → Dto`
- Mappers receive only `Dto` and Domain types — never `Cdo` or Entity.

### 5.2 Facade mappers

- Path: `common/facade/{resource}/mapper/`
- Direction: **bidirectional** (Domain ↔ `Cdo`/Entity).
- Mappers receive only Domain and `Cdo`/Entity types — never `Dto`.

### 5.3 Usecase mappers

- **None.** Usecases work with Domain only.

---

## 6. Imports — Avoid Over-Importing

`@Mapping` is only needed if the interface body uses it. After removing a redundant override, also remove the now-unused `org.mapstruct.Mapping` import.

The `mb-api-build-tools` PMD ruleset enforces `TooManyStaticImports` (max 10) and unused imports will fail the build.

---

## 7. Javadoc on Mappers

Follow the [`javadoc`](%USERPROFILE%\.codeium\windsurf\skills\javadoc\SKILL.md) skill. For mappers specifically:

- ✅ Class-level Javadoc explaining **what** the mapper translates between (with `{@link ...}` to source and target).
- ✅ Method-level Javadoc only when the method is redeclared (because of `@Mapping` or custom logic).
- ❌ Do NOT add method Javadoc just to repeat the parent interface's contract — if you didn't redeclare the method, you don't add Javadoc for it here.

---

## 8. Testing Mappers

- One test class per mapper (`{MapperName}Test.java`).
- **No mocks.** Test the real generated implementation via `Mappers.getMapper(GsXxxMapper.class)`.
- Cover: happy path with all fields populated, `null` input branch, edge cases for nested fields.

> 📖 Full testing standards: see Skill [`unittesting`](%USERPROFILE%\.codeium\windsurf\skills\unittesting\SKILL.md).

---

## 9. Quick Checklist Before Committing a Mapper

- [ ] `Gs` interface annotated with `@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)` and `@TargetMultiBean(bankId = DCP)`.
- [ ] Body is empty `{}` **unless** at least one method needs `@Mapping` or custom logic.
- [ ] No `@Override` declaration that just repeats the parent's signature without adding `@Mapping`.
- [ ] All imports are used (no orphaned `Mapping`, `Mappings`, etc. after removing overrides).
- [ ] Class-level Javadoc explains the translation in English.
- [ ] Test class exists and exercises real mapping, including the `null` branch.
