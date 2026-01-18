package dev.atinroy.counterBackend.security;

import dev.atinroy.counterBackend.entity.User;
import dev.atinroy.counterBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;

/**
 * CUSTOM USER DETAILS SERVICE - Load user from database for authentication
 * 
 * Spring Security calls this when validating HTTP Basic auth headers.
 * 
 * Flow:
 * 1. Frontend sends: Authorization: Basic base64(email:password)
 * 2. Spring extracts email from header
 * 3. Calls: loadUserByUsername(email)
 * 4. We query database to find user
 * 5. Return UserDetails with stored password
 * 6. Spring compares: received password == database password
 * 
 * Why this indirection?
 * - UserDetailsService is Spring's interface
 * - Could load from database, LDAP, OAuth, hardcoded, etc.
 * - Flexible: swap implementations without changing security config
 * 
 * @Service: Register as Spring component (singleton)
 * @RequiredArgsConstructor: Inject UserRepository
 * 
 * See: security/README.md for complete flow
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * MAIN AUTHENTICATION HOOK
     * Called by Spring Security when processing HTTP Basic auth
     * 
     * Parameter name "username" is misleading (legacy Spring naming).
     * In our case, it's the email address.
     * 
     * @param username The username from Authorization header
     *                 (actually the email in our implementation)
     * 
     * @return UserDetails object with password and authorities
     * 
     * @throws UsernameNotFoundException If user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Normalize the input: lowercase + trim
        // "ATIN@DEV.COM " → "atin@dev.com"
        String normalizedEmail = normalize(username);
        
        // Query database for user by email
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> 
                    new UsernameNotFoundException("No user found for email: " + normalizedEmail));

        // Return Spring's UserDetails object (not our User entity)
        // This is the key: decouple Spring Security from our domain model
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())           // Store email as username
                .password(user.getPassword())             // Password from database
                .authorities(Collections.emptyList())     // No roles (everyone can do everything)
                .build();
    }

    /**
     * EMAIL NORMALIZATION
     * 
     * Why normalize?
     * Emails are case-insensitive: Atin@dev.com = atin@dev.com = ATIN@DEV.COM
     * Database lookups should be consistent.
     * 
     * Steps:
     * 1. trim(): Remove leading/trailing spaces
     *    "  atin@dev.com  " → "atin@dev.com"
     * 2. toLowerCase(Locale.ROOT): Convert to lowercase
     *    "ATIN@DEV.COM" → "atin@dev.com"
     * 
     * Locale.ROOT: ignore regional settings
     * (Turkish has special 'i' that causes issues)
     * 
     * @param value The email to normalize (might be null)
     * @return Normalized email, or empty string if null
     */
    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }
}
