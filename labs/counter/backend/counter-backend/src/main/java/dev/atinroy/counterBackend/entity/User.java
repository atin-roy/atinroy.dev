package dev.atinroy.counterBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * USER ENTITY - Database representation of a user
 * 
 * This class maps directly to the "users" table in the database.
 * JPA (Hibernate) automatically creates this table on startup.
 * 
 * Key concepts:
 * - @Entity: tells Spring this is a database model
 * - @Table(name = "users"): specifies exact table name
 * - @Data: Lombok generates getters, setters, toString(), equals(), hashCode()
 * 
 * See: entity/README.md for the full database architecture
 */
@Entity
@Table(name = "users")
@Data
public class User {
    
    /**
     * IDENTITY primary key strategy:
     * - @Id marks this as the unique identifier
     * - @GeneratedValue(strategy = IDENTITY) = auto-increment in database
     * - Equivalent SQL: BIGINT PRIMARY KEY AUTO_INCREMENT
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * Email is the unique login credential.
     * - unique = true: database constraint prevents duplicate emails
     * - nullable = false: always required
     * 
     * Note: In production, validate format with @Email annotation
     * and consider adding a unique index for performance.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Username is just display text, not unique.
     * Multiple users could have similar usernames.
     * Use email for lookups, username for display.
     */
    @Column(unique = false, nullable = false)
    private String username;

    /**
     * Password stored in plain text here (BAD PRACTICE!).
     * In production: use BCrypt to hash passwords
     * 
     * SECURITY WARNING: Never return password in API responses!
     * UserResponse DTO explicitly excludes this field.
     * See: security/README.md for authentication details
     */
    @Column(unique = false, nullable = false)
    private String password;

    /**
     * Audit fields: track when records were created/modified.
     * Automatically set by @PrePersist and @PreUpdate hooks.
     * Useful for debugging: "When did this user register?"
     */
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * @PrePersist lifecycle hook
     * Runs automatically BEFORE the first INSERT
     * Sets createdAt to now, updatedAt stays null initially
     */
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * @PreUpdate lifecycle hook
     * Runs automatically BEFORE every UPDATE
     * Updates the updatedAt timestamp
     * 
     * This ensures the database always knows when a record was last modified.
     */
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
