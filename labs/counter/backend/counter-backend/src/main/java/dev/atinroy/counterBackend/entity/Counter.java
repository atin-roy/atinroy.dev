package dev.atinroy.counterBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * COUNTER ENTITY - A single counter record owned by a user
 * 
 * Database Table:
 * counters
 * ├── counter_id (PK, auto-increment)
 * ├── counter_name
 * ├── counter_value
 * ├── user_id (FK)
 * ├── created_at
 * └── updated_at
 * 
 * Relationship: Many Counters → One User (N:1)
 * Each counter belongs to exactly one user.
 * 
 * See: entity/README.md for relationship details
 */
@Data
@Entity
@Table(name = "counters")
public class Counter {
    
    /**
     * Auto-increment primary key.
     * Like User.userId, this is the unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long counterId;

    /**
     * Name of the counter (e.g., "Push-ups", "Water intake", "Books read").
     * Required, but not unique (multiple counters can have same name).
     */
    @Column(nullable = false)
    String counterName;

    /**
     * Current value of the counter (e.g., 25 push-ups done).
     * Can be positive, negative, or zero.
     * No constraints here; business logic validates if needed.
     */
    @Column(nullable = false)
    Long counterValue;

    /**
     * RELATIONSHIP: @ManyToOne(fetch = FetchType.LAZY)
     * 
     * "Many counters to one user" - This counter belongs to a user.
     * 
     * LAZY vs EAGER loading:
     * - LAZY: Don't fetch User object until asked
     *   Benefits: faster queries, less data loaded
     *   Cost: causes extra query if accessed
     * - EAGER: Always fetch User with Counter
     *   Benefits: fewer queries
     *   Cost: always loads even if not needed
     * 
     * LAZY is better for most cases. Use EAGER only when you always need it.
     * 
     * Example:
     *   Counter c = counterRepository.findById(1);  // Doesn't load User
     *   User u = c.getUser();  // Now it loads User from database
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * PERFORMANCE OPTIMIZATION: Direct access to foreign key
     * 
     * Instead of:  counter.getUser().getUserId()  → causes lazy load + database hit
     * Just use:    counter.getUserId()            → direct field, no extra query
     * 
     * Why need both?
     * - user field: object relationship (when you need User data)
     * - userId field: just the ID (when you only need the FK value)
     * 
     * Marked with insertable=false, updatable=false because:
     * - The actual user_id column is managed by the @JoinColumn above
     * - This field is read-only, automatically synced
     * 
     * WITHOUT these flags: Hibernate would try to INSERT/UPDATE twice!
     */
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    /**
     * Audit timestamps (same as User entity).
     * Automatically set by @PrePersist and @PreUpdate hooks.
     */
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * @PrePersist lifecycle hook - runs before INSERT.
     * Sets the creation timestamp.
     */
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * @PreUpdate lifecycle hook - runs before UPDATE.
     * Updates the modification timestamp.
     */
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
