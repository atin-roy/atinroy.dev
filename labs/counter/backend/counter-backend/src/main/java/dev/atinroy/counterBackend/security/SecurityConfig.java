package dev.atinroy.counterBackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SECURITY CONFIG - Spring Security configuration
 * 
 * This class defines HOW Spring Security protects your API.
 * It's like the bouncer's rule book for the nightclub.
 * 
 * Flow when request arrives:
 * 1. SecurityFilterChain intercepts request
 * 2. Checks: "Does this endpoint require auth?"
 * 3. If yes: validates Authorization header
 * 4. Calls CustomUserDetailsService to load user
 * 5. Compares provided password with database password
 * 6. If valid: allows request to proceed
 * 7. If invalid: returns 401 Unauthorized
 * 
 * @Configuration: marks this as Spring config class (loaded on startup)
 * @RequiredArgsConstructor: Lombok injects CustomUserDetailsService
 * 
 * @Bean methods: return objects that Spring manages
 * Spring automatically calls these on startup.
 * 
 * See: security/README.md for detailed explanation
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    /**
     * MAIN SECURITY CHAIN - The bouncer's rule book
     * 
     * SecurityFilterChain defines:
     * - Which endpoints need authentication
     * - Which are public
     * - How to authenticate (HTTP Basic, OAuth, JWT, etc.)
     * - Session management policy
     * - CSRF protection
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                /**
                 * CSRF Protection
                 * csrf(AbstractHttpConfigurer::disable)
                 * 
                 * What's CSRF?
                 * Cross-Site Request Forgery attack.
                 * Browser automatically sends cookies to any site.
                 * 
                 * Traditional web app flow (❌ vulnerable to CSRF):
                 *   User logs in on mybank.com
                 *   Browser stores session cookie
                 *   User visits evil.com
                 *   evil.com: <img src="mybank.com/transfer?amount=1000&to=attacker">
                 *   Browser automatically sends cookie → transfer happens! 😱
                 * 
                 * Solution: CSRF token
                 *   Form includes hidden CSRF token
                 *   evil.com can't read token (same-origin policy)
                 *   Server rejects requests without valid token
                 * 
                 * Why disabled here?
                 * - API is STATELESS (no sessions, no cookies)
                 * - Each request includes credentials in header
                 * - evil.com can't read Authorization header (same-origin policy)
                 * - Clients are trusted (not in browser)
                 * 
                 * CSRF only matters for browser-based apps with cookies.
                 * REST APIs using auth headers are safe without CSRF tokens.
                 */
                .csrf(AbstractHttpConfigurer::disable)
                
                /**
                 * AUTHORIZATION RULES - Who can access what?
                 * 
                 * requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                 * → Allow public registration
                 * 
                 * anyRequest().authenticated()
                 * → Everything else requires authentication
                 * 
                 * In English:
                 * "I allow anyone to POST to /api/users (register).
                 *  Everything else (GET /users, POST /counters, etc.)
                 *  requires you to be authenticated."
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .anyRequest().authenticated())
                
                /**
                 * HTTP BASIC AUTHENTICATION
                 * httpBasic(Customizer.withDefaults())
                 * 
                 * How it works:
                 * 1. Frontend sends: Authorization: Basic base64(email:password)
                 * 2. Spring decodes header
                 * 3. Calls CustomUserDetailsService.loadUserByUsername("email")
                 * 4. Gets user password from database
                 * 5. Compares: received password == database password
                 * 6. If match: sets authentication in SecurityContext
                 * 
                 * Example curl request:
                 * curl -X GET http://localhost:8080/api/users/123 \
                 *   -u atin@dev.com:password123
                 * 
                 * Postman: Authorization tab → Basic Auth → enter email/password
                 * 
                 * ⚠️ Not for production!
                 * Credentials sent with every request (even if HTTPS, risky).
                 * Better: JWT tokens or OAuth2.
                 */
                .httpBasic(Customizer.withDefaults())
                
                /**
                 * CUSTOM USER DETAILS SERVICE
                 * .userDetailsService(userDetailsService)
                 * 
                 * Tells Spring: "When loading user details, use this service."
                 * CustomUserDetailsService queries database to find user by email.
                 * 
                 * If not set: Spring uses in-memory user store (limited).
                 */
                .userDetailsService(userDetailsService)
                
                /**
                 * STATELESS SESSIONS
                 * sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 * 
                 * What's stateless?
                 * - Server doesn't store user sessions
                 * - Each request is independent
                 * - Client must provide credentials every time
                 * 
                 * Why STATELESS for REST APIs?
                 * ✅ Scales: any server can handle any request
                 * ✅ Distributed: works with load balancers
                 * ✅ Mobile-friendly: no session affinity needed
                 * 
                 * Traditional stateful (❌ doesn't scale):
                 *   User logs in → server stores session in memory
                 *   Request → routed to same server (session affinity)
                 *   Hard to scale horizontally
                 *   Hard to add caching layers
                 * 
                 * STATELESS (✅ scales perfectly):
                 *   User includes credentials → every server validates
                 *   Request → any server can handle it
                 *   Easy to scale: add more servers
                 *   Works with load balancers
                 */
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                /**
                 * DISABLE FORM LOGIN
                 * formLogin(AbstractHttpConfigurer::disable)
                 * 
                 * Normally Spring provides a login form page:
                 * GET /login → shows HTML form
                 * POST /login → processes form submission
                 * 
                 * For REST APIs: no need for HTML login form.
                 * Clients use HTTP Basic or JWT directly.
                 */
                .formLogin(AbstractHttpConfigurer::disable)
                
                .build();  // Return the configured SecurityFilterChain
    }

    /**
     * PASSWORD ENCODER
     * 
     * ⚠️ WARNING: NoOpPasswordEncoder is INSECURE!
     * This stores passwords in PLAIN TEXT in the database.
     * NEVER use in production!
     * 
     * Why used here?
     * - Learning project: focus on architecture, not crypto
     * - Simple to understand: no hashing/salting complexity
     * - Demonstrates password flow
     * 
     * Production alternative: BCryptPasswordEncoder
     * 
     * How BCrypt works:
     * 1. Hashing: password → hash (one-way function)
     * 2. Salting: add random data before hashing (prevents rainbow tables)
     * 3. Verification: password → hash → compare with stored hash
     * 
     * Setup:
     * @Bean
     * public PasswordEncoder passwordEncoder() {
     *     return new BCryptPasswordEncoder();
     * }
     * 
     * Usage in code:
     * String hashedPassword = passwordEncoder.encode(rawPassword);
     * user.setPassword(hashedPassword);  // Store hash, not raw password
     * 
     * Spring handles comparison automatically:
     * passwordEncoder.matches(rawPassword, hashedPassword)  // true if correct
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // ⚠️ DEVELOPMENT ONLY - NEVER IN PRODUCTION
        // Use BCryptPasswordEncoder in real apps
        return NoOpPasswordEncoder.getInstance();
    }
}
