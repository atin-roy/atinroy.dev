package dev.atinroy.dsa;

/**
 * Sample solution class demonstrating the setup.
 * 
 * Problem: Two Sum
 * Given an array of integers and a target, return indices of two numbers that add up to target.
 */
public class Solution {
    
    /**
     * Finds two indices whose values sum to the target.
     * 
     * @param nums array of integers
     * @param target the target sum
     * @return array of two indices, or empty array if not found
     */
    public int[] twoSum(int[] nums, int target) {
        // Brute force approach for demonstration
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    /**
     * Main method for quick manual testing.
     * Run with: mvn exec:java
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        
        int[] result = solution.twoSum(nums, target);
        
        if (result.length == 2) {
            System.out.printf("Found indices: [%d, %d]%n", result[0], result[1]);
            System.out.printf("Values: nums[%d] = %d, nums[%d] = %d%n", 
                result[0], nums[result[0]], result[1], nums[result[1]]);
            System.out.printf("Sum: %d + %d = %d%n", 
                nums[result[0]], nums[result[1]], nums[result[0]] + nums[result[1]]);
        } else {
            System.out.println("No solution found");
        }
    }
}
