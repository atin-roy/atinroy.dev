# Controller Package - HTTP Endpoint Handler

## 🌐 What's Here?

Controllers handle HTTP requests and orchestrate the response. They're the **conductor of your REST API**—asking repositories for data, applying business logic, and returning responses.

**Key Principle: Keep controllers thin.** Repository queries → validation → transform → respond.

---

## 📚 The Controller in This Museum

### `ApiController.java`

**Annotations:**
```java
@RestController           // "I handle HTTP and return JSON"
@RequestMapping("/api")   // All routes here start with /api
@Validated               // Enable @Valid validation
@RequiredArgsConstructor // Lombok: inject these 3 via constructor
```

**Injected Dependencies:**
```java
private final UserRepository userRepository;      // Query users
private final CounterRepository counterRepository;  // Query counters
private final UserMapper userMapper;               // DTO conversion
```

---

## 🎯 Endpoint Breakdown

### 1️⃣ Create User (Register)

```java
@PostMapping("/users")
public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request)
```

**Request:**
```json
POST /api/users
Content-Type: application/json

{
  "email": "atin@dev.com",
  "username": "atin",
  "password": "secure123"
}
```

**Logic:**
```
1. Validate request (@Valid checks CreateUserRequest annotations)
2. Normalize email (trim + lowercase)
3. Check if email already registered (prevent duplicates)
4. Create User entity
5. Save to database
6. Convert to UserResponse (excludes password)
7. Return 201 CREATED with response body
```

**Response:**
```json
201 Created
Content-Type: application/json

{
  "userId": 1,
  "username": "atin",
  "email": "atin@dev.com",
  "createdAt": "2025-01-18T10:30:00"
}
```

**Error Cases:**
```json
409 Conflict
{"timestamp": "...", "status": 409, "error": "Email already registered"}

400 Bad Request
{"message": "Validation failed for: password must be at least 8 characters"}
```

---

### 2️⃣ Get User Profile

```java
@GetMapping("/users/{userId}")
public UserResponse getUser(@PathVariable Long userId)
```

**Request:**
```
GET /api/users/123
Authorization: Basic base64(email:password)
```

**Logic:**
```
1. Extract userId from URL path
2. Get current authenticated user
3. Verify user owns this ID (ensureUserMatches)
4. Fetch user from database
5. Convert to DTO
6. Return 200 OK
```

**Response:**
```json
200 OK

{
  "userId": 123,
  "username": "atin",
  "email": "atin@dev.com",
  "createdAt": "2025-01-18T..."
}
```

**Error Cases:**
```json
403 Forbidden
"Users can only access their own counters"

401 Unauthorized
"Authentication required"
```

---

### 3️⃣ Search Users

```java
@GetMapping("/users")
public List<UserResponse> searchUsers(@RequestParam(required = false) String username)
```

**Requests:**
```
GET /api/users                           → All users
GET /api/users?username=atin            → Search by partial username
GET /api/users?username=john%20doe      → Multi-word search
```

**Logic:**
```
1. Check if username query param provided
2. If yes → find users with username containing search term
3. If no → fetch all users
4. Convert all to DTOs
5. Return as List
```

**Response:**
```json
200 OK

[
  {
    "userId": 1,
    "username": "atin",
    "email": "atin@dev.com",
    "createdAt": "2025-01-18T..."
  },
  {
    "userId": 2,
    "username": "atinroy",
    "email": "atinroy@dev.com",
    "createdAt": "2025-01-18T..."
  }
]
```

---

### 4️⃣ Create Counter

```java
@PostMapping("/users/{userId}/counters")
public ResponseEntity<CounterResponse> createCounter(
    @PathVariable Long userId,
    @Valid @RequestBody CreateCounterRequest request
)
```

**Request:**
```json
POST /api/users/123/counters
Authorization: Basic ...

{
  "counterName": "Push-ups",
  "counterValue": 0
}
```

**Logic:**
```
1. Validate request
2. Verify user owns this userId (security gate)
3. Fetch User from database
4. Create Counter entity
5. Associate with user
6. Save to database
7. Convert to response DTO
8. Return 201 CREATED
```

**Response:**
```json
201 Created

{
  "counterId": 456,
  "counterName": "Push-ups",
  "counterValue": 0
}
```

---

### 5️⃣ List Counters (with Filtering)

```java
@GetMapping("/users/{userId}/counters")
public List<CounterResponse> listCounters(
    @PathVariable Long userId,
    @RequestParam(required = false) String name,
    @RequestParam(required = false) Long minValue,
    @RequestParam(required = false) Long maxValue
)
```

**Requests:**
```
GET /api/users/123/counters
  → All counters for user 123

GET /api/users/123/counters?name=push
  → Filter by name containing "push" (case-insensitive)

GET /api/users/123/counters?minValue=10&maxValue=50
  → Filter by value range

GET /api/users/123/counters?name=workout&minValue=5
  → Combine filters
```

**Logic:**
```
1. Verify user owns this ID
2. Fetch all counters for user from database
3. Apply name filter (case-insensitive "contains")
4. Apply minValue filter (>= condition)
5. Apply maxValue filter (<= condition)
6. Convert all to DTOs
7. Return as List
```

**Key Pattern:** All filtering done in **memory**, not SQL. Why?
- Small datasets (a user has ~10-100 counters)
- More flexible filtering logic
- Simpler code
- In production: move to SQL for billions of rows

**Response:**
```json
200 OK

[
  {"counterId": 456, "counterName": "Push-ups", "counterValue": 25},
  {"counterId": 789, "counterName": "Workouts", "counterValue": 10}
]
```

---

### 6️⃣ Update Counter (PATCH)

```java
@PatchMapping("/users/{userId}/counters/{counterId}")
public CounterResponse updateCounter(
    @PathVariable Long userId,
    @PathVariable Long counterId,
    @Valid @RequestBody UpdateCounterRequest request
)
```

**Request:**
```json
PATCH /api/users/123/counters/456
Authorization: Basic ...

{
  "counterName": "Morning Push-ups",
  "counterValue": 30
}
```

**Logic:**
```
1. Verify user owns this ID
2. Find counter (checking it belongs to user)
3. If counterName provided → update it
4. If counterValue provided → update it
5. If nothing changed → reject (BAD_REQUEST)
6. Save to database
7. Convert to response
8. Return 200 OK
```

**Pattern: PATCH vs PUT**
- **PATCH** (partial) = update only fields sent
- **PUT** (full) = replace entire resource

Here we use PATCH because both fields are optional.

**Response:**
```json
200 OK

{
  "counterId": 456,
  "counterName": "Morning Push-ups",
  "counterValue": 30
}
```

**Error Cases:**
```json
400 Bad Request
"Provide a name or value to change"

404 Not Found
"Counter not found for user"
```

---

### 7️⃣ Increment Counter (Special Operation)

```java
@PatchMapping("/users/{userId}/counters/{counterId}/increment")
public CounterResponse incrementCounter(
    @PathVariable Long userId,
    @PathVariable Long counterId,
    @Valid @RequestBody IncrementCounterRequest request
)
```

**Request:**
```json
PATCH /api/users/123/counters/456/increment
Authorization: Basic ...

{
  "incrementBy": 5
}
```

**Logic:**
```
1. Verify user owns ID
2. Find counter
3. Add incrementBy to current value (can be negative!)
4. Save
5. Return updated counter
```

**Why separate endpoint?**
- Explicit intent: "I'm incrementing, not replacing"
- Atomic operation: no race condition issues
- Clear API design

**Response:**
```json
200 OK

{
  "counterId": 456,
  "counterName": "Morning Push-ups",
  "counterValue": 35  // 30 + 5
}
```

---

### 8️⃣ Delete Counter

```java
@DeleteMapping("/users/{userId}/counters/{counterId}")
public ResponseEntity<Void> deleteCounter(
    @PathVariable Long userId,
    @PathVariable Long counterId
)
```

**Request:**
```
DELETE /api/users/123/counters/456
Authorization: Basic ...
```

**Logic:**
```
1. Verify user owns ID
2. Find counter
3. Delete from database
4. Return 204 No Content
```

**Response:**
```
204 No Content
(empty body)
```

No need to return the deleted resource—the fact that we returned 204 means it worked.

---

## 🔐 Security Patterns You'll See

### Pattern 1: Authentication Check

```java
private User getAuthenticatedUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated() 
        || auth instanceof AnonymousAuthenticationToken) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "...");
    }
    // Extract user from auth
}
```

**Every endpoint requires:**
1. HTTP Basic Auth header → `Authorization: Basic base64(email:password)`
2. User must exist in database
3. SecurityConfig checks this automatically (except `/api/users` POST)

### Pattern 2: Authorization Check (Ownership)

```java
private User ensureUserMatches(Long userId) {
    User authenticated = getAuthenticatedUser();
    if (!authenticated.getUserId().equals(userId)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "...");
    }
    return authenticated;
}
```

**Always called before accessing counters:**
- User 1 can't access User 2's counters
- No accidental data leaks
- User 2 tries to GET /api/users/1/counters → 403 Forbidden

### Pattern 3: Data Normalization

```java
private String normalize(String input) {
    if (!StringUtils.hasText(input)) return null;
    return input.trim().toLowerCase(Locale.ROOT);
}
```

**Why?**
- Email: `" ATIN@DEV.COM "` → `"atin@dev.com"` (case-insensitive, no spaces)
- Search: `" PuSh "` → `"push"` (consistent filtering)
- Locale.ROOT: ignore regional settings (Turkish "i" issue)

---

## 📊 HTTP Status Codes Used

| Code | Meaning | When Used |
|------|---------|-----------|
| 200 | OK | GET, PATCH successful |
| 201 | Created | POST successful (new resource) |
| 204 | No Content | DELETE successful |
| 400 | Bad Request | Validation failed, bad logic |
| 401 | Unauthorized | No auth header or invalid |
| 403 | Forbidden | Auth OK, but not allowed |
| 404 | Not Found | Resource doesn't exist |
| 409 | Conflict | Email already registered |

---

## 🎯 Key Takeaways

1. **@RestController** returns JSON, not HTML
2. **@PathVariable** extracts from URL (`{userId}`)
3. **@RequestParam** extracts from query string (`?name=push`)
4. **@Valid** triggers DTO validation automatically
5. **ResponseEntity** = full control over status code + body
6. **Security first** = verify user owns resource before returning
7. **Normalize data** = consistent searches and lookups
8. **Meaningful status codes** = 201 for created, 204 for deleted, 409 for conflict

---

## 📖 Architecture Complete!

You've now seen the full Spring Boot request → response cycle:

```
Request → SecurityConfig (auth) → Controller (orchestration) 
       → Repository (database) → Entity (JPA) 
       → Mapper (DTO conversion) → Response
```

Next: **Security** package to understand how authentication actually works.
