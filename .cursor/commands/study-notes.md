name: "Create Study Notes"
description: "Generate comprehensive study notes with examples, best practices, and cheatsheets"
prompt: Create comprehensive study notes for the topic: {topic}

## Instructions:

1. Analyze the topic and determine the most appropriate folder from this list:
   - Artificial Intelligence
   - Computer Networks
   - Data Science
   - Database Management Systems
   - Design & Analysis of Algorithms
   - DSA
   - Java
   - Math
   - Operating Systems
   - Web Development

2. Create a markdown file at: `~/code/knowledge/the-cognitive-realm/Atlas/Dots/<appropriate-folder>/<topic-name>.md`

3. Use this structure and make it COMPREHENSIVE with real, working examples:

# {Topic Name}

## 1. Overview (What & Why)

**What is it?**

- Clear, concise definition
- Core purpose and functionality
- Historical context if relevant

**Why does it matter?**

- Real-world importance
- Problem it solves
- Industry relevance

**Use cases:**

- Practical applications (at least 3-5)
- When to use vs when not to use

---

## 2. Key Concepts

### Core Principles

- **Concept 1**: Detailed explanation with analogies
- **Concept 2**: How it works fundamentally
- **Concept 3**: Relationships to other concepts

### Important Terminology

- **Term 1**: Definition + context
- **Term 2**: Definition + context
- **Term 3**: Definition + context

### Mental Models

- Visual/conceptual ways to think about this topic
- Analogies that make complex ideas simple

---

## 3. Code Examples

### Example 1: Hello World / Basic Implementation

```language
// Actual working code with comments
// Should be copy-paste runnable
```

**Explanation:**

- Line-by-line breakdown
- Why each part matters

**Output:**

```
Expected output here
```

### Example 2: Intermediate Usage

```language
// More complex but still clear example
```

**Explanation:**

- Key differences from basic example
- New concepts introduced

### Example 3: Advanced/Production Pattern

```language
// Real-world pattern with error handling
```

**Explanation:**

- Best practices demonstrated
- Edge cases handled

### Common Pitfalls

```language
// ❌ WRONG - Don't do this
```

```language
// ✅ RIGHT - Do this instead
```

---

## 4. Under the Hood

### How it works in memory

- Memory allocation patterns
- Stack vs heap considerations
- Memory footprint

### CPU/Processing considerations

- How the processor handles this
- Cache efficiency
- Parallelization possibilities

### Time & Space Complexity

- **Time Complexity**: O(?) - with explanation
- **Space Complexity**: O(?) - with explanation
- **Best/Average/Worst cases**: When they occur

### Internal mechanisms

- What happens at the system level
- Compiler/interpreter behavior
- OS interactions if relevant

### Performance Tips

- How to optimize
- What to avoid
- Benchmarking considerations

---

## 5. Best Practices & Patterns

### Do's ✅

- Best practice 1 with rationale
- Best practice 2 with rationale
- Best practice 3 with rationale

### Don'ts ❌

- Anti-pattern 1 and why to avoid
- Anti-pattern 2 and why to avoid
- Anti-pattern 3 and why to avoid

### Design Patterns

- Relevant design patterns for this topic
- When to apply each pattern

---

## 6. Cheatsheet

### Quick Reference

```language
// Most common operations
operation1()  // Description
operation2()  // Description
operation3()  // Description
```

### Syntax Table

| Syntax | Description  | Example     |
| ------ | ------------ | ----------- |
| `code` | What it does | `example()` |

### Common Commands/Methods

- `method1()` - Does X
- `method2()` - Does Y
- `method3()` - Does Z

### Keyboard Shortcuts (if applicable)

- `Ctrl+X` - Action
- `Ctrl+Y` - Action

---

## 7. Troubleshooting & FAQs

### Common Errors

**Error 1: "Error message here"**

- **Cause**: Why it happens
- **Solution**: How to fix it

```language
// Fixed code
```

**Error 2: "Another error message"**

- **Cause**: Root cause
- **Solution**: Resolution steps

### Frequently Asked Questions

**Q: Common question?**
A: Detailed answer with examples

**Q: Another common question?**
A: Detailed answer

---

## 8. Related Topics & Further Learning

### Related Concepts

- [[Related Topic 1]] - How it connects
- [[Related Topic 2]] - Relationship
- [[Related Topic 3]] - Comparison

### Prerequisites

- What you should know before this
- Foundational concepts

### Next Steps

- What to learn after mastering this
- Advanced topics to explore

### Resources

- 📚 **Books**: Title - Author (why it's good)
- 🎥 **Videos**: Link/Channel - What it covers
- 📝 **Articles**: Title - Key takeaways
- 🔧 **Tools**: Tool name - Use case
- 💻 **Practice**: Platforms/exercises

---

## 9. Summary (TL;DR)

### Key Takeaways

1. Most important point
2. Second most important point
3. Third most important point
4. Critical gotcha or insight
5. Memorable quote or principle

### One-Liner

> {Topic} in one sentence

### When to use

- ✅ Scenario 1
- ✅ Scenario 2
- ❌ Not appropriate for scenario 3
- ❌ Not appropriate for scenario 4

### Comparison Table

| This Topic  | Alternative Approach |
| ----------- | -------------------- |
| Advantage 1 | Trade-off 1          |
| Advantage 2 | Trade-off 2          |

---

## 10. Practice Exercises

### Beginner

1. Exercise description
   - Expected outcome
   - Hint if needed

### Intermediate

1. More complex exercise
   - What to implement
   - Constraints

### Advanced

1. Challenging problem
   - Real-world scenario
   - Multiple approaches possible

---

**Tags:** #{folder-tag} #study-notes #{topic-tag} #{additional-relevant-tags}
**Created:** {current-date}
**Last Updated:** {current-date}
**Difficulty:** {Beginner/Intermediate/Advanced}
**Estimated Study Time:** {X hours}

---

## REQUIREMENTS:

- Use ACTUAL working code examples (no placeholders)
- Include at least 5 code examples
- Provide real command outputs where applicable
- Add visual diagrams using ASCII art or mermaid if helpful
- Make it comprehensive enough to learn the topic from scratch
- Include both theoretical and practical knowledge
- Add memorable mnemonics or mental models
- Cross-reference related topics with [[wiki-style]] links
- Use proper markdown formatting with code blocks specifying language
- Make the cheatsheet actually useful for quick reference
- Include version information if relevant
- Add warnings for deprecated features
- Specify compatibility/requirements

After creating the file, provide:

1. The full file path where it was created
2. A brief summary of what was covered
3. Suggested next topics to study
