# Setup New Spring Boot Project

Create a new Spring Boot project with Java 21+ and best practices.

## What This Does
1. Generates Spring Boot project using Spring Initializr
2. Configures for Java 21+ with modern features (records, pattern matching, virtual threads)
3. Sets up recommended dependencies
4. Follows dependency injection best practices

## Command (Using Spring Initializr CLI)
```bash
# Install Spring Boot CLI first if needed:
# sdk install springboot

spring init \
  --boot-version=3.2.1 \
  --type=gradle-project \
  --java-version=21 \
  --packaging=jar \
  --name=<project-name> \
  --group-id=dev.atinroy \
  --artifact-id=<project-name> \
  --dependencies=web,data-jpa,postgresql,lombok,validation \
  <project-name>

# Explain:
# --boot-version=3.2.1 = Latest stable (supports virtual threads)
# --java-version=21 = Modern Java features (records, sealed classes)
# --dependencies:
#   - web: REST APIs
#   - data-jpa: Database ORM (avoid raw SQL)
#   - postgresql: Production-ready DB
#   - lombok: Reduce boilerplate (@Getter, @Setter)
#   - validation: Bean validation (@Valid, @NotNull)
```

## Alternative: Web UI
```bash
# Open in browser
https://start.spring.io/

# Download and extract, then:
./gradlew bootRun
```

## Post-Setup Checklist
- [ ] Configure `application.yml` (prefer YAML over .properties for readability)
- [ ] Set up database connection in `application-dev.yml`
- [ ] Create package structure: `controller/`, `service/`, `repository/`, `model/`
- [ ] Add `.env` file for secrets (never commit credentials!)

## Teaching Notes
**Why Records over Classes for DTOs?**
```java
// Old way (verbose)
public class UserDTO {
    private final String name;
    private final String email;
    // Constructor, getters, equals, hashCode, toString...
}

// Records (Java 14+, concise)
public record UserDTO(String name, String email) {}
```
**Trade-off:** Records are immutable by default (good for DTOs), but can't be extended.

**Why Constructor Injection over @Autowired?**
```java
// Prefer this (immutable, testable)
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

// Over this (@Autowired makes testing harder)
@Autowired
private UserRepository userRepository;
```
