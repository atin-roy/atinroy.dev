package dev.atinroy.dsa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.*;

/**
 * Test suite for Solution class demonstrating JUnit 5 + AssertJ setup.
 */
@DisplayName("Two Sum Problem")
class SolutionTest {
    
    private Solution solution;
    
    @BeforeEach
    void setUp() {
        solution = new Solution();
    }
    
    @Nested
    @DisplayName("Basic Cases")
    class BasicCases {
        
        @Test
        @DisplayName("Should find two numbers that sum to target")
        void shouldFindTwoNumbersThatSumToTarget() {
            // Given
            int[] nums = {2, 7, 11, 15};
            int target = 9;
            
            // When
            int[] result = solution.twoSum(nums, target);
            
            // Then
            assertThat(result)
                .hasSize(2)
                .containsExactly(0, 1);
            assertThat(nums[result[0]] + nums[result[1]])
                .isEqualTo(target);
        }
        
        @Test
        @DisplayName("Should work with different indices")
        void shouldWorkWithDifferentIndices() {
            // Given
            int[] nums = {3, 2, 4};
            int target = 6;
            
            // When
            int[] result = solution.twoSum(nums, target);
            
            // Then
            assertThat(result).containsExactly(1, 2);
        }
        
        @Test
        @DisplayName("Should work with same values at different indices")
        void shouldWorkWithSameValues() {
            // Given
            int[] nums = {3, 3};
            int target = 6;
            
            // When
            int[] result = solution.twoSum(nums, target);
            
            // Then
            assertThat(result).containsExactly(0, 1);
        }
    }
    
    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {
        
        @Test
        @DisplayName("Should handle negative numbers")
        void shouldHandleNegativeNumbers() {
            // Given
            int[] nums = {-1, -2, -3, -4, -5};
            int target = -8;
            
            // When
            int[] result = solution.twoSum(nums, target);
            
            // Then
            assertThat(nums[result[0]] + nums[result[1]])
                .isEqualTo(target);
        }
        
        @Test
        @DisplayName("Should return empty array when no solution exists")
        void shouldReturnEmptyWhenNoSolution() {
            // Given
            int[] nums = {1, 2, 3};
            int target = 10;
            
            // When
            int[] result = solution.twoSum(nums, target);
            
            // Then
            assertThat(result).isEmpty();
        }
    }
}
