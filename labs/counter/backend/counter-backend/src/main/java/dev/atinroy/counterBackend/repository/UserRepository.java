package dev.atinroy.counterBackend.repository;

import dev.atinroy.counterBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * USER REPOSITORY - Database access for User entities
 * 
 * Spring Data JPA automatically implements this interface!
 * You just define method signatures, and Spring generates the SQL at startup.
 * 
 * Inheritance:
 * - JpaRepository<User, Long>
 *   ├── User = the entity type to query
 *   └── Long = the primary key type (userId is Long)
 * 
 * You get 50+ free methods: save(), findById(), findAll(), delete(), etc.
 * 
 * Naming Convention:
 * - find + By + FieldName = SELECT * WHERE fieldName = ?
 * - findBy + FieldName + Containing = SELECT * WHERE fieldName LIKE %?%
 * - Optional wrapper = null-safe (handles missing records gracefully)
 * 
 * See: repository/README.md for detailed query patterns
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email (exact match)
     * 
     * Generated SQL: SELECT * FROM users WHERE email = ?
     * 
     * Usage in code:
     *   Optional<User> user = userRepository.findByEmail("atin@dev.com");
     *   user.ifPresent(u -> println(u.getUsername()));
     *   user.orElseThrow(() -> new Exception("User not found"));
     * 
     * Returns Optional because user might not exist.
     * Never null - either contains user or empty.
     * 
     * Why Optional instead of User?
     *   - Forces you to handle the "not found" case
     *   - Type-safe: can't forget null check
     *   - Functional style: .map(), .filter(), .ifPresent()
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by ID (primary key lookup)
     * 
     * Generated SQL: SELECT * FROM users WHERE user_id = ?
     * 
     * Actually, JpaRepository already has findById(Long id),
     * so this method is redundant! But kept for clarity.
     * 
     * In production, remove this - just use inherited findById().
     */
    Optional<User> findByUserId(Long id);

    /**
     * Find users by partial username match (case-sensitive)
     * 
     * Generated SQL: SELECT * FROM users WHERE username LIKE %?%
     * 
     * Usage:
     *   List<User> users = userRepository.findByUsernameContaining("john");
     *   // Returns users with username containing "john" (case-sensitive)
     *   // e.g., "John", "john", "johnny" but NOT "JOHN" in some databases
     * 
     * Returns List (could be empty, one, or many results).
     * 
     * Note: Case-sensitivity depends on database collation.
     * For case-insensitive search: use SQL @Query or do .toLowerCase() in controller
     */
    List<User> findByUsernameContaining(String username);
}
