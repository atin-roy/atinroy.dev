package dev.atinroy.counterBackend.dto.counter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * CREATE COUNTER REQUEST DTO - Schema for creating a new counter
 * 
 * Request:
 * POST /api/users/123/counters
 * {
 *   "counterName": "Push-ups",
 *   "counterValue": 0
 * }
 * 
 * Usage flow:
 * 1. Frontend sends this JSON
 * 2. Spring deserializes JSON → CreateCounterRequest object
 * 3. @Valid annotation triggers validation
 * 4. If valid → controller receives object and proceeds
 * 5. If invalid → Spring returns 400 Bad Request with error details
 * 
 * See: controller/ApiController.createCounter() for usage
 */
@Data
public class CreateCounterRequest {
    
    /**
     * Counter name (e.g., "Push-ups", "Books read", "Coffee cups")
     * 
     * @NotBlank: can't be null or only whitespace
     * @Size: length 1-100 characters
     * 
     * Why required?
     * - Users need to know what the counter tracks
     * - Empty name is meaningless
     * 
     * Why max 100?
     * - Prevent database pollution from extremely long strings
     * - UI typically needs reasonable width
     * - Adjust based on your needs
     */
    @NotBlank(message = "Counter name is required")
    @Size(min = 1, max = 100, message = "Counter name must be between 1 and 100 characters")
    private String counterName;

    /**
     * Initial counter value
     * 
     * @NotNull: can't be null (but CAN be 0, negative, etc.)
     * 
     * Difference between @NotBlank and @NotNull:
     * - @NotBlank: String can't be "", " ", or null
     * - @NotNull: field can't be null (but can be 0, false, etc.)
     * 
     * Examples:
     * - "Create counter 'Push-ups' with value 0" → starting fresh
     * - "Create counter 'Books completed' with value 5" → already have done some
     * - "Create counter 'Debt' with value -1000" → tracking negative numbers
     * 
     * Why Long instead of Integer?
     * - Counters could run very high (billions)
     * - Long covers range from -9,223,372,036,854,775,808 to +9,223,372,036,854,775,807
     * - Integer would overflow for high-use counters
     * - Future-proofing for growth
     */
    @NotNull(message = "Initial value is required")
    private Long counterValue;
}
