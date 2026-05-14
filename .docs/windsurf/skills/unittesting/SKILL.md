---
name: unittesting
description: Standards for unit testing in Java microservices with Spring Boot. Use when creating or modifying test classes.
---

# Unit Testing Standards

## 1. Naming Conventions

| Element | Pattern | Example |
|---------|---------|---------|
| Test class | `{ClassName}Test` | `CreateCustomerUsecaseTest` |
| Test method | `should{Expected}When{Condition}` | `shouldThrowExceptionWhenEmailIsNull` |
| Test method (alt) | `{method}_{scenario}_{expected}` | `execute_nullEmail_throwsException` |

## 2. Test Structure (AAA Pattern)

```java
@Test
void shouldCreateCustomerWhenValidData() {
    // Arrange (Given)
    Customer customer = Customer.builder()
        .name("John Doe")
        .email("john@example.com")
        .build();
    when(facade.save(any())).thenReturn(customer);

    // Act (When)
    Customer result = usecase.execute(customer);

    // Assert (Then)
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("John Doe");
    verify(facade).save(customer);
}
```

## 3. Required ✅

- One test class per production class
- Test only public methods
- Use `@DisplayName` for complex test scenarios
- Use AssertJ for assertions (`assertThat()`)
- Use Mockito for mocking dependencies
- Use `@ExtendWith(MockitoExtension.class)` (not `@RunWith`)
- Use Instancio for random test data generation
- Verify interactions with `verify()` when relevant
- Test edge cases: null, empty, boundary values
- Test exception scenarios with `assertThrows()`

## 4. Forbidden ❌

- `@SpringBootTest` for unit tests → use only for integration tests
- Real database/network calls in unit tests
- `Thread.sleep()` in tests
- Tests depending on execution order
- Hardcoded dates/times → use `Clock` injection
- `@Autowired` in unit tests → use `@Mock` and `@InjectMocks`
- Manual `@BeforeEach setUp()` with `mock(Class.class)` + constructor wiring → use `@Mock` fields and `@InjectMocks` (Mockito does the wiring)
- Multiple assertions without clear separation
- Catching exceptions manually → use `assertThrows()`
- Testing private methods directly
- Tests without assertions
- Reimplementing controller assertions inline (`assertNotNull` + `assertEquals(HttpStatus.OK...)` + `assertSame(body)`) → use the `ControllerTestUtils` helpers (see §9)

## 5. Mocking Rules

| Layer | What to Mock |
|-------|--------------|
| **Usecase** | Facades, Services |
| **Facade** | Clients, Repositories |
| **Controller** | Usecase, Mapper |
| **Mapper** | Nothing (test real mapping) |

## 6. Coverage Requirements

| Metric | Minimum |
|--------|---------|
| Line coverage | 80% |
| Branch coverage | 80% |
| Usecase coverage | 90% |

## 7. Test Data Generation

```java
// ✅ Use Instancio for random data
Customer customer = Instancio.create(Customer.class);

// ✅ Use Instancio with customization
Customer customer = Instancio.of(Customer.class)
    .set(field(Customer::getEmail), "test@example.com")
    .create();

// ❌ Avoid hardcoded test data
Customer customer = new Customer("John", "john@test.com");
```

## 8. Wiring: prefer `@Mock` + `@InjectMocks` over manual setup

### ❌ Forbidden — manual `@BeforeEach` wiring

```java
class MyFacadeTest {

  private MyClient client;
  private MyMapper mapper;
  private MyFacade facade;

  @BeforeEach
  void setUp() {
    client = mock(MyClient.class);
    mapper = mock(MyMapper.class);
    facade = new MyFacade(client, mapper);
  }
}
```

### ✅ Correct — Mockito does the wiring

```java
@ExtendWith(MockitoExtension.class)
class MyFacadeTest {

  @Mock private MyClient client;

  @Mock private MyMapper mapper;

  @InjectMocks private MyFacade facade;
}
```

Mockito handles constructor injection automatically when the production class is annotated with `@RequiredArgsConstructor` (or has an explicit constructor). The class under test is built before each test, so the `@BeforeEach` is unnecessary noise.

> Use `mock(SomeClass.class)` only for **transient** test data inside a single test method (e.g. mocking a return value of a collaborator), never for the class under test or its long-lived collaborators.

---

## 9. Controller test helpers — `ControllerTestUtils`

The shared utility lives at:
`src/test/java/<base-package>/testutils/ControllerTestUtils.java`

Use it for the standard `ResponseEntity` assertions in **every** controller test. It removes 3 lines of boilerplate and keeps assertions consistent across the codebase.

### 9.1 `assertResponseIsOK(response, expectedBody)`

Replaces the typical happy-path triplet (`assertNotNull` + `assertEquals(HttpStatus.OK)` + body check):

```java
// ❌ Forbidden — verbose, easy to drift
assertNotNull(response);
assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
assertSame(dtoResponse, response.getBody());

// ✅ Correct
ControllerTestUtils.assertResponseIsOK(response, dtoResponse);
```

### 9.2 `assertResponseIsNoContent(response)`

Replaces the empty/no-content branch assertions:

```java
// ❌ Forbidden
assertNotNull(response);
assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
assertFalse(response.hasBody());

// ✅ Correct
ControllerTestUtils.assertResponseIsNoContent(response);
```

### 9.3 Side effect — clean imports

After switching to `ControllerTestUtils`, also remove the now-unused imports (`assertEquals`, `assertNotNull`, `assertSame`, `assertFalse`, `HttpStatus`). Unused imports break the build under PMD/Spotless.

---

## 10. Example Test Class

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("CreateCustomerUsecase Tests")
class CreateCustomerUsecaseTest {

    @Mock
    private CustomerFacade facade;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private CreateCustomerUsecase usecase;

    @Test
    @DisplayName("Should create customer when valid data provided")
    void shouldCreateCustomerWhenValidData() {
        // Arrange
        Customer customer = Instancio.create(Customer.class);
        when(facade.save(any())).thenReturn(customer);

        // Act
        Customer result = usecase.execute(customer);

        // Assert
        assertThat(result).isNotNull();
        verify(facade).save(customer);
    }

    @Test
    @DisplayName("Should throw exception when email is null")
    void shouldThrowExceptionWhenEmailIsNull() {
        // Arrange
        Customer customer = Instancio.of(Customer.class)
            .set(field(Customer::getEmail), null)
            .create();

        // Act & Assert
        assertThrows(ValidationException.class, 
            () -> usecase.execute(customer));
        verify(facade, never()).save(any());
    }
}
```
