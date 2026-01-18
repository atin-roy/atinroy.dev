# Counter Backend - Complete Architecture Guide

## 🏛️ Welcome to the Museum

You're exploring a **complete, production-grade Spring Boot REST API** built for learning. Every package, class, and annotation serves a purpose. This guide is your museum tour.

---

## 📍 Project Structure

```
src/main/java/dev/atinroy/counterBackend/
├── 🚪 README.md                      ← Start here: overview & request flow
├── 📦 ARCHITECTURE_GUIDE.md           ← You are here
│
├── application/
│   └── CounterBackendApplication.java ← Entry point (Spring Boot)
│
├── 🔐 security/
│   ├── README.md                      ← Authentication & authorization concepts
│   ├── SecurityConfig.java            ← Security rules (who can access what)
│   └── CustomUserDetailsService.java  ← Load user from database
│
├── 🌐 controller/
│   ├── README.md                      ← HTTP endpoints & request/response examples
│   └── ApiController.java             ← REST endpoints (HTTP handlers)
│
├── 📦 dto/ (Data Transfer Objects)
│   ├── README.md                      ← API contracts & validation
│   ├── user/
│   │   ├── CreateUserRequest.java     ← Registration form schema
│   │   └── UserResponse.java          ← User data in responses
│   └── counter/
│       ├── CreateCounterRequest.java  ← Create counter form
│       ├── UpdateCounterRequest.java  ← Update counter form
│       ├── IncrementCounterRequest.java ← Increment operation
│       └── CounterResponse.java       ← Counter in response
│
├── 🗄️  entity/
│   ├── README.md                      ← Database models & JPA concepts
│   ├── User.java                      ← User table
│   └── Counter.java                   ← Counter table
│
├── 💾 repository/
│   ├── README.md                      ← Database query layer & JpaRepository
│   ├── UserRepository.java            ← Queries for users
│   └── CounterRepository.java         ← Queries for counters
│
└── 🔄 mapper/
    ├── README.md                      ← Entity ↔ DTO conversion (MapStruct)
    └── UserMapper.java                ← Convert User → UserResponse
```

---

## 🔄 Complete Request Flow (The Big Picture)

Here's how a request travels through your entire application:

```
1. FRONTEND
   └─→ POST /api/users/123/counters
       Authorization: Basic base64(email:password)
       { "counterName": "Push-ups", "counterValue": 0 }

2. SECURITY FILTER CHAIN (security/SecurityConfig.java)
   ├─→ Check: Is this endpoint public? (POST /api/users is YES, others need auth)
   ├─→ Extract Authorization header
   ├─→ Call CustomUserDetailsService.loadUserByUsername(email)
   │   └─→ Query: UserRepository.findByEmail(email)
   │       └─→ Database: SELECT * FROM users WHERE email = ?
   ├─→ Compare: received password == database password
   └─→ If valid: Create Authentication object in SecurityContext
       If invalid: Return 401 Unauthorized

3. DISPATCHER SERVLET (Spring's request router)
   └─→ Match route to controller method
       POST /api/users/{userId}/counters → ApiController.createCounter()

4. CONTROLLER (controller/ApiController.java)
   ├─→ Validate @RequestBody with @Valid
   │   └─→ Check: counterName not blank, counterValue not null
   │       └─→ If invalid: Return 400 Bad Request
   ├─→ Security check: ensureUserMatches(123)
   │   └─→ Get authenticated user from SecurityContext
   │   └─→ Verify: authenticated userId == 123
   │       └─→ If different: Return 403 Forbidden
   ├─→ Query database: userRepository.findById(123)
   ├─→ Create Counter entity
   ├─→ Save: counterRepository.save(counter)
   │   └─→ Database: INSERT INTO counters (...)
   ├─→ Mapper: Convert Counter → CounterResponse
   │   └─→ CounterResponse.counterId = counter.counterId
   │   └─→ CounterResponse.counterName = counter.counterName
   │   └─→ CounterResponse.counterValue = counter.counterValue
   └─→ Return: ResponseEntity(201 CREATED, counterResponse)

5. RESPONSE SERIALIZER (Spring)
   └─→ Convert CounterResponse object → JSON
       {
         "counterId": 456,
         "counterName": "Push-ups",
         "counterValue": 0
       }

6. HTTP RESPONSE
   └─→ 201 Created
       Content-Type: application/json
       { ... JSON body ... }

7. FRONTEND
   └─→ Receives response, updates UI
```

---

## 🎯 Core Concepts (You'll See These Everywhere)

### 1. **Annotations = Configuration**

Instead of XML config files, Spring uses annotations. Common ones:

| Annotation | Meaning | Example |
|-----------|---------|---------|
| `@SpringBootApplication` | Main entry point | `CounterBackendApplication.java` |
| `@Configuration` | Contains @Bean methods | `SecurityConfig.java` |
| `@Bean` | Register object with Spring | `passwordEncoder()` |
| `@RestController` | Handles HTTP requests, returns JSON | `ApiController.java` |
| `@Service` | Business logic component | `CustomUserDetailsService.java` |
| `@Repository` | Data access component | `UserRepository.java` |
| `@Entity` | Database table | `User.java`, `Counter.java` |
| `@Mapper` | MapStruct code generator | `UserMapper.java` |

### 2. **Dependency Injection (@RequiredArgsConstructor)**

Spring manages object creation. No `new` keywords!

```java
@RestController
@RequiredArgsConstructor
public class ApiController {
    private final UserRepository userRepository;  // Injected by Spring
    private final CounterRepository counterRepository;  // Injected by Spring
    
    // Spring automatically finds implementations and injects them
    // @RequiredArgsConstructor generates constructor
}
```

### 3. **HTTP Methods & Status Codes**

| Method | Purpose | Example | Status |
|--------|---------|---------|--------|
| POST | Create new resource | POST /api/users | 201 Created |
| GET | Retrieve resource | GET /api/users/123 | 200 OK |
| PATCH | Partial update | PATCH /api/users/123/counters/456 | 200 OK |
| DELETE | Remove resource | DELETE /api/users/123/counters/456 | 204 No Content |

### 4. **Validation (@Valid, @NotBlank, @Email)**

Validation runs automatically when `@Valid` annotation is present:

```java
@PostMapping("/users")
public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
    // If validation fails → 400 Bad Request (automatic)
    // If valid → proceed
}
```

---

## 🔐 Security Layers (Defense in Depth)

This project has three layers of security:

### Layer 1: Authentication (Who are you?)
**File:** `security/SecurityConfig.java`, `security/CustomUserDetailsService.java`

Every request (except registration) requires HTTP Basic auth:
```
Authorization: Basic base64(email:password)
```

### Layer 2: Authorization (Are you allowed?)
**File:** `controller/ApiController.java`

After authenticating, controller checks user ownership:
```java
private User ensureUserMatches(Long userId) {
    User authenticated = getAuthenticatedUser();
    if (!authenticated.getUserId().equals(userId)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, ...);
    }
    return authenticated;
}
```

### Layer 3: Data Access (Can't access what doesn't exist)
**File:** `repository/CounterRepository.java`

All queries scoped to user:
```java
Optional<Counter> findByCounterIdAndUserId(Long counterId, Long userId)
// Never: findByCounterId(counterId)
```

**Why three layers?**
- Defense in depth: if one fails, others catch it
- Clear separation: authentication, authorization, data scoping
- Easier to understand and test

---

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

### Counters Table
```sql
CREATE TABLE counters (
    counter_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    counter_name VARCHAR(255) NOT NULL,
    counter_value BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

### Relationships
```
users (1) ←─→ (N) counters
One user can have many counters
Each counter belongs to exactly one user
```

---

## 🔄 Lifecycle Hooks (Automatic Database Tasks)

JPA provides hooks that run automatically:

```java
@Entity
public class User {
    @PrePersist
    public void onCreate() {
        // Runs BEFORE first INSERT
        this.createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    public void onUpdate() {
        // Runs BEFORE every UPDATE
        this.updatedAt = LocalDateTime.now();
    }
}
```

**Use cases:**
- Set timestamps (like here)
- Normalize data (uppercase username)
- Validate state (counter value >= 0)
- Audit logging (who changed what, when)

---

## 🎯 Common Patterns

### Pattern 1: Optional (Null-Safe)

```java
Optional<User> user = userRepository.findByEmail("atin@dev.com");

// Old way (risky):
if (user != null) { ... }

// New way (safe - what we use):
user.ifPresent(u -> println(u.getUsername()));
user.orElseThrow(() -> new Exception("Not found"));
User u = user.orElse(defaultUser);
```

### Pattern 2: Validation

```java
// In DTO:
@NotBlank(message = "Email required")
@Email(message = "Valid email required")
private String email;

// In controller:
@PostMapping("/users")
public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
    // @Valid automatically validates all fields
    // If invalid: 400 Bad Request (framework handles it)
}
```

### Pattern 3: Error Handling

```java
// Throw ResponseStatusException with HTTP status
throw new ResponseStatusException(
    HttpStatus.NOT_FOUND, 
    "Counter not found for user"
);

// Spring automatically converts to:
// HTTP 404 Not Found
// { "message": "Counter not found for user" }
```

### Pattern 4: User Scoping

```java
// ALWAYS check user ownership before returning data
private User ensureUserMatches(Long userId) {
    User authenticated = getAuthenticatedUser();
    if (!authenticated.getUserId().equals(userId)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, ...);
    }
    return authenticated;
}

// Usage in every counter endpoint:
@GetMapping("/users/{userId}/counters")
public List<CounterResponse> listCounters(@PathVariable Long userId) {
    ensureUserMatches(userId);  // Security gate
    // Now safe to access userId's data
}
```

---

## 🚀 What's NOT Here (For Future Learning)

This project is intentionally simple. For production, you'd add:

1. **Proper Password Hashing**
   - Use BCryptPasswordEncoder
   - Currently: NoOpPasswordEncoder (insecure!)

2. **JWT Tokens**
   - Better than HTTP Basic for distributed systems
   - Stateless, scalable, mobile-friendly

3. **Roles & Permissions**
   - @PreAuthorize, @PostAuthorize annotations
   - Role-based access control

4. **Transaction Management**
   - @Transactional for multi-step operations
   - Rollback on failure

5. **Caching**
   - @Cacheable annotation
   - Reduce database hits

6. **Error Handling**
   - @ControllerAdvice for centralized error handling
   - Custom exception classes

7. **Logging**
   - SLF4J for structured logging
   - Audit trails

8. **Testing**
   - Unit tests (@Test, Mockito)
   - Integration tests (@SpringBootTest)

9. **API Documentation**
   - Swagger/SpringDoc for auto-generated docs

10. **Rate Limiting**
    - Prevent brute-force attacks
    - Quota management

---

## 📖 Reading Path (Recommended Order)

1. **Start Here:** `README.md` in root package
2. **Then:** `entity/README.md` - Understand the database model
3. **Next:** `repository/README.md` - Learn how to query
4. **Then:** `dto/README.md` - API contracts & validation
5. **Then:** `mapper/README.md` - Data transformation
6. **Then:** `controller/README.md` - How endpoints work
7. **Finally:** `security/README.md` - Authentication & authorization

---

## 🎓 Key Takeaways

1. **Spring Boot is opinionated** - conventions over configuration
2. **Annotations drive behavior** - `@Entity`, `@Bean`, `@Valid`, etc.
3. **Dependency injection** - Spring manages object creation
4. **Security is layered** - authentication → authorization → data scoping
5. **DTOs separate API from internals** - entities are private
6. **Repositories are your database interface** - method naming generates SQL
7. **Optional is null-safe** - use it instead of null checks
8. **Validation is automatic** - @Valid triggers on request
9. **Exceptions become HTTP responses** - ResponseStatusException
10. **Timestamps are automatic** - @PrePersist, @PreUpdate hooks

---

## 🔍 Quick Reference

### Common Commands

```bash
# Start the application
./mvnw spring-boot:run

# Run tests
./mvnw test

# Build JAR
./mvnw clean package

# Check dependencies
./mvnw dependency:tree
```

### Common HTTP Requests

```bash
# Register
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "atin@dev.com",
    "username": "atin",
    "password": "secure123"
  }'

# Create counter (with auth)
curl -X POST http://localhost:8080/api/users/1/counters \
  -u atin@dev.com:secure123 \
  -H "Content-Type: application/json" \
  -d '{
    "counterName": "Push-ups",
    "counterValue": 0
  }'

# List counters
curl -X GET http://localhost:8080/api/users/1/counters \
  -u atin@dev.com:secure123
```

---

**You've finished the tour! Now go build something great.** 🚀
