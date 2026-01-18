# Counter Backend - Study Guide

This guide helps you understand the project through common questions and scenarios.

---

## 🎯 5-Minute Quick Understanding

**What does this project do?**
A REST API for managing personal counters (e.g., push-ups done, books read). Users register, authenticate, and manage their own counters.

**What are the main layers?**
```
Security (who are you?) 
    ↓
Controller (what do you want?)
    ↓
Business Logic (are you allowed?)
    ↓
Repository (get data from database)
    ↓
Database (JPA manages this)
```

**Why are there DTOs?**
Entities are internal (database models). DTOs are the API contract. You never expose raw entities to the frontend.

**Why is security checked three times?**
Defense in depth. Authentication + Authorization + Data Scoping means even bugs don't leak data.

---

## 🔍 Common Questions & Answers

### Q: Why separate Entity and DTO?

**The Problem:**
```java
// ❌ If you expose Entity directly
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    return userRepository.save(user);
}

// Problems:
// 1. Password is returned in response (security leak!)
// 2. Can't control which fields are accepted (userId, createdAt shouldn't be input)
// 3. API breaks if you rename entity fields
// 4. Frontend can't validate properly (no @Email on entity)
```

**The Solution:**
```java
// ✅ Use DTOs
@PostMapping("/users")
public UserResponse createUser(@RequestBody CreateUserRequest request) {
    // request has @Email, @NotBlank validations
    // response excludes password
    // entity fields are private
}
```

**Key insight:** DTOs are contracts. Entities are implementation.

---

### Q: Why @ManyToOne with LAZY?

**What's @ManyToOne?**
Many counters belong to one user. It's a foreign key relationship.

**What's LAZY?**
```java
@ManyToOne(fetch = FetchType.LAZY)
private User user;

Counter counter = counterRepository.findById(1);
User u = counter.getUser();  // ← Database query happens HERE
```

vs EAGER:
```java
@ManyToOne(fetch = FetchType.EAGER)
private User user;

Counter counter = counterRepository.findById(1);  // ← Loads User too
User u = counter.getUser();  // ← No database query
```

**When to use:**
- **LAZY:** Default, load when needed, performance optimized
- **EAGER:** Load everything, simpler code but slower queries

**In this project:** We use LAZY, but also have a direct `userId` field for when you only need the ID:
```java
long id = counter.getUserId();  // No query!
User user = counter.getUser();  // Lazy load query
```

---

### Q: How does Spring Data JPA generate SQL?

**Method name parsing:**
```
find     By     Email     Containing
↓        ↓      ↓         ↓
PREFIX   VERB   COLUMN    OPERATOR

Generates: SELECT * FROM users WHERE email LIKE %?%
```

**Common operators:**
- `find...By...` = WHERE (equality)
- `find...By...Containing` = WHERE LIKE %?%
- `find...By...GreaterThan` = WHERE > ?
- `find...By...LessThanEqual` = WHERE <= ?
- `find...By...And...` = WHERE ... AND ...
- `find...By...Or...` = WHERE ... OR ...

**Examples:**
```java
findByEmail(String email)
  → SELECT * FROM users WHERE email = ?

findByCounterIdAndUserId(Long id, Long userId)
  → SELECT * FROM counters WHERE counter_id = ? AND user_id = ?

findByCounterNameContaining(String name)
  → SELECT * FROM counters WHERE counter_name LIKE %?%

findByCounterValueGreaterThanEqual(Long value)
  → SELECT * FROM counters WHERE counter_value >= ?
```

**Key insight:** Method name = SQL query. No string SQL to maintain!

---

### Q: Why Optional instead of null?

**Old way (risky):**
```java
User user = userRepository.findByEmail("atin@dev.com");
if (user != null) {  // ← Easy to forget!
    println(user.getUsername());
}
// What if you forget? NullPointerException! 💥
```

**New way (safe):**
```java
Optional<User> user = userRepository.findByEmail("atin@dev.com");

// Forced to handle "not found" case
user.ifPresent(u -> println(u.getUsername()));

// Or throw exception if not found
User u = user.orElseThrow(() -> new Exception("Not found"));

// Or provide default
User u = user.orElse(defaultUser);
```

**Key insight:** Optional forces you to think about "what if not found?"

---

### Q: How does @Valid validation work?

**In DTO:**
```java
@Data
public class CreateUserRequest {
    @NotBlank(message = "Email required")
    @Email(message = "Valid email required")
    private String email;
    
    @Size(min = 8, message = "Password 8+ chars")
    private String password;
}
```

**In Controller:**
```java
@PostMapping("/users")
public UserResponse createUser(
    @Valid @RequestBody CreateUserRequest request
) {
    // If validation fails → 400 Bad Request (Spring handles this!)
    // If valid → request object is 100% validated
}
```

**What happens if validation fails:**
```
Request:
{
  "email": "not-an-email",
  "password": "short"
}

Response (400 Bad Request):
{
  "timestamp": "2025-01-18T...",
  "status": 400,
  "error": "Bad Request",
  "errors": [
    {"field": "email", "message": "Valid email required"},
    {"field": "password", "message": "Password 8+ chars"}
  ]
}
```

**Key insight:** @Valid is compile-safe validation. Catch errors early.

---

### Q: Why check user ownership in controller?

**Bad (data leak):**
```java
@GetMapping("/users/{userId}/counters")
public List<CounterResponse> listCounters(@PathVariable Long userId) {
    return counterRepository.findByUserId(userId);
    // User 1 can GET /users/2/counters and steal data! 😱
}
```

**Good (secure):**
```java
@GetMapping("/users/{userId}/counters")
public List<CounterResponse> listCounters(@PathVariable Long userId) {
    ensureUserMatches(userId);  // ← Verify ownership
    return counterRepository.findByUserId(userId);
}

private User ensureUserMatches(Long userId) {
    User authenticated = getAuthenticatedUser();
    if (!authenticated.getUserId().equals(userId)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "...");
    }
    return authenticated;
}
```

**Key insight:** Always verify user owns the resource before accessing it.

---

### Q: Why use MapStruct for mapping?

**Manual mapping (❌ error-prone):**
```java
public UserResponse toUserResponse(User user) {
    UserResponse response = new UserResponse();
    response.setUserId(user.getUserId());
    response.setUsername(user.getUsername());
    response.setEmail(user.getEmail());
    response.setCreatedAt(user.getCreatedAt());
    response.setPassword(user.getPassword());  // ⚠️ OOPS! Leaked password!
    return response;
}
```

**MapStruct (✅ safe):**
```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
    // MapStruct generates code that:
    // 1. Only maps fields that exist in BOTH entity and DTO
    // 2. password field doesn't exist in UserResponse
    // 3. Therefore: password is never copied
    // 4. Type-safe: impossible to leak password
}
```

**Key insight:** Code generation prevents mistakes. MapStruct generates at compile-time.

---

### Q: Why HTTP Basic instead of JWT?

**HTTP Basic:**
```
Authorization: Basic base64(email:password)

Pros:
✓ Simple to understand
✓ Works everywhere
✓ No token expiration

Cons:
✗ Credentials sent with every request
✗ Can't revoke without changing password
✗ Doesn't scale to microservices
```

**JWT:**
```
POST /login → returns token
Authorization: Bearer eyJhbGci...

Pros:
✓ Token only sent on login
✓ Can set expiration
✓ Scales to microservices
✓ Mobile-friendly

Cons:
✗ More complex
✗ Can't revoke (use blacklist or expiration)
```

**In this project:** HTTP Basic is fine for learning. Production uses JWT.

**Key insight:** Choose authentication based on your use case. HTTP Basic is simple, JWT is scalable.

---

### Q: What does @PrePersist do?

**Answer:** Runs automatically before INSERT.

```java
@Entity
public class User {
    private LocalDateTime createdAt;
    
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

// When you do:
userRepository.save(new User(...));

// Spring automatically calls onCreate() before INSERT
// createdAt is set to current time
// No need to set it manually in controller!
```

**Lifecycle hooks:**
- `@PrePersist` → before INSERT
- `@PostPersist` → after INSERT
- `@PreUpdate` → before UPDATE
- `@PostUpdate` → after UPDATE
- `@PreRemove` → before DELETE
- `@PostRemove` → after DELETE

**Key insight:** Automate repetitive tasks (timestamps) with lifecycle hooks.

---

## 🧩 Architecture Patterns

### Pattern 1: Entity → DTO Conversion

```
Database row
  ↓ (JPA deserializes to)
User entity (with password field)
  ↓ (MapStruct converts to)
UserResponse DTO (no password)
  ↓ (Spring serializes to)
JSON response (sent to frontend)
```

**Why?** Entities are internal, DTOs are external. Never expose internals.

---

### Pattern 2: Security Gates

```
Request arrives
  ↓
SecurityFilterChain: Are you authenticated? (401 if no)
  ↓
Controller: Are you the owner? (403 if no)
  ↓
Repository: Query scoped to user (data-level security)
```

**Why?** Defense in depth. Multiple layers catch bugs.

---

### Pattern 3: Validation Layers

```
Frontend: Quick client-side validation (UX feedback)
  ↓
Controller @Valid: Spring validation (catches malformed data)
  ↓
Business logic: Domain rules (are they allowed to do this?)
```

**Why?** Catch errors as early as possible.

---

## 🔍 Debugging Tips

### How to find a bug in the request flow?

1. **Check logs** - Look for exception stack trace
2. **Identify the layer:**
   - Security error? → `security/`
   - Validation error? → `dto/`
   - Authorization error? → `controller/` ensureUserMatches()
   - Database error? → `repository/`
   - Mapping error? → `mapper/`
3. **Add print statements** in that layer
4. **Use browser dev tools** to inspect request headers
5. **Use Postman** to test endpoints manually

### Common errors & solutions:

| Error | Cause | Solution |
|-------|-------|----------|
| 401 Unauthorized | Bad password or missing header | Check auth credentials |
| 403 Forbidden | User doesn't own resource | Verify userId matches authenticated user |
| 400 Bad Request | Validation failed | Check DTO fields match @NotBlank/@Email |
| 404 Not Found | Resource doesn't exist | Verify counter/user IDs are correct |
| 409 Conflict | Email already registered | Use different email |

---

## 📚 Study Checklist

- [ ] Read `README_START_HERE.md`
- [ ] Read `ARCHITECTURE_GUIDE.md`
- [ ] Read `entity/README.md` + examine `User.java`, `Counter.java`
- [ ] Read `repository/README.md` + examine query methods
- [ ] Read `dto/README.md` + examine validation annotations
- [ ] Read `mapper/README.md` + understand MapStruct
- [ ] Read `controller/README.md` + trace all 8 endpoints
- [ ] Read `security/README.md` + understand auth flow
- [ ] Run application: `./mvnw spring-boot:run`
- [ ] Test with curl/Postman (register → create counter → list → update → delete)
- [ ] Find and comment on one security gap (for learning)
- [ ] Design your own Spring Boot project on paper

---

## 🎓 Knowledge Verification

**Can you answer these?**

1. Why do we use DTOs instead of exposing entities directly?
2. What's the difference between @ManyToOne and @OneToMany?
3. How does Spring Data JPA generate SQL from method names?
4. Why use Optional instead of null checks?
5. What does @Valid do, and when is it triggered?
6. Why check user ownership in the controller?
7. How does MapStruct prevent password leaks?
8. What are the three security layers in this project?
9. What does @PrePersist do, and when does it run?
10. How does HTTP Basic authentication work?

**If you can explain all of these, you understand the architecture!**

---

## 🚀 Next Learning Steps

After mastering this project:

1. **Add Features:**
   - Roles & permissions (@PreAuthorize)
   - Counter history/audit log
   - Counter statistics (average, min, max)

2. **Security Hardening:**
   - Replace NoOpPasswordEncoder with BCryptPasswordEncoder
   - Add JWT tokens
   - Add rate limiting
   - Add HTTPS/TLS

3. **Performance:**
   - Add caching (@Cacheable)
   - Database indexing
   - Pagination for large lists

4. **Operations:**
   - Comprehensive logging
   - Error tracking (Sentry)
   - Metrics (Micrometer)
   - API documentation (Swagger)

5. **Testing:**
   - Unit tests with Mockito
   - Integration tests with @SpringBootTest
   - End-to-end tests with RestAssured

---

## 💡 Remember

This project is a **learning museum**, not production code. As you read each file, think about:

- **Why** this design was chosen
- **What** would break if we changed it
- **How** it connects to other parts
- **When** you'd use this in your projects

Good luck! 🎓
