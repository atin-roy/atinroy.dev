# Entity Package - The Database Schema

## 🏛️ What's Here?

Entities are **JPA-annotated POJOs that map directly to database tables**. This is where your data model lives. Think of entities as the "source of truth" - the shape of your data in the database.

## Key Principle: One @Entity = One Database Table

When you mark a class with `@Entity`, Spring Data JPA (Hibernate) automatically:
- Creates a SQL table if it doesn't exist
- Maps Java fields to SQL columns
- Handles INSERT, UPDATE, DELETE operations
- Manages primary keys and relationships

## 📚 Entities in This Museum

### `User.java` - Identity & Authentication Hub
```sql
CREATE TABLE users (
  user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255) UNIQUE NOT NULL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
)
```

**Key Patterns:**
- `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)` = auto-increment primary key
- `@Column(unique = true)` = database constraint (email must be unique)
- `@PrePersist` & `@PreUpdate` = lifecycle hooks (run before INSERT/UPDATE)
- `@Data` (Lombok) = auto-generates getters, setters, toString, equals, hashCode

**Why these choices?**
- Email is the unique identifier (login credential)
- Username is just display text (not unique - can change)
- Password stored as plain text here (BAD in production! Should use BCrypt)

### `Counter.java` - User's Data Records

```sql
CREATE TABLE counters (
  counter_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  counter_name VARCHAR(255) NOT NULL,
  counter_value BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
)
```

**Key Patterns:**
- `@ManyToOne(fetch = FetchType.LAZY)` = many counters per user, one user per counter
  - LAZY loading = don't fetch the User until asked (performance optimization)
- `@JoinColumn(name = "user_id")` = foreign key column name
- Duplicate `userId` field marked with `insertable = false, updatable = false` = read-only access to FK

**Why the duplicate userId field?**
```java
// Option 1: Access via relationship (causes lazy load)
counter.getUser().getUserId()

// Option 2: Direct field access (no DB hit!)
counter.getUserId()
```

Use Option 2 for performance when you only need the ID.

---

## 🎯 Common Questions

**Q: Why not use `createdAt` and `updatedAt` in every query response?**
A: For that, use DTOs (next package). Entities are internal; expose only what's needed.

**Q: What's the difference between @Column and @JoinColumn?**
A: `@Column` = simple value column. `@JoinColumn` = foreign key (relationship).

**Q: Should I add business logic here?**
A: Avoid it! Entities should be "dumb data holders". Put logic in Services (which we don't have here, but would in bigger apps).

---

## 🔑 Lifecycle Hooks You'll Use

```java
@PrePersist   // Before INSERT
@PreUpdate    // Before UPDATE
@PostLoad     // After SELECT (before returning to your code)
@PreRemove    // Before DELETE
```

Use these for:
- Setting timestamps (like here)
- Normalizing data
- Validating state

---

## 📖 Next Steps

See how these entities are accessed through **Repositories** (the `repository/` package).
