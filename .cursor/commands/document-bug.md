name: "Document Bug"
description: "Create a detailed bug report after fixing an issue with AI help"
prompt: I just fixed a bug with your help. Create a comprehensive bug report so I don't forget this lesson.

## Context Analysis:

First, analyze the current project:

- Detect project type (Next.js/React, Spring Boot, Python, DSA problem)
- Review recent conversation history to understand the bug
- Identify the root cause and solution

## Bug Report Creation:

1. **Determine the next bug number:**
   - Check `/bugs/` directory for existing reports or create bugs directory if not present for current project
   - Find the highest number (e.g., if bug-05 exists, create bug-06)
   - Use format: `bug-XX-YYYY-MM-DD.md` (e.g., `bug-06-2026-01-15.md`)

2. **Create file at:** `bugs/bug-XX-YYYY-MM-DD.md`

3. **Use this comprehensive structure:**

---

# Bug Report #XX

**Date:** {YYYY-MM-DD}  
 **Time:** {HH:MM:SS}  
 **Project:** {Project Name/Type}  
 **Severity:** 🔴 Critical / 🟡 Medium / 🟢 Low  
 **Category:** {Logic Error / Syntax Error / Runtime Error / Performance / Memory Leak / Race Condition / etc.}

---

## 📋 Quick Summary

**One-liner:** {What went wrong in one sentence}

**The Mistake:** {What I did wrong}

**The Fix:** {What actually works}

---

## 🐛 The Bug

### What Happened

{Describe the observable behavior - what wasn't working}

**Expected Behavior:**

```
What should have happened
```

**Actual Behavior:**

```
What actually happened (with error messages if any)
```

### Error Messages

```
Full error stack trace or output here
```

### Context

- **File(s) Affected:** `path/to/file.ext`
- **Function/Method:** `functionName()`
- **Line Number(s):** Line XX-YY
- **What I Was Trying to Do:** {Goal/feature being implemented}

---

## 🔍 Root Cause Analysis

### Why It Happened

{Deep explanation of the root cause - why did this bug occur?}

### My Thought Process (Wrong)

1. I assumed {wrong assumption 1}
2. I thought {wrong assumption 2}
3. I didn't consider {missed consideration}

### What I Missed

- Overlooked: {what was overlooked}
- Didn't know: {knowledge gap}
- Misunderstood: {conceptual misunderstanding}

### Technical Details

{Explain the technical reason - memory, scope, async, type mismatch, etc.}

---

## ❌ Broken Code

```{language}
// The code that caused the bug
// With comments explaining what's wrong

function brokenExample() {
  // ❌ This is wrong because...
  const result = buggyOperation();
  return result;
}
```

**Why This Failed:**

- Point 1: Specific reason
- Point 2: Technical explanation
- Point 3: Edge case not handled

---

## ✅ Fixed Code

```{language}
// The corrected code
// With comments explaining why it works

function fixedExample() {
  // ✅ This works because...
  const result = correctOperation();
  return result;
}
```

**Why This Works:**

- Point 1: How it solves the problem
- Point 2: What changed
- Point 3: Edge cases now handled

### Key Changes

1. **Change 1:** {What was modified and why}
2. **Change 2:** {What was added and why}
3. **Change 3:** {What was removed and why}

---

## 🎓 What I Learned

### Technical Lessons

1. **Lesson 1:** {Specific technical concept learned}
   - Why it matters
   - When to apply it

2. **Lesson 2:** {Another technical insight}
   - Gotcha to remember
   - Related concepts

3. **Lesson 3:** {Third key takeaway}

### Conceptual Understanding

- **Before:** I thought {misconception}
- **After:** I now understand {correct understanding}

### Mental Model

{A way to remember or visualize this concept}

> 💡 **Key Insight:** {Most important takeaway in one sentence}

---

## 🎯 How to Avoid This in the Future

### Prevention Checklist

Before writing similar code, always:

- [ ] {Specific check 1}
- [ ] {Specific check 2}
- [ ] {Specific check 3}
- [ ] {Specific check 4}

### Warning Signs

Watch out for these red flags:

- 🚩 {Warning sign 1}
- 🚩 {Warning sign 2}
- 🚩 {Warning sign 3}

### Best Practices

1. **Always:** {What to always do}
2. **Never:** {What to never do}
3. **Remember:** {Important rule to follow}

### Code Review Questions

When reviewing similar code, ask:

- Q: {Question to ask yourself}
- Q: {Another important question}
- Q: {Third question}

---

## 📚 Campus Interview Prep

### If Asked in an Interview

**Question Pattern:**
"{How the interviewer might ask about this}"

**What to Say:**

1. Start with: {Opening explanation}
2. Explain the problem: {Clear problem statement}
3. Discuss approaches: {Different solutions}
4. Mention edge cases: {What to watch for}
5. Time/Space complexity: O(?) / O(?)

**Common Follow-ups:**

- Q: {Follow-up question 1}
  - A: {How to answer}
- Q: {Follow-up question 2}
  - A: {How to answer}

### Whiteboard Approach

```
Step 1: {How to start on whiteboard}
Step 2: {What to write next}
Step 3: {How to validate}
Step 4: {How to optimize}
```

### Key Talking Points

- Mention: {Important point to demonstrate knowledge}
- Highlight: {Show deeper understanding}
- Discuss: {Trade-offs or alternatives}

---

## 🧪 Test Cases

### Test That Would Have Caught This

```{language}
// Unit test that catches this bug
test('should handle {scenario}', () => {
  const input = {test input};
  const expected = {expected output};
  const actual = functionName(input);
  expect(actual).toBe(expected);
});
```

### Edge Cases to Test

1. **Edge Case 1:** {Description}
   - Input: `{input}`
   - Expected: `{output}`

2. **Edge Case 2:** {Description}
   - Input: `{input}`
   - Expected: `{output}`

3. **Edge Case 3:** {Description}
   - Input: `{input}`
   - Expected: `{output}`

---

## 🔗 Related Concepts

### Similar Bugs to Watch For

- [[Bug #XX]]: {Related bug if exists}
- Pattern: {Similar pattern that causes issues}

### Prerequisites Knowledge

- Understand: {Concept 1}
- Know about: {Concept 2}
- Familiar with: {Concept 3}

### Further Reading

- 📖 {Resource 1}: {Why it's relevant}
- 📖 {Resource 2}: {What it explains}
- 📄 {Documentation}: {Link/section}

---

## 🎪 Real-World Impact

### Why This Matters

- **In Production:** {What could happen in real app}
- **At Scale:** {How it affects performance/reliability}
- **For Users:** {Impact on user experience}

### Similar Bugs in Famous Projects

{If applicable, mention known bugs in popular projects that are similar}

---

## 🔖 Tags & Metadata

**Tags:** #{project-type} #{bug-category} #{language} #{concept-involved}  
 **Difficulty:** {Beginner/Intermediate/Advanced}  
 **Time Spent Debugging:** {X minutes/hours}  
 **Times Encountered:** 1  
 **Last Reviewed:** {YYYY-MM-DD}

**Related Files:**

- `{file1.ext}`
- `{file2.ext}`

**Git Commit:** `{commit hash if applicable}`

---

## 💭 Personal Notes

### How I Felt

{Frustration level, confidence after fix, etc.}

### Debug Strategy Used

{What debugging approach worked - console.log, debugger, testing, etc.}

### Time Analysis

- Time stuck: {X minutes}
- Time to find cause: {Y minutes}
- Time to implement fix: {Z minutes}

### Would I Remember This?

**Before Bug Report:** ❌ Probably would forget  
 **After Bug Report:** ✅ Have reference to review

---

## 🔄 Review Schedule

- [ ] Review in 1 day ({date})
- [ ] Review in 1 week ({date})
- [ ] Review in 1 month ({date})
- [ ] Review before interviews

---

## ⚡ Quick Reference Card

```
╔═══════════════════════════════════════╗
║  BUG #{XX}: {SHORT TITLE}            ║
╠═══════════════════════════════════════╣
║  ❌ Wrong: {one-liner wrong way}     ║
║  ✅ Right: {one-liner right way}     ║
╠═══════════════════════════════════════╣
║  Remember: {Key point to never forget}║
╚═══════════════════════════════════════╝
```

## REQUIREMENTS FOR AI:

- Analyze the conversation history to extract exact code and error messages
- Be SPECIFIC - use actual variable names, function names, line numbers
- Include the ACTUAL error message, not a generic one
- Make the "How to Avoid" section practical and actionable
- Tailor the interview prep to the actual bug type
- Create test cases that would have caught THIS specific bug
- Number the bug report correctly (check existing bugs directory)
- Use appropriate emojis for quick visual scanning
- Make it searchable with good tags
- Keep it honest - if it was a stupid mistake, say so
- Include time estimates for interview scenarios
- Add complexity analysis if it's a DSA problem
- Make the quick reference card actually useful for last-minute review

After creating the report, provide:

1. The full file path
2. Bug number assigned
3. One-sentence summary of the bug
4. Estimated time to review this report: {X minutes}
5. Suggested review dates
6. Related topics to study to prevent similar bugs
