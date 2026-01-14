# DSA Practice - Java

Data Structures & Algorithms practice workspace using modern Java (25+).

## Structure

```
dsa-java/
├── src/
│   ├── main/java/dev/atinroy/dsa/     # Solution implementations
│   └── test/java/dev/atinroy/dsa/     # JUnit tests
├── pom.xml                             # Maven configuration
└── README.md
```

## Tech Stack

- **Java 25+** (with preview features enabled)
- **JUnit 5** for testing
- **AssertJ** for fluent assertions
- **Lombok** for reducing boilerplate (provided scope)

## Topic Organization

Organize solutions by topic using sub-packages:

```
dev.atinroy.dsa/
├── arrays/          # Array problems
├── dp/              # Dynamic Programming
├── graphs/          # Graph algorithms
├── trees/           # Tree problems
├── linkedlists/     # Linked list problems
└── ...
```

## Usage

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=SolutionTest
```

### Run Main Method (if present)
```bash
mvn exec:java -Dexec.mainClass="dev.atinroy.dsa.Solution"
# or with default configured class:
mvn exec:java
```

### Compile Only
```bash
mvn compile
```

### Clean Build
```bash
mvn clean compile
```

## Writing Solutions

1. **Create solution class** in `src/main/java/dev/atinroy/dsa/[topic]/`
2. **Add tests** in `src/test/java/dev/atinroy/dsa/[topic]/`
3. **Use AssertJ** for readable assertions
4. **Optional:** Add `main` method for quick manual testing

## Example: Adding a New Problem

```java
// src/main/java/dev/atinroy/dsa/arrays/BinarySearch.java
package dev.atinroy.dsa.arrays;

public class BinarySearch {
    public int search(int[] nums, int target) {
        // implementation
    }
}
```

```java
// src/test/java/dev/atinroy/dsa/arrays/BinarySearchTest.java
package dev.atinroy.dsa.arrays;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class BinarySearchTest {
    @Test
    void shouldFindTarget() {
        var bs = new BinarySearch();
        assertThat(bs.search(new int[]{1,2,3}, 2)).isEqualTo(1);
    }
}
```

## Tips

- Use Lombok's `@Data`, `@Builder` for test helper classes (e.g., `TreeNode`, `ListNode`)
- Leverage JUnit 5's `@Nested`, `@DisplayName` for organized test suites
- Use `@ParameterizedTest` for testing multiple inputs
- Write Big O complexity as comments above each solution
