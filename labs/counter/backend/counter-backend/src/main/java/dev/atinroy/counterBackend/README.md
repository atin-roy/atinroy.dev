# Counter Backend - Spring Boot Architecture Guide

Welcome to the **Counter Backend Museum**! This is a reference implementation of a modern Spring Boot REST API with authentication, data persistence, and clean architectural patterns.

## 📍 You Are Here: Root Package (`dev.atinroy.counterBackend`)

This is the application's **entry point and organizational root**. Think of it as the main entrance to the museum.

### What Lives Here
- **`CounterBackendApplication.java`** - The @SpringBootApplication entry point. Spring discovers all components in this package and sub-packages automatically.

### Quick Museum Map

```
counterBackend/
├── 🚪 controller/          → HTTP layer (REST endpoints)
├── 📦 dto/                 → Data Transfer Objects (API contracts)
├── 🗄️  entity/             → JPA entities (database models)
├── 🔄 mapper/              → DTO ↔ Entity conversion
├── 💾 repository/          → Database access layer
└── 🔐 security/            → Authentication & authorization
```

## 🏗️ The Full Picture: Spring Boot Request Flow

Understanding how a request flows through Spring Boot is crucial:

```
HTTP Request
    ↓
SecurityFilterChain (authentication)
    ↓
DispatcherServlet (Spring's request router)
    ↓
Controller (@RestController)
    ↓
Repository (talks to database)
    ↓
Controller transforms & returns Response
    ↓
HTTP Response
```

## Key Concepts You'll See Here

### 1. **Dependency Injection (@Autowired, @RequiredArgsConstructor)**
Spring manages object creation and injection. No `new` keywords!

### 2. **Annotations = Configuration**
Instead of XML config files, Spring uses annotations like:
- `@RestController` - This is an HTTP endpoint handler
- `@Entity` - This is a database table
- `@Repository` - This is a data access object
- `@Bean` - Register something with Spring's container

### 3. **Stateless REST Design**
Each request is independent. No sessions stored on server (SessionCreationPolicy.STATELESS).

### 4. **HTTP Basic Auth**
The simplest auth method - credentials sent in every request header.

## 🔍 A Real Request Journey

**User creates a counter:**

```
1. Frontend sends:    POST /api/users/123/counters
                      Body: { "counterName": "Goals", "counterValue": 0 }
                      Auth: Basic base64(email:password)

2. SecurityConfig    → Checks if POST /api/users is allowed
                     → Validates auth header, calls CustomUserDetailsService

3. CustomUserDetailsService → Looks up user by email in database

4. ApiController     → Receives request, calls ensureUserMatches(123)
                     → Creates Counter entity
                     → Saves to database via CounterRepository

5. CounterRepository → Executes SQL INSERT via JPA/Hibernate

6. ApiController     → Converts Counter entity to CounterResponse DTO
                     → Returns 201 CREATED with JSON body

7. Frontend receives the response
```

## Learning Path

Navigate to each package's README to understand specific concepts:

1. **Start:** `entity/` - Learn about JPA, @Entity, relationships
2. **Then:** `repository/` - Understand JpaRepository query methods
3. **Next:** `controller/` - See how endpoints are built
4. **Also:** `security/` - How authentication works
5. **Polish:** `dto/`, `mapper/` - Clean API contracts

---

**This codebase is intentionally simple so you can see all the pieces clearly. Master these fundamentals, then scale to microservices, caching, etc.**
