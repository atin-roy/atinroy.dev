# Security Package - Authentication & Authorization

## 🔐 What's Here?

This package handles **who you are** (authentication) and **what you can do** (authorization). It's the bouncer at the nightclub.

**Key Principle: Security is not optional. It's the first thing that runs.**

---

## 🎯 The Security Flow

```
HTTP Request arrives
       ↓
SecurityFilterChain intercepts it
       ↓
Is this /api/users POST? → Allow (no auth needed for registration)
       ↓
Does request have Authorization header? → Check
       ↓
CustomUserDetailsService looks up user by email
       ↓
Spring compares password hash
       ↓
If valid → set SecurityContext (user is now "authenticated")
       ↓
If invalid → 401 Unauthorized
       ↓
Controller receives request with auth info
       ↓
Controller calls ensureUserMatches() → 403 Forbidden if doesn't match
```

---

## 📚 Components

### `SecurityConfig.java` - The Bouncer's Rules

```java
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
```

**What's @Configuration + @Bean?**

`@Bean` methods return Spring-managed objects. `@Configuration` marks a class as containing beans.

```java
http.csrf(AbstractHttpConfigurer::disable)
```

**CSRF (Cross-Site Request Forgery) Protection:**
- Normally: browser sends CSRF token with POST requests
- Why disabled here: API is **stateless** (no sessions), clients are trusted
- For browser-based apps: enable CSRF

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
    .anyRequest().authenticated())
```

**Authorization Rules:**
- `POST /api/users` (registration) → **anyone**, no auth needed
- Everything else (`anyRequest`) → **authenticated** required

**In English:** "I allow public user registration, but everything else needs login."

```java
.httpBasic(Customizer.withDefaults())
```

**HTTP Basic Authentication:**
```
Authorization: Basic base64(email:password)

Example:
Authorization: Basic YXRpbkBkZXYuY29tOnBhc3N3b3JkMTIz
             ↑
          Decoded: atin@dev.com:password123
```

Spring automatically parses this header and loads the user.

```java
.userDetailsService(userDetailsService)
```

**Tell Spring:** Use our custom `CustomUserDetailsService` to load user details.

```java
.sessionManagement(session -> 
    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
```

**Stateless Design:**
- No sessions stored on server
- Each request includes credentials
- Scalable: any server can handle any request
- REST principle: each request is independent

**Why stateless?**
```
Stateful (❌ doesn't scale):
  User logs in → server stores session in memory
  Request 1 → goes to Server A (session found)
  Request 2 → goes to Server B (session not found!)
  
Stateless (✅ scales perfectly):
  User logs in
  Request 1 → goes to Server A (validates auth header)
  Request 2 → goes to Server B (validates same auth header)
```

```java
.formLogin(AbstractHttpConfigurer::disable)
```

**Disable form login:** We're building an API, not a web form. No redirect to login page.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
}
```

⚠️ **WARNING: INSECURE! For learning only!**

This stores passwords **in plain text**. 

**Production:** Use BCrypt:
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

Then when saving user:
```java
user.setPassword(passwordEncoder.encode(request.getPassword()));
```

And Spring handles comparison automatically.

---

### `CustomUserDetailsService.java` - The ID Checker

```java
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
```

**What's UserDetailsService?**

Spring's interface for loading users. When HTTP Basic auth header arrives, Spring calls:

```
"Load user for email: atin@dev.com"
   ↓
Our method is called with username="atin@dev.com"
   ↓
We query database for user
   ↓
Return UserDetails object with password & authorities
   ↓
Spring compares received password with stored password
```

**Key method:**

```java
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String normalizedEmail = normalize(username);
    User user = userRepository.findByEmail(normalizedEmail)
        .orElseThrow(() -> new UsernameNotFoundException("No user found..."));
    
    return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .authorities(Collections.emptyList())  // No roles in this app
        .build();
}
```

**Breaking it down:**

1. **Normalize email** → lowercase, trim
2. **Query database** → find user by email
3. **Return UserDetails** → Spring's representation (not our entity)

**Why return UserDetails?**
- Decouples Spring Security from our `User` entity
- Standard interface Spring recognizes
- Can be reused with LDAP, OAuth, etc.

```java
.authorities(Collections.emptyList())
```

This app has **no roles**. Every authenticated user can do everything.

**With roles (production):**
```java
.authorities("ROLE_USER")  // or getGrantedAuthorities() from entity
```

Then in controller:
```java
@PreAuthorize("hasRole('ADMIN')")  // Only admins can call this
@DeleteMapping("/admin/users/{id}")
public void deleteUser(@PathVariable Long id) { ... }
```

---

## 🔄 Complete Request Journey with Security

```
1. Frontend sends:
   POST /api/users/123/counters
   Authorization: Basic YXRpbkBkZXYuY29tOnNlY3VyZTEyMw==
   { "counterName": "Push-ups", "counterValue": 0 }

2. SecurityFilterChain intercepts:
   - Checks: is this POST /api/users? NO
   - Requires: authenticated
   - Decodes header: atin@dev.com:secure123

3. CustomUserDetailsService.loadUserByUsername("atin@dev.com"):
   - Queries: SELECT * FROM users WHERE email = 'atin@dev.com'
   - Returns UserDetails with password from database

4. Spring Security:
   - Compares: received password (secure123) with stored (secure123)
   - Match! ✅
   - Sets SecurityContext.Authentication as authenticated

5. Controller receives request:
   - Calls ensureUserMatches(123)
   - Gets authenticated user (atin@dev.com → userId 123)
   - Compares: 123 == 123 ✅
   - Proceeds with business logic

6. Response returned
```

---

## 🚨 Security Considerations

### ✅ What This Project Does Right

1. **User ownership checks** → ensureUserMatches()
2. **No password in responses** → UserResponse has no password field
3. **Data normalization** → email lowercase + trim
4. **Stateless design** → scales easily
5. **Endpoint authorization** → registration public, rest authenticated

### ❌ What You'd Fix in Production

1. **NoOpPasswordEncoder** → Use BCrypt
2. **No HTTPS** → Always use HTTPS in production
3. **No rate limiting** → Add rate limiter to prevent brute-force
4. **No API keys** → Consider API key auth for public APIs
5. **No JWT** → HTTP Basic is fine for this demo, but JWT better for distributed systems
6. **No audit log** → Log who accessed what, when

### 🎓 Production Upgrade: JWT

```java
// Why JWT over HTTP Basic?
// HTTP Basic: every request validates password against database
// JWT: server signs token once, client sends token, server validates signature

@PostMapping("/login")
public AuthResponse login(@RequestBody LoginRequest request) {
    // Validate email/password
    // Generate JWT token
    return new AuthResponse(token);
}

// Then in subsequent requests:
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...

// Server validates JWT signature (no database hit!)
```

---

## 🎯 Key Takeaways

1. **@Configuration + @Bean** = Spring configuration
2. **SecurityFilterChain** = the security rules
3. **UserDetailsService** = load user from database
4. **HTTP Basic** = simple auth (not for production)
5. **Stateless** = great for REST/microservices
6. **Authorization matrix:**
   - POST /api/users → permitAll (register)
   - Everything else → authenticated
7. **User ownership check** → controller level (ensureUserMatches)
8. **Never leak passwords** → filter out in DTOs

---

## 🔓 Testing Endpoints with Auth

### Using curl:

```bash
# Register (no auth needed)
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "atin@dev.com",
    "username": "atin",
    "password": "secure123"
  }'

# Create counter (with auth)
curl -X POST http://localhost:8080/api/users/123/counters \
  -H "Authorization: Basic YXRpbkBkZXYuY29tOnNlY3VyZTEyMw==" \
  -H "Content-Type: application/json" \
  -d '{
    "counterName": "Push-ups",
    "counterValue": 0
  }'
```

### Using Postman:
1. Select "Authorization" tab
2. Choose "Basic Auth"
3. Enter email and password
4. Postman encodes it and adds header automatically

---

## 📖 The Full Architecture Cycle

You've now seen:

```
Request → Security (who are you?) 
       → Controller (what do you want?) 
       → Business Logic (authorization: user ownership)
       → Repository (query database)
       → Response
```

The security layer runs **first**, protecting everything downstream.

---

## 🎓 Next Level Learning

Once you master this, study:
- **JWT** (stateless token-based auth)
- **OAuth2** (federated login: "Login with Google")
- **Spring Security expressions** (@PreAuthorize, @PostAuthorize)
- **Encryption** (storing passwords securely)
