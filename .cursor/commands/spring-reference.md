# Spring Boot Quick Reference

Common Spring Boot patterns and annotations you might forget.

## Core Annotations
```java
// Application entry point
@SpringBootApplication // Combines @Configuration, @EnableAutoConfiguration, @ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// Controller layer (REST endpoints)
@RestController // Combines @Controller + @ResponseBody
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping // GET /api/users
    public List<User> getUsers() { }
    
    @GetMapping("/{id}") // GET /api/users/123
    public User getUser(@PathVariable Long id) { }
    
    @PostMapping // POST /api/users
    public User createUser(@RequestBody @Valid UserDTO dto) { }
    
    @PutMapping("/{id}") // PUT /api/users/123
    public User updateUser(@PathVariable Long id, @RequestBody UserDTO dto) { }
    
    @DeleteMapping("/{id}") // DELETE /api/users/123
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) { }
}

// Service layer (business logic)
@Service
public class UserService {
    private final UserRepository userRepository;
    
    // Constructor injection (preferred over @Autowired)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

// Repository layer (data access)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring auto-implements based on method name!
    List<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByAgeGreaterThan(int age);
    
    // Custom query
    @Query("SELECT u FROM User u WHERE u.email LIKE %:domain")
    List<User> findByEmailDomain(@Param("domain") String domain);
}
```

## Entity (JPA)
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(name = "full_name")
    private String name;
    
    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}

// Modern approach: Use records for DTOs
public record UserDTO(
    String email,
    String name
) {}
```

## Validation
```java
public record CreateUserDTO(
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email,
    
    @NotBlank
    @Size(min = 2, max = 50)
    String name,
    
    @Min(18)
    @Max(120)
    Integer age
) {}

// In controller
@PostMapping
public User createUser(@RequestBody @Valid CreateUserDTO dto) {
    // If validation fails, Spring automatically returns 400 Bad Request
}
```

## Exception Handling
```java
// Custom exception
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}

// Global exception handler
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();
        return new ErrorResponse("Validation failed", errors);
    }
}
```

## Configuration (application.yml)
```yaml
spring:
  application:
    name: my-app
  
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: ${DB_USER:postgres}  # Environment variable with default
    password: ${DB_PASSWORD}
    
  jpa:
    hibernate:
      ddl-auto: validate  # Options: none, validate, update, create, create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        
server:
  port: 8080
  error:
    include-message: always
    include-stacktrace: on_param  # Only show in dev (add ?trace=true)
```

## Dependency Injection Patterns
```java
// ✅ GOOD: Constructor injection (immutable, testable)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
}

// ❌ AVOID: Field injection (harder to test)
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}

// If many dependencies, consider refactoring (SRP violation)
```

## Common JPA Query Methods
```java
// Derived query methods (Spring auto-implements)
findBy{Property}                    // findByEmail
findBy{Property}And{Property}       // findByEmailAndAge
findBy{Property}Or{Property}        // findByEmailOrUsername
findBy{Property}In(Collection)      // findByIdIn(List.of(1,2,3))
findBy{Property}Between(a, b)       // findByAgeBetween(18, 65)
findBy{Property}LessThan(value)     // findByAgeGreaterThan(18)
findBy{Property}Like(pattern)       // findByNameLike("%John%")
findBy{Property}Containing(string)  // findByNameContaining("John")
findBy{Property}IsNull()            // findByEmailIsNull()
findBy{Property}OrderBy{Property}   // findByAgeOrderByNameAsc(25)
```

## Testing
```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    void shouldGetUser() throws Exception {
        when(userService.findById(1L))
            .thenReturn(Optional.of(new User("test@example.com")));
            
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}
```

**Why these patterns?** Follow SOLID principles, testability, and Spring best practices!
