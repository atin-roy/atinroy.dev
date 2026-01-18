# Counter Backend - START HERE 🏛️

Welcome! This is a **fully documented Spring Boot REST API** designed as a learning reference for your future projects.

You're building a museum tour guide approach. Let's start.

---

## 🎯 What This Project Teaches

This codebase covers:
- ✅ Spring Boot fundamentals
- ✅ REST API design
- ✅ JPA/Hibernate database mapping
- ✅ Authentication & Security
- ✅ Request validation
- ✅ Clean architecture patterns
- ✅ Dependency injection
- ✅ HTTP status codes & error handling

---

## 📍 Quick Navigation

### 🚀 Start With These

| Document | Focus | Time |
|----------|-------|------|
| [`ARCHITECTURE_GUIDE.md`](./ARCHITECTURE_GUIDE.md) | Big picture & complete flow | 15 min |
| [`src/main/java/.../README.md`](./src/main/java/dev/atinroy/counterBackend/README.md) | Package overview | 10 min |
| [`src/main/java/.../entity/README.md`](./src/main/java/dev/atinroy/counterBackend/entity/README.md) | Database models | 10 min |

### 📚 Deep Dives (In Order)

1. **[entity/README.md](./src/main/java/dev/atinroy/counterBackend/entity/README.md)** - Learn JPA @Entity, relationships, lifecycle hooks
2. **[repository/README.md](./src/main/java/dev/atinroy/counterBackend/repository/README.md)** - Understand Spring Data JPA query methods
3. **[dto/README.md](./src/main/java/dev/atinroy/counterBackend/dto/README.md)** - Request/response design & validation
4. **[mapper/README.md](./src/main/java/dev/atinroy/counterBackend/mapper/README.md)** - Entity ↔ DTO conversion with MapStruct
5. **[controller/README.md](./src/main/java/dev/atinroy/counterBackend/controller/README.md)** - All 8 REST endpoints explained
6. **[security/README.md](./src/main/java/dev/atinroy/counterBackend/security/README.md)** - Authentication & authorization

---

## 🔄 How Data Flows (30-Second Version)

```
Request arrives
    ↓
SecurityConfig checks who you are
    ↓
CustomUserDetailsService validates credentials
    ↓
Controller receives authenticated request
    ↓
Controller validates input (DTO with @Valid)
    ↓
Controller checks user owns the resource (authorization)
    ↓
Repository queries database
    ↓
Mapper converts Entity → DTO (never expose raw entities)
    ↓
Controller returns JSON response
```

---

## 🏗️ Project Structure

```
src/main/java/dev/atinroy/counterBackend/
│
├── 🔐 SECURITY (Authentication & Authorization)
│   ├── SecurityConfig.java        ← Security rules (who can access what)
│   └── CustomUserDetailsService.java ← Load user from database
│
├── 🌐 CONTROLLER (HTTP Endpoints)
│   └── ApiController.java         ← 8 REST endpoints
│
├── 📦 DTO (API Contracts)
│   ├── user/
│   │   ├── CreateUserRequest.java ← Registration
│   │   └── UserResponse.java      ← User data back to client
│   └── counter/
│       ├── CreateCounterRequest.java
│       ├── UpdateCounterRequest.java
│       ├── IncrementCounterRequest.java
│       └── CounterResponse.java
│
├── 🗄️  ENTITY (Database Models)
│   ├── User.java                  ← users table
│   └── Counter.java               ← counters table
│
├── 💾 REPOSITORY (Database Queries)
│   ├── UserRepository.java        ← JPA queries for users
│   └── CounterRepository.java     ← JPA queries for counters
│
└── 🔄 MAPPER (Entity ↔ DTO Conversion)
    └── UserMapper.java            ← MapStruct auto-generates mapping
```

---

## 🎓 The 8 Endpoints (REST API)

### User Management

```bash
# 1. Register (Public - no auth needed)
POST /api/users
{
  "email": "atin@dev.com",
  "username": "atin",
  "password": "secure123"
}
→ 201 Created

# 2. Get your profile
GET /api/users/{userId}
Authorization: Basic base64(email:password)
→ 200 OK { userId, username, email, createdAt }

# 3. Search users
GET /api/users?username=atin
Authorization: Basic ...
→ 200 OK [ { user1 }, { user2 } ]
```

### Counter Management

```bash
# 4. Create counter
POST /api/users/{userId}/counters
Authorization: Basic ...
{
  "counterName": "Push-ups",
  "counterValue": 0
}
→ 201 Created

# 5. List counters (with filtering)
GET /api/users/{userId}/counters
GET /api/users/{userId}/counters?name=push&minValue=10&maxValue=100
Authorization: Basic ...
→ 200 OK [ { counter1 }, { counter2 } ]

# 6. Update counter (partial)
PATCH /api/users/{userId}/counters/{counterId}
Authorization: Basic ...
{
  "counterName": "Morning Push-ups",
  "counterValue": 30
}
→ 200 OK

# 7. Increment counter
PATCH /api/users/{userId}/counters/{counterId}/increment
Authorization: Basic ...
{
  "incrementBy": 5
}
→ 200 OK

# 8. Delete counter
DELETE /api/users/{userId}/counters/{counterId}
Authorization: Basic ...
→ 204 No Content
```

---

## 🔑 Key Concepts You'll Master

### 1. **Annotations** = Configuration
Instead of XML, Spring uses annotations:
- `@Entity` = this is a database table
- `@Repository` = this accesses the database
- `@RestController` = this handles HTTP requests
- `@Valid` = validate this object's fields

### 2. **Dependency Injection**
Spring creates objects and injects them automatically:
```java
@Service
@RequiredArgsConstructor
public class MyService {
    private final UserRepository userRepository;  // Injected by Spring!
}
```

### 3. **HTTP Status Codes**
```
201 = Created (new resource)
200 = OK (success, with data)
204 = No Content (success, no data)
400 = Bad Request (validation failed)
401 = Unauthorized (not authenticated)
403 = Forbidden (authenticated but not allowed)
404 = Not Found (resource doesn't exist)
409 = Conflict (duplicate email)
```

### 4. **JPA Relationships**
```java
// One-to-Many: one user has many counters
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
private User user;
```

### 5. **Query Methods**
Spring generates SQL from method names:
```java
findByEmail(String email)
  ↓
SELECT * FROM users WHERE email = ?

findByCounterIdAndUserId(Long counterId, Long userId)
  ↓
SELECT * FROM counters WHERE counter_id = ? AND user_id = ?
```

---

## 🚀 How to Use This Project

### As a Learning Reference

1. Read `ARCHITECTURE_GUIDE.md` first
2. Follow the reading path (entity → repository → dto → mapper → controller → security)
3. Each file has detailed comments explaining WHY not just WHAT
4. Every README explains concepts, not just code

### Testing the API

```bash
# Start the application
./mvnw spring-boot:run

# Register
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "atin@dev.com",
    "username": "atin",
    "password": "secure123"
  }'

# Get your profile (with basic auth)
curl -X GET http://localhost:8080/api/users/1 \
  -u atin@dev.com:secure123

# Create a counter
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

Or use Postman:
1. Create environment variable: `password = secure123`, `email = atin@dev.com`, `userId = 1`
2. In Authorization tab: choose "Basic Auth", enter email/password
3. Postman auto-encodes and adds header

---

## 🎯 What Each Package Does

| Package | Purpose | Key Files |
|---------|---------|-----------|
| `security/` | Authentication & authorization | SecurityConfig.java |
| `controller/` | HTTP endpoints | ApiController.java |
| `dto/` | Request/response schemas | User, Counter request/response |
| `entity/` | Database tables | User.java, Counter.java |
| `repository/` | Database queries | UserRepository, CounterRepository |
| `mapper/` | Entity ↔ DTO conversion | UserMapper (MapStruct) |

---

## ⚠️ Important: What's NOT Production-Ready

This is a **learning project**, not production code. For real apps, add:

- ✅ BCryptPasswordEncoder (currently plaintext - BAD!)
- ✅ JWT tokens (currently HTTP Basic - not scalable)
- ✅ @Transactional for multi-step operations
- ✅ @ControllerAdvice for centralized error handling
- ✅ Proper logging (SLF4J)
- ✅ Comprehensive testing
- ✅ API documentation (Swagger)
- ✅ Rate limiting
- ✅ HTTPS enforcement
- ✅ Database migrations (Flyway/Liquibase)

The comments in the code point out these issues!

---

## 📖 File-by-File Comments

Every Java class has detailed comments explaining:
- What it does (purpose)
- Why we chose this approach
- How it connects to other parts
- Common gotchas

Examples of documented concepts:
- `User.java` → Why email is unique, why we need @PrePersist
- `Counter.java` → Lazy loading, why we need duplicate userId field
- `UserRepository.java` → Query naming convention, Optional usage
- `CreateUserRequest.java` → Validation annotations explained
- `ApiController.java` → All 8 endpoints with request/response examples
- `SecurityConfig.java` → Why we disable CSRF, why stateless matters
- `CustomUserDetailsService.java` → How authentication loads user from database

---

## 🎓 Learning Outcomes

After studying this codebase, you'll understand:

✅ How Spring Boot boots up an application
✅ How dependency injection works
✅ How REST APIs are structured
✅ How to map database tables with JPA
✅ How to query databases with JpaRepository
✅ How to validate request data
✅ How to convert between entities and DTOs
✅ How HTTP authentication works
✅ How to design secure APIs
✅ How to handle errors properly

---

## 🔍 Quick Reference: Where to Find Things

**"How do I create a database table?"**
→ `entity/User.java`, `entity/README.md`

**"How do I query the database?"**
→ `repository/UserRepository.java`, `repository/README.md`

**"How do I validate user input?"**
→ `dto/user/CreateUserRequest.java`, `dto/README.md`

**"How do I convert Entity to DTO?"**
→ `mapper/UserMapper.java`, `mapper/README.md`

**"How do I create an HTTP endpoint?"**
→ `controller/ApiController.java`, `controller/README.md`

**"How does authentication work?"**
→ `security/SecurityConfig.java`, `security/README.md`

**"How does a complete request flow work?"**
→ `ARCHITECTURE_GUIDE.md`

---

## 🚀 Next Steps

1. **Read `ARCHITECTURE_GUIDE.md`** (15 min)
2. **Read each package's README in order** (1 hour)
3. **Read the class files with comments** (30 min)
4. **Run the application and test endpoints** (15 min)
5. **Build your own Spring Boot project** using this as reference

---

## 💡 Pro Tips

- **Bookmark the READMEs** - you'll reference them constantly
- **Copy patterns, not code** - understand the "why" before copy-pasting
- **Comments explain architecture** - read them, they're intentional
- **Security checks are everywhere** - notice how every endpoint verifies user ownership
- **DTOs are your boundary** - entities never leave the controller

---

**Ready? Start with `ARCHITECTURE_GUIDE.md`. The entire tour is self-guided.** 🏛️

Good luck building! 🚀
