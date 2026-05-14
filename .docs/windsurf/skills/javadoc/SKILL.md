---
name: javadoc
description: Standards for Javadoc documentation in Java microservices. Use when creating or modifying classes, methods, or fields.
---

# Javadoc Standards

## 1. Language

All code must include Javadoc documentation in **English**.

## 2. Class-Level Javadoc

```java
/**
 * Service responsible for customer creation and validation.
 *
 * @author Team Name
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CreateCustomerUsecase {
    // ...
}
```

## 3. Method-Level Javadoc

```java
/**
 * Creates a new customer in the system.
 *
 * @param customer the customer domain object to create
 * @return the created customer with generated ID
 * @throws CustomerAlreadyExistsException if email is already registered
 */
public Customer execute(Customer customer) {
    // ...
}
```

## 4. Field-Level Javadoc

```java
/** Customer unique identifier. */
private String id;

/** Customer full name. */
private String name;
```

## 5. Requirements

| Element | Required | Content |
|---------|----------|---------|
| Classes | ✅ | Purpose and responsibility |
| Public methods | ✅ | `@param`, `@return`, `@throws` |
| Fields | ✅ | Brief description |
| Private methods | ⚠️ Optional | Only if complex logic |

## 6. Best Practices

- ✅ Write in **English**
- ✅ Start with a verb for methods: "Creates", "Validates", "Returns"
- ✅ Be concise but descriptive
- ✅ Document all `@param` parameters
- ✅ Document `@return` unless `void`
- ✅ Document checked exceptions with `@throws`
- ❌ Avoid redundant comments like "Gets the name" for `getName()`
- ❌ Avoid empty or placeholder Javadoc
