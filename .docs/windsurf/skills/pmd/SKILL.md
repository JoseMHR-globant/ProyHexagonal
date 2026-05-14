---
name: pmd
description: PMD rules enforced during build. Use when reviewing code for PMD violations or understanding build failures related to static analysis.
---

# PMD Ruleset Reference

> PMD rules enforced during build via `maven-pmd-plugin:3.26.0` with ruleset `modelbank_pmd_ruleset.xml` from `mb-api-build-tools:6.0.5`.

---

## 1. Error Prone Rules

Rules that detect common programming mistakes.

| Rule | Description |
|------|-------------|
| `AvoidBranchingStatementAsLastInLoop` | Avoid `break`/`continue` as last statement in loop |
| `AvoidDecimalLiteralsInBigDecimalConstructor` | Use `BigDecimal.valueOf()` instead of `new BigDecimal(double)` |
| `AvoidMultipleUnaryOperators` | Avoid `!!` or `--` operators |
| `AvoidUsingOctalValues` | Avoid octal literals (e.g., `010`) |
| `BrokenNullCheck` | Detect broken null checks |
| `ClassCastExceptionWithToArray` | Use `toArray(new Type[0])` instead of `toArray()` |
| `DoNotCallGarbageCollectionExplicitly` | Never call `System.gc()` |
| `DoNotThrowExceptionInFinally` | Never throw exceptions in finally blocks |
| `DontImportSun` | Never import `sun.*` packages |
| `EmptyCatchBlock` | No empty catch blocks |
| `EmptyFinalizer` | No empty `finalize()` methods |
| `EqualsNull` | Never use `x.equals(null)` |
| `IdempotentOperations` | Avoid operations with no effect (e.g., `x = x`) |
| `InstantiationToGetClass` | Use `.class` instead of `new Foo().getClass()` |
| `JumbledIncrementer` | Detect wrong loop variable increment |
| `MisplacedNullCheck` | Null check after potential NPE |
| `MissingStaticMethodInNonInstantiatableClass` | Utility classes need static methods |
| `NonCaseLabelInSwitch` | Only `case` labels in switch |
| `NonStaticInitializer` | Avoid non-static initializer blocks |
| `NullAssignment` | Avoid assigning `null` to variables |
| `OverrideBothEqualsAndHashcode` | Override both `equals()` and `hashCode()` |
| `ProperCloneImplementation` | Implement `clone()` correctly |
| `ReturnEmptyCollectionRatherThanNull` | Return empty collection, not `null` |
| `ReturnFromFinallyBlock` | Never return from finally block |
| `SimpleDateFormatNeedsLocale` | Always specify `Locale` in `SimpleDateFormat` |
| `StringBufferInstantiationWithChar` | Don't instantiate `StringBuffer` with char |
| `SuspiciousOctalEscape` | Detect suspicious octal escapes |
| `UnconditionalIfStatement` | Avoid `if(true)` or `if(false)` |
| `UnnecessaryCaseChange` | Avoid unnecessary `toUpperCase()`/`toLowerCase()` |
| `UnnecessaryConversionTemporary` | Avoid unnecessary conversion objects |
| `UnusedNullCheckInEquals` | Detect unused null checks in equals |
| `UseEqualsToCompareStrings` | Use `.equals()` for String comparison |
| `UselessOperationOnImmutable` | Detect useless operations on immutable objects |
| `MethodWithSameNameAsEnclosingClass` | Method name should not match class name |
| `SuspiciousHashcodeMethodName` | Detect `hashcode()` instead of `hashCode()` |
| `SuspiciousEqualsMethodName` | Detect `equal()` instead of `equals()` |

### Finalizer Rules

| Rule | Description |
|------|-------------|
| `FinalizeOnlyCallsSuperFinalize` | Finalizer only calls super |
| `FinalizeOverloaded` | Don't overload `finalize()` |
| `FinalizeDoesNotCallSuperFinalize` | Always call `super.finalize()` |
| `FinalizeShouldBeProtected` | `finalize()` should be protected |
| `AvoidCallingFinalize` | Never call `finalize()` directly |

---

## 2. Code Style Rules

| Rule | Description |
|------|-------------|
| `EmptyControlStatement` | No empty `if`/`for`/`while`/`switch` |
| `UnnecessarySemicolon` | No unnecessary semicolons |
| `TooManyStaticImports` | Maximum **10** static imports per file |
| `ShortMethodName` | Method names must be descriptive |
| `UnnecessaryBoxing` | Avoid unnecessary boxing/unboxing |

---

## 3. Best Practices Rules

| Rule | Description |
|------|-------------|
| `SimplifiableTestAssertion` | Simplify test assertions |
| `ReplaceVectorWithList` | Use `ArrayList` instead of `Vector` |
| `ReplaceHashtableWithMap` | Use `HashMap` instead of `Hashtable` |
| `ReplaceEnumerationWithIterator` | Use `Iterator` instead of `Enumeration` |
| `PrimitiveWrapperInstantiation` | Use `valueOf()` instead of `new Integer()` |

### JUnit Rules

| Rule | Description |
|------|-------------|
| `JUnitSpelling` | Correct JUnit method spelling |
| `TestClassWithoutTestCases` | Test classes must have test methods |
| `UnnecessaryBooleanAssertion` | Avoid `assertTrue(true)` |
| `UnitTestShouldUseBeforeAnnotation` | Use `@Before` annotation |
| `UnitTestShouldUseAfterAnnotation` | Use `@After` annotation |
| `UnitTestShouldUseTestAnnotation` | Use `@Test` annotation |
| `JUnit4SuitesShouldUseSuiteAnnotation` | Use `@Suite` annotation |
| `JUnitUseExpected` | Use `expected` attribute for exceptions |

---

## 4. Performance Rules

| Rule | Description |
|------|-------------|
| `AddEmptyString` | Don't use `"" + x` for conversion |
| `AppendCharacterWithChar` | Use `append('c')` instead of `append("c")` |
| `AvoidArrayLoops` | Use `System.arraycopy()` or `Arrays.copyOf()` |
| `BigIntegerInstantiation` | Use `BigInteger.valueOf()` |
| `ConsecutiveLiteralAppends` | Combine consecutive string appends |
| `InefficientEmptyStringCheck` | Use `isEmpty()` instead of `length() == 0` |
| `InefficientStringBuffering` | Avoid inefficient string buffering |
| `InsufficientStringBufferDeclaration` | Set initial capacity for `StringBuilder` |
| `OptimizableToArrayCall` | Use `toArray(new Type[0])` |
| `TooFewBranchesForSwitch` | Use `if` for switches with < 3 cases |
| `StringInstantiation` | Don't use `new String()` |
| `StringToString` | Don't call `toString()` on String |
| `UseStringBufferForStringAppends` | Use `StringBuilder` in loops |
| `UseStringBufferLength` | Use `length()` instead of `toString().length()` |
| `UseArraysAsList` | Use `Arrays.asList()` |
| `UseArrayListInsteadOfVector` | Use `ArrayList` instead of `Vector` |
| `UselessStringValueOf` | Avoid useless `String.valueOf()` |
| `UseIndexOfChar` | Use `indexOf(char)` instead of `indexOf(String)` |

---

## 5. Multithreading Rules

| Rule | Description |
|------|-------------|
| `AvoidSynchronizedAtMethodLevel` | Synchronize on specific blocks, not methods |
| `AvoidThreadGroup` | Don't use `ThreadGroup` |
| `AvoidUsingVolatile` | Avoid `volatile` unless necessary |
| `DontCallThreadRun` | Call `start()`, not `run()` |
| `DoubleCheckedLocking` | Double-checked locking is broken |
| `NonThreadSafeSingleton` | Singletons must be thread-safe |
| `UseNotifyAllInsteadOfNotify` | Use `notifyAll()` instead of `notify()` |
| `UnsynchronizedStaticFormatter` | Static formatters must be synchronized |

---

## 6. Custom Project Rules

### 6.1 AuthCommAccessAnnotationRequired (Critical)

Every REST endpoint **must** have an access annotation.

**Valid annotations:**
- `@Access.Public`
- `@Access.EndUser`
- `@Access.BoundAgent`
- `@Access.UnboundAgent`
- `@Access.ServiceUser`
- `@Access.ExternalServiceUser`
- `@Access.Ivr`
- `@WebSocketCustomId`

```java
// ❌ WRONG - Missing access annotation
@RestController
public class CustomerController {
    @GetMapping("/customers")
    public List<Customer> getAll() { ... }
}

// ✅ CORRECT - Has access annotation
@RestController
public class CustomerController {
    @Access.EndUser
    @GetMapping("/customers")
    public List<Customer> getAll() { ... }
}
```

### 6.2 AvoidTargetMultiCountryAnnotation

The `@TargetMultiCountry` annotation is **forbidden**.

```java
// ❌ WRONG
@TargetMultiCountry
public class MyService { ... }

// ✅ CORRECT
public class MyService { ... }
```

---

## 7. Running PMD

### Check violations
```bash
mvn pmd:check
```

### Generate report
```bash
mvn pmd:pmd
# Report at: target/site/pmd.html
```

### Skip PMD (not recommended)
```bash
mvn compile -Dpmd.skip=true
```
