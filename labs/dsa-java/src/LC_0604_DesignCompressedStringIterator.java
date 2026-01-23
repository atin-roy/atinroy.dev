import java.util.ArrayDeque;
import java.util.Queue;

class StringIterator {
    char[] str;
    Queue<Integer> queue;
    int index;
    int count;

    public StringIterator(String compressedString) {
        str = compressedString.toCharArray();
        queue = new ArrayDeque<>();
        index = -1;

        for (int i = 0; i < str.length; i++) {
            int count = 0;
            while (i < str.length && Character.isDigit(str[i])) {
                count *= 10;
                count += str[i] - '0';
                str[i] = ' ';
                i++;
            }
            if (count > 0) {
                queue.offer(count);
                i--;
            }
        }
        if (!queue.isEmpty())
            count = queue.poll();
        else
            count = 0;
    }

    public char next() {
        char next = str[index + 1];
        count--;
        if (count == 0) {
            str[index + 1] = ' ';
            while (index + 1 < str.length && str[index + 1] == ' ') {
                index++;
            }
            if (!queue.isEmpty())
                count = queue.poll();
        }
        return next;
    }

    public boolean hasNext() {
        if (queue.isEmpty() && count == 0)
            return false;
        return true;
    }
}

/**
 * Your StringIterator object will be instantiated and called as such:
 * StringIterator obj = new StringIterator(compressedString);
 * char param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
/**
 * Your StringIterator object will be instantiated and called as such:
 * StringIterator obj = new StringIterator(compressedString);
 * char param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */

// Test the implementation
class LC_0604_DesignCompressedStringIterator {
    public static void main(String[] args) {
        // Basic test case
        test("L1e2t1C1o1d1e1", "LeetCode");

        // Edge cases
        test("", ""); // Empty string

        test("a1", "a"); // Single character with count 1

        test("a5", "aaaaa"); // Single character with count > 1

        test("a10", "aaaaaaaaaa"); // Single character with larger count

        test("a2b2c2", "aabbcc"); // Multiple characters with same count

        test("x1y1z1", "xyz"); // Multiple characters each with count 1

        test("A100", "A".repeat(100)); // Very large count (100)

        test("b3c1d4", "bbbcdddd"); // Mixed counts

        test("Z1", "Z"); // Single uppercase letter

        test("m2n2", "mmnn"); // Lowercase letters

        test("p1q3r1", "pqqqr"); // Mixed single and multiple counts

        test("s10t1u2", "s".repeat(10) + "tu"); // Large count followed by small counts

        test("v1", "v"); // Minimal case

        test("w999", "w".repeat(999)); // Very large count (999)

        // Test hasNext behavior
        StringIterator emptyIter = new StringIterator("");
        System.out.println("Empty iterator hasNext(): " + emptyIter.hasNext()); // Should be false

        StringIterator singleIter = new StringIterator("x1");
        System.out.println("Single char iterator hasNext() before: " + singleIter.hasNext()); // Should be true
        singleIter.next();
        System.out.println("Single char iterator hasNext() after: " + singleIter.hasNext()); // Should be false
    }

    private static void test(String input, String expected) {
        StringIterator iterator = new StringIterator(input);
        StringBuilder result = new StringBuilder();

        while (iterator.hasNext()) {
            result.append(iterator.next());
        }

        boolean passed = result.toString().equals(expected);
        System.out.println("Test \"" + input + "\": " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            System.out.println("  Expected: \"" + expected + "\", Got: \"" + result + "\"");
        }
    }
}