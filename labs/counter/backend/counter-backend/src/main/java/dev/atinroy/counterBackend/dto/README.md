# DTO Package - API Contracts

## 🎭 What's Here?

DTOs (Data Transfer Objects) are **simple, request/response objects** that define your API contracts. They're what your frontend sees—not your internal entities.

**Key Principle: Never expose Entities directly in API responses.**

## Why Split DTO from Entity?

### Imagine This:
```java
// ❌ BAD: Exposing Entity directly
@PostMapping("/api/users")
public User createUser(@RequestBody User user) {
    return userRepository.save(user);
}

// Problems:
// - Frontend gets user.password (security leak!)
// - If you rename entity field, API breaks
// - Entity has DB columns user shouldn't control (createdAt)
```

```java
// ✅ GOOD: Using DTOs
@PostMapping("/api/users")
public UserResponse createUser(@RequestBody CreateUserRequest request) {
    // ... business logic ...
    return mapper.toUserResponse(saved);
}

// Benefits:
// - Request schema ≠ Response schema
// - Password never exposed
// - Frontend-facing API is stable even if entity changes
// - Validation rules live in DTOs
```

---

## 📚 DTOs in This Museum

### Request DTOs (Input)

#### `user/CreateUserRequest.java`
What the frontend sends when registering:
```json
POST /api/users
{
  "email": "atin@dev.com",
  "username": "atin",
  "password": "secure123"
}
```

**Validation Rules:**
- `@NotBlank` = can't be empty or just spaces
- `@Size(min=8, ...)` = password must be 8+ chars
- `@Email` = must be valid email format

Jakarta Bean Validation handles this automatically. If validation fails, Spring returns **400 Bad Request** with error details.

#### `counter/CreateCounterRequest.java`
```json
POST /api/users/123/counters
{
  "counterName": "Push-ups",
  "counterValue": 50
}
```

#### `counter/UpdateCounterRequest.java`
Partial update (all fields optional):
```json
PATCH /api/users/123/counters/456
{
  "counterName": "Morning Pushups",
  "counterValue": 75
}
```

**Design Note:** Both `counterName` and `counterValue` are optional. If you send `{}`, the controller will reject with "Provide a name or value to change".

#### `counter/IncrementCounterRequest.java`
```json
PATCH /api/users/123/counters/456/increment
{
  "incrementBy": 5
}
```

Can be negative for decrementing!

---

### Response DTOs (Output)

#### `user/UserResponse.java`
What the frontend receives:
```json
{
  "userId": 123,
  "username": "atin",
  "email": "atin@dev.com",
  "createdAt": "2025-01-18T10:30:00"
}
```

**Notice:** No `password` field! Security first.

#### `counter/CounterResponse.java`
```json
{
  "counterId": 456,
  "counterName": "Push-ups",
  "counterValue": 50
}
```

**Design Decision:** Response includes `counterId`, but the frontend comment says "Counter IDs are returned but the frontend intentionally ignores them."

Why? The frontend tracks counters by name and value, not ID. IDs are internal implementation details.

---

## 🏗️ Request → Response Flow

```
Frontend sends CreateCounterRequest JSON
         ↓
Spring @Valid annotation validates it
         ↓
If valid → Controller receives as Java object
If invalid → 400 Bad Request with error messages
         ↓
Controller calls repository, gets Counter entity
         ↓
Controller converts Counter → CounterResponse DTO
         ↓
Spring serializes CounterResponse to JSON
         ↓
Frontend receives response
```

---

## 🎯 Validation Deep Dive

### Where Validation Happens

```java
@PostMapping("/users")
public ResponseEntity<UserResponse> createUser(
    @Valid @RequestBody CreateUserRequest request  // ← @Valid triggers validation
) {
    // If we reach here, request is 100% valid
    // ...
}
```

### Standard Annotations

| Annotation | Meaning | Example |
|------------|---------|---------|
| `@NotBlank` | Can't be null/empty/whitespace | username |
| `@NotNull` | Can't be null (but can be empty) | counterValue |
| `@Email` | Must be valid email | email |
| `@Size(min, max)` | String length | password |
| `@Positive` | Must be > 0 | unused here |
| `@Pattern` | Regex match | unused here |

### Validation Error Response

```
POST /api/users
{
  "email": "invalid-email",
  "username": "ab",  // Too short
  "password": "short"  // Too short
}

Response: 400 Bad Request
{
  "timestamp": "2025-01-18...",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for ...",
  "errors": [
    {"field": "email", "message": "Email must be valid"},
    {"field": "username", "message": "Username must be between 3 and 50 characters"},
    {"field": "password", "message": "Password must be at least 8 characters"}
  ]
}
```

---

## 🎲 Folder Organization

```
dto/
├── counter/         → Everything counter-related
│   ├── CreateCounterRequest.java
│   ├── UpdateCounterRequest.java
│   ├── IncrementCounterRequest.java
│   └── CounterResponse.java
└── user/           → Everything user-related
    ├── CreateUserRequest.java
    └── UserResponse.java
```

**Pattern:** Organize by **domain** (counter, user), not by purpose (request, response).

---

## 🔑 Key Takeaways

1. **DTOs are contracts** - your frontend and backend agree on this shape
2. **Validation lives here** - @Valid, @NotBlank, @Email, etc.
3. **Never leak secrets** - no password in responses
4. **Optional fields in requests** - use UpdateCounterRequest pattern for PATCH operations
5. **Map entities to DTOs** - done by `mapper/` package (next stop)

---

## 📖 Next Steps

See how DTOs are converted to Entities using **Mappers** in `mapper/README.md`.
