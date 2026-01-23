import java.util.*;

public class LC_0748_ShortestCompletingWord {
    public static void main(String[] args) {
        var testCases = List.of(
                new TestCase("0gEu755", new String[] { "enough", "these", "play", "wide", "wonder", "box", "arrive",
                        "money", "tax", "thus" }, "enough"));

        var s = new Solution();
        int passed = 0;
        for (int i = 0; i < testCases.size(); i++) {
            var t = testCases.get(i);
            System.out.println("Test Case " + (i + 1) + ":");
            System.out
                    .println("  Input: licensePlate = \"" + t.licensePlate + "\", words = " + Arrays.toString(t.words));
            System.out.println("  Expected: \"" + t.expected + "\"");

            String result = s.shortestCompletingWord(t.licensePlate, t.words);
            System.out.println("  Got:      \"" + result + "\"");

            boolean isPass = result != null && result.equals(t.expected);
            System.out.println("  Status:   " + (isPass ? "✓ PASS" : "✗ FAIL"));
            if (isPass)
                passed++;
            System.out.println("---");
        }

        System.out.println("Results: " + passed + "/" + testCases.size() + " passed");
    }

    // PASTE YOUR LEETCODE SOLUTION HERE
    static class Solution {
        public String shortestCompletingWord(String licenseP, String[] words) {
            char[] arr = licenseP.toCharArray();
            Arrays.sort(arr);
            String licensePlate = new String(arr);

            int minLength = Integer.MAX_VALUE;
            String result = "";
            for (int i = 0; i < words.length; i++) {
                arr = words[i].toCharArray();
                Arrays.sort(arr);
                String word = new String(arr);
                int wp = 0, lp = 0;
                while (wp < word.length() && lp < licensePlate.length()) {
                    char wordCh = word.charAt(wp);
                    char plateCh = licensePlate.charAt(lp);
                    if (!Character.isLetter(wordCh)) {
                        wp++;
                        continue;
                    }
                    if (!Character.isLetter(plateCh)) {
                        lp++;
                        continue;
                    }
                    if (Character.toLowerCase(wordCh) == Character.toLowerCase(plateCh))
                        lp++;
                    wp++;
                }
                if (lp == licensePlate.length() && minLength > word.length()) {
                    minLength = word.length();
                    result = words[i];
                }
            }
            return result;
        }
    }

    record TestCase(String licensePlate, String[] words, String expected) {
    }
}
