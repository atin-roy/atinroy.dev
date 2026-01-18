package dev.atinroy.counterBackend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * CREATE USER REQUEST DTO - Registration form schema
 * 
 * What frontend sends when registering:
 * POST /api/users
 * {
 *   "email": "atin@dev.com",
 *   "username": "atin",
 *   "password": "secure123"
 * }
 * 
 * @Data (Lombok) auto-generates:
 * - Getters, setters
 * - toString()
 * - equals(), hashCode()
 * 
 * Validation annotations (Jakarta Bean Validation):
 * - Run automatically when @Valid is used in controller
 * - Return 400 Bad Request with error details if validation fails
 * - Message is user-friendly error text
 * 
 * See: controller/ApiController.createUser() for usage
 * See: dto/README.md for validation patterns
 */
@Data
public class CreateUserRequest {
    
    /**
     * @NotBlank: Can't be null, empty, or only whitespace
     * @Size: String length between min and max characters
     * 
     * Why 3 minimum?
     * - "ab" is too short to be meaningful
     * - Prevent abuse: single-char names confusing
     * - Policy decision: adjust as needed
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /**
     * @NotBlank: Required
     * @Size(min = 8): At least 8 characters for basic security
     * 
     * In production: Add more strict password rules
     * - Uppercase letter
     * - Number
     * - Special character
     * - No common patterns
     * 
     * Library: Use spring-security-crypto or passay for password validation
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    /**
     * @NotBlank: Required
     * @Email: Must be valid email format
     * 
     * Spring validates with basic regex (not perfect, but good enough)
     * 
     * Why email as unique identifier?
     * - Emails are globally unique (Atin@dev.com = only one account)
     * - Usernames can be similar (many "Johns")
     * - Email is required for account recovery
     * 
     * In production: Send verification email before account is active
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
}
