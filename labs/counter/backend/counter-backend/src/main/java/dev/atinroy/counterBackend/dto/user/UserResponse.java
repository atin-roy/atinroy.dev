package dev.atinroy.counterBackend.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * USER RESPONSE DTO - What frontend receives after successful operations
 * 
 * Example responses:
 * 
 * POST /api/users → 201 Created
 * {
 *   "userId": 1,
 *   "username": "atin",
 *   "email": "atin@dev.com",
 *   "createdAt": "2025-01-18T10:30:00"
 * }
 * 
 * GET /api/users/1 → 200 OK
 * {
 *   "userId": 1,
 *   "username": "atin",
 *   "email": "atin@dev.com",
 *   "createdAt": "2025-01-18T10:30:00"
 * }
 * 
 * Key Security Pattern:
 * ⚠️ NO password field!
 * 
 * Why?
 * 1. Accidental exposure of hashed passwords is a vulnerability
 * 2. Frontend doesn't need the password back
 * 3. API contract enforces this: if password isn't in DTO, it can't leak
 * 
 * How does this prevent leaks?
 * - UserMapper only maps fields that exist in UserResponse
 * - password field doesn't exist here → MapStruct won't copy it
 * - Type-safe guarantee: impossible to accidentally include it
 * 
 * Conversion:
 * User (entity) → UserResponse (DTO)
 *   userId ✓
 *   username ✓
 *   email ✓
 *   createdAt ✓
 *   password ✗ (excluded by design)
 *   updatedAt ✗ (not in response)
 * 
 * See: mapper/UserMapper.java for the conversion logic
 */
@Data
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    
    // ⚠️ INTENTIONALLY EXCLUDED: password
    // See security/README.md for why this matters
}
