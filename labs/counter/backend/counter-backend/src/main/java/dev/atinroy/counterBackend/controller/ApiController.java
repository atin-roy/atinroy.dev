package dev.atinroy.counterBackend.controller;

import dev.atinroy.counterBackend.dto.counter.CounterResponse;
import dev.atinroy.counterBackend.dto.counter.CreateCounterRequest;
import dev.atinroy.counterBackend.dto.counter.IncrementCounterRequest;
import dev.atinroy.counterBackend.dto.counter.UpdateCounterRequest;
import dev.atinroy.counterBackend.dto.user.CreateUserRequest;
import dev.atinroy.counterBackend.dto.user.UserResponse;
import dev.atinroy.counterBackend.entity.Counter;
import dev.atinroy.counterBackend.entity.User;
import dev.atinroy.counterBackend.mapper.UserMapper;
import dev.atinroy.counterBackend.repository.CounterRepository;
import dev.atinroy.counterBackend.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class ApiController {

    private final UserRepository userRepository;
    private final CounterRepository counterRepository;
    private final UserMapper userMapper;

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        String email = normalize(request.getEmail());
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(request.getUsername().trim());
        user.setPassword(request.getPassword());

        User saved = userRepository.save(user);
        UserResponse response = userMapper.toUserResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return userMapper.toUserResponse(ensureUserMatches(userId));
    }

    @GetMapping("/users")
    public List<UserResponse> searchUsers(@RequestParam(required = false) String username) {
        List<User> users = StringUtils.hasText(username)
                ? userRepository.findByUsernameContaining(username.trim())
                : userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{userId}/counters")
    public ResponseEntity<CounterResponse> createCounter(
            @PathVariable Long userId,
            @Valid @RequestBody CreateCounterRequest request
    ) {
        User user = ensureUserMatches(userId);
        Counter counter = new Counter();
        counter.setCounterName(request.getCounterName().trim());
        counter.setCounterValue(request.getCounterValue());
        counter.setUser(user);
        Counter saved = counterRepository.save(counter);
        return ResponseEntity.status(HttpStatus.CREATED).body(toCounterResponse(saved));
    }

    @GetMapping("/users/{userId}/counters")
    public List<CounterResponse> listCounters(
            @PathVariable Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long minValue,
            @RequestParam(required = false) Long maxValue
    ) {
        ensureUserMatches(userId); // ensure authenticated caller matches the path
        // Counter IDs are returned but the frontend intentionally ignores them.
        String normalizedSearch = normalize(name);
        return counterRepository.findByUserId(userId).stream()
                .filter(counter -> matchesName(counter, normalizedSearch))
                .filter(counter -> minValue == null || counter.getCounterValue() >= minValue)
                .filter(counter -> maxValue == null || counter.getCounterValue() <= maxValue)
                .map(this::toCounterResponse)
                .collect(Collectors.toList());
    }

    @PatchMapping("/users/{userId}/counters/{counterId}")
    public CounterResponse updateCounter(
            @PathVariable Long userId,
            @PathVariable Long counterId,
            @Valid @RequestBody UpdateCounterRequest request
    ) {
        ensureUserMatches(userId);
        Counter counter = findCounter(userId, counterId);
        boolean changed = false;

        if (StringUtils.hasText(request.getCounterName())) {
            counter.setCounterName(request.getCounterName().trim());
            changed = true;
        }

        if (request.getCounterValue() != null) {
            counter.setCounterValue(request.getCounterValue());
            changed = true;
        }

        if (!changed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide a name or value to change");
        }

        return toCounterResponse(counterRepository.save(counter));
    }

    @PatchMapping("/users/{userId}/counters/{counterId}/increment")
    public CounterResponse incrementCounter(
            @PathVariable Long userId,
            @PathVariable Long counterId,
            @Valid @RequestBody IncrementCounterRequest request
    ) {
        ensureUserMatches(userId);
        Counter counter = findCounter(userId, counterId);
        counter.setCounterValue(counter.getCounterValue() + request.getIncrementBy());
        return toCounterResponse(counterRepository.save(counter));
    }

    @DeleteMapping("/users/{userId}/counters/{counterId}")
    public ResponseEntity<Void> deleteCounter(
            @PathVariable Long userId,
            @PathVariable Long counterId
    ) {
        ensureUserMatches(userId);
        Counter counter = findCounter(userId, counterId);
        counterRepository.delete(counter);
        return ResponseEntity.noContent().build();
    }

    private CounterResponse toCounterResponse(Counter counter) {
        CounterResponse response = new CounterResponse();
        response.setCounterId(counter.getCounterId());
        response.setCounterName(counter.getCounterName());
        response.setCounterValue(counter.getCounterValue());
        return response;
    }

    private Counter findCounter(Long userId, Long counterId) {
        return counterRepository.findByCounterIdAndUserId(counterId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Counter not found for user"));
    }

    private String normalize(String input) {
        if (!StringUtils.hasText(input)) {
            return null;
        }
        return input.trim().toLowerCase(Locale.ROOT);
    }

    private boolean matchesName(Counter counter, String search) {
        if (search == null) {
            return true;
        }
        return counter.getCounterName().toLowerCase(Locale.ROOT).contains(search);
    }

    private User ensureUserMatches(Long userId) {
        User authenticatedUser = getAuthenticatedUser();
        if (!authenticatedUser.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Users can only access their own counters");
        }
        return authenticatedUser;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }

        String principalName;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            principalName = userDetails.getUsername();
        } else if (principal instanceof String name) {
            principalName = name;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to resolve authenticated user");
        }

        return userRepository.findByEmail(principalName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authenticated user not found"));
    }
}
