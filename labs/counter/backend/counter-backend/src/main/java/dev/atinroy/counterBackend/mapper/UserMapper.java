package dev.atinroy.counterBackend.mapper;

import dev.atinroy.counterBackend.dto.user.UserResponse;
import dev.atinroy.counterBackend.entity.User;
import org.mapstruct.Mapper;

/**
 * USER MAPPER - Entity ↔ DTO conversion
 * 
 * MapStruct is a code generator that creates mapper implementations at compile time.
 * 
 * @Mapper(componentModel = "spring") tells MapStruct:
 * - Generate implementation class (UserMapperImpl)
 * - Register it as a Spring @Component (auto-injectable)
 * 
 * Magic: The actual mapping logic is generated automatically!
 * You just declare the method signature, Spring generates the implementation.
 * 
 * Why MapStruct?
 * - Fast: compiles to efficient code
 * - Type-safe: compile errors if fields don't match
 * - Flexible: @Mapping annotations for custom rules
 * - Null-safe: handles null objects
 * 
 * Behind the scenes, MapStruct generates something like:
 * 
 *   public class UserMapperImpl implements UserMapper {
 *     @Override
 *     public UserResponse toUserResponse(User user) {
 *       if (user == null) return null;
 *       UserResponse response = new UserResponse();
 *       response.setUserId(user.getUserId());
 *       response.setUsername(user.getUsername());
 *       response.setEmail(user.getEmail());
 *       response.setCreatedAt(user.getCreatedAt());
 *       // Notice: NO password mapping! 🔐
 *       return response;
 *     }
 *   }
 * 
 * See: mapper/README.md for mapping patterns and examples
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    
    /**
     * Convert User entity to UserResponse DTO
     * 
     * Field mapping:
     * - userId → userId (exact match)
     * - username → username (exact match)
     * - email → email (exact match)
     * - createdAt → createdAt (exact match)
     * - password → EXCLUDED (no field in UserResponse)
     * 
     * Usage in code:
     *   User user = userRepository.findByEmail("atin@dev.com").orElseThrow();
     *   UserResponse response = userMapper.toUserResponse(user);
     *   return ResponseEntity.ok(response);
     * 
     * Security: Password is automatically excluded because:
     * 1. UserResponse DTO doesn't have a password field
     * 2. MapStruct only maps fields that exist in both source and target
     * 3. No need for @Mapping(target = "password", ignore = true)
     * 
     * Why this pattern matters:
     * ✅ Prevents accidental password leaks
     * ✅ Creates clean API contracts (frontend only sees what's in DTO)
     * ✅ Decouples entity changes from API responses
     * 
     * If UserResponse had a password field, it would be copied!
     * Always be careful about what you include in response DTOs.
     */
    UserResponse toUserResponse(User user);
}
