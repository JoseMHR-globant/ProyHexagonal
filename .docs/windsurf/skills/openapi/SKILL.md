---
name: openapi
description: Standards for OpenAPI/Swagger documentation in DTOs and Controllers. Use when creating or modifying DTOs, Controllers, or API endpoints.
---

# OpenAPI Documentation Standards

## 1. Required `@Schema` in DTOs

All `Dto` fields must be annotated with `@Schema` for OpenAPI documentation:

```java
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer creation request")
public class CustomerRequestDto implements Serializable {

    private static final long serialVersionUID = 3456789089767543L;

    @Schema(description = "Customer full name", example = "John Doe")
    private String name;

    @Schema(description = "Customer email address", example = "john@example.com")
    private String email;

    @Schema(description = "Customer age", example = "30", minimum = "18")
    private Integer age;
}
```

## 2. `@ApiResponse` with Examples

Controllers must include `@ApiResponse` with proper examples:

```java
@Operation(
    summary = "Create customer",
    description = "Creates a new customer in the system")
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Customer created successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CustomerResponseDto.class),
            examples = @ExampleObject(
                name = "Success",
                value = """
                    {
                      "id": "12345",
                      "name": "John Doe",
                      "status": "ACTIVE"
                    }
                    """)))
})
@PostMapping
public ResponseEntity<CustomerResponseDto> create(
        @Valid @RequestBody CustomerRequestDto request) {
    // ...
}
```

## 3. Rules

- ✅ All `Dto` classes must have `@Schema` at class level
- ✅ All `Dto` fields must have `@Schema` with `description` and `example`
- ✅ Controllers must document `200`/`204` responses with examples
- ❌ Do NOT document `4XX` or `5XX` error responses (handled globally)
