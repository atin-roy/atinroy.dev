# Repository Package - The Database Query Layer

## 🏪 What's Here?

Repositories are **Spring Data JPA interfaces** that handle all database queries. They're the *only* place in your code that talks to the database.

**Think of it as:** A library checkout desk. You ask "Give me the book with ID 5" or "Find all books by Tolkien", and the library handles the SQL.

## Core Idea: JpaRepository Magic

```java
public interface UserRepository extends JpaRepository<User, Long> {
    // You get 50+ methods for FREE from JpaRepository
    // save(), findById(), findAll(), delete(), etc.
}
```

Spring generates the SQL **at startup** based on method signatures. No SQL strings to maintain!

## 📚 Query Methods = Auto-Generated SQL

### Naming Convention Formula

```
find      By     FieldName1     And|Or     FieldName2
↓         ↓      ↓              ↓          ↓
PREFIX    VERB   CONDITION      LOGIC      CONDITION

Result: Generates WHERE clause automatically
```

## Repositories in This Museum

### `UserRepository`

```java
Optional<User> findByEmail(String email)
  → SELECT * FROM users WHERE email = ?
  → Returns Optional (null-safe)

List<User> findByUsernameContaining(String username)
  → SELECT * FROM users WHERE username LIKE %?%
  → Case-sensitive partial match
```

**Key Pattern: Optional**
```java
Optional<User> result = userRepository.findByEmail("atin@example.com");

// Old way (risky):
if (result != null) { ... }

// New way (safe):
result.ifPresent(user -> println(user));
result.orElseThrow(() -> new Exception("Not found"));
```

### `CounterRepository`

```java
Optional<Counter> findByCounterIdAndUserId(Long counterId, Long userId)
  → SELECT * FROM counters WHERE counter_id = ? AND user_id = ?
  → Security: ensure user owns the counter before returning

List<Counter> findByUserId(Long userId)
  → SELECT * FROM counters WHERE user_id = ?

List<Counter> findByCounterValueGreaterThanEqualAndUserId(Long value, Long userId)
  → SELECT * FROM counters WHERE counter_value >= ? AND user_id = ?
```

**Important Pattern: Scoped Queries**

Notice how every query includes `AND user_id = ?`. This is **crucial for security**. Never let a user query another user's data by accident.

---

## 🎯 How It Works: Behind the Scenes

```java
userRepository.findByEmail("atin@dev.com")
```

1. Spring detects `findByEmail` at startup
2. Parses: "find" (prefix) + "By" + "Email" (field)
3. Generates SQL: `SELECT * FROM users WHERE email = ?`
4. Creates proxy method that handles the query
5. When called, executes SQL and maps result to `User` object (if found)

**No string SQL = Type-safe and refactor-friendly!**

---

## 🔄 CRUD Operations (Inherited from JpaRepository)

| Method | SQL | Use Case |
|--------|-----|----------|
| `save(entity)` | INSERT or UPDATE | Create or update |
| `findById(id)` | SELECT ... WHERE id = ? | Get by primary key |
| `findAll()` | SELECT * | Get everything |
| `deleteById(id)` | DELETE WHERE id = ? | Remove by ID |
| `count()` | SELECT COUNT(*) | How many? |

---

## 🎲 When to Write Custom Queries

### Method naming isn't enough → Use `@Query`

```java
@Query("SELECT c FROM Counter c WHERE c.counterValue > :minVal ORDER BY c.counterValue DESC")
List<Counter> findTopCounters(@Param("minVal") Long minVal);
```

This project doesn't use custom queries because it's simple enough for method names.

---

## 🚨 Security Consideration

**ALWAYS scope queries to the current user.**

```java
// ✅ GOOD
counterRepository.findByCounterIdAndUserId(counterId, currentUserId)

// ❌ DANGEROUS
counterRepository.findById(counterId)  // User 1 could steal User 2's counter!
```

See how `ApiController` uses `ensureUserMatches(userId)` first? That's the gatekeeper.

---

## 📖 Next Steps

Now that you know *how* to query, see how the **Controller** uses these queries in `controller/README.md`.
