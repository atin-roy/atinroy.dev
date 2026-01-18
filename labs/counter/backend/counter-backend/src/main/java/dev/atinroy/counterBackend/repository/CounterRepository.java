package dev.atinroy.counterBackend.repository;

import dev.atinroy.counterBackend.entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * COUNTER REPOSITORY - Database access for Counter entities
 * 
 * Key difference from UserRepository:
 * ✅ ALL methods are scoped to userId (security-first design)
 * 
 * NEVER query counters without userId - otherwise users can see each other's data!
 * 
 * Naming patterns:
 * - And: combine conditions (WHERE clause + AND)
 * - GreaterThanEqual, LessThanEqual: numeric comparisons (>=, <=)
 * - Containing: partial string match (LIKE)
 * 
 * See: repository/README.md for why scoping matters
 */
@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {
    
    /**
     * Find a specific counter AND verify ownership
     * 
     * Generated SQL: SELECT * FROM counters WHERE counter_id = ? AND user_id = ?
     * 
     * Security pattern: Always check counterId AND userId together!
     * 
     * Usage in code:
     *   counterRepository.findByCounterIdAndUserId(counterId, currentUserId)
     *   .orElseThrow(() -> new Exception("Counter not found for user"));
     * 
     * Prevents user 1 from accessing user 2's counters.
     * See: controller/ApiController.findCounter() for usage
     */
    Optional<Counter> findByCounterIdAndUserId(Long counterId, Long userId);

    /**
     * Find all counters for a user
     * 
     * Generated SQL: SELECT * FROM counters WHERE user_id = ?
     * 
     * Basic operation: "Show me all my counters"
     * 
     * Always scoped to userId - never returns another user's counters!
     */
    List<Counter> findByUserId(Long userId);

    /**
     * Find counters where value >= threshold (for a specific user)
     * 
     * Generated SQL: SELECT * FROM counters WHERE counter_value >= ? AND user_id = ?
     * 
     * Usage: "Show me all counters with value >= 10"
     * 
     * Comparison operators:
     * - GreaterThan: >
     * - GreaterThanEqual: >=
     * - LessThan: <
     * - LessThanEqual: <=
     * 
     * Not used in this app, but available for queries like:
     * "Show me my counters with value between 5 and 20"
     */
    List<Counter> findByCounterValueGreaterThanEqualAndUserId(Long counterValue, Long userId);

    /**
     * Find counters where value <= threshold (for a specific user)
     * 
     * Generated SQL: SELECT * FROM counters WHERE counter_value <= ? AND user_id = ?
     * 
     * Usage: "Show me all counters with value <= 50"
     */
    List<Counter> findByCounterValueLessThanEqualAndUserId(Long counterValue, Long userId);

    /**
     * Find counters with exact value (for a specific user)
     * 
     * Generated SQL: SELECT * FROM counters WHERE counter_value = ? AND user_id = ?
     * 
     * Usage: "Find all my counters with exactly 42 as value"
     * (Probably not useful in practice, but shows the pattern)
     */
    List<Counter> findByCounterValueAndUserId(Long counterValue, Long userId);

    /**
     * Find counters by partial name match (case-sensitive, for a specific user)
     * 
     * Generated SQL: SELECT * FROM counters WHERE counter_name LIKE %?% AND user_id = ?
     * 
     * Usage: "Show me my counters with 'push' in the name"
     * 
     * Case-sensitive on most databases.
     * Controller handles case-insensitive search in memory
     * (see: ApiController.listCounters() -> matchesName()).
     */
    List<Counter> findByCounterNameContainingAndUserId(String counterName, Long userId);
}
