# Code Review Protocol

Please review the selected code (or current file) acting as a Senior Engineer.

## Focus Areas

1. **Logic & Edge Cases:** Are there off-by-one errors? Null pointer risks? Race conditions?
2. **Performance:** specific Big O analysis. Can we use a more efficient data structure?
3. **Security:** SQL injection? XSS? Unsanitized inputs?
4. **Readability:** Variable naming (is it descriptive?), function length.

## Output Format

- **Summary:** A 1-sentence high-level summary.
- **Critical Issues:** (If any) - Stop ship items.
- **Suggestions:** Concrete refactors.
- **Nitpicks:** Formatting/Styling.

## Constraints

- Be blunt.
- Do NOT rewrite the code unless explicitly asked. Just point out the flaws.
- If the code is actually good, say "LGTM" and move on.
