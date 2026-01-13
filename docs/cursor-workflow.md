# 🚀 The "Anti-Crutch" Learning Protocol

**Role:** Senior Engineer Mentor (Ruthless but educational)
**Goal:** Mastery over Speed. Understanding over Copy-Pasting.

## 🟢 Phase 1: The Scaffolding (Automate the Boring)

_Use Cursor (Composer / Ctrl+I) ONLY for setup._

- "Scaffold a standard Next.js project with pnpm and TypeScript."
- "Create a basic folder structure for a Spring Boot service."
- **Rule:** Do not let AI write business logic or algorithms here. Only boilerplate.

## 🟡 Phase 2: The Struggle (Manual Mode)

_Cursor is BANNED from writing code here._

1. **Read Docs:** Use `@Docs` in Chat (Ctrl+L) to find correct syntax.
   - _Prompt:_ "Summarize the @Next.js Docs for Server Actions vs API routes. Don't write code, just explain the concepts."
2. **Write Code:** Implement the feature yourself.
   - If it breaks: Read the error. Try to fix it.
   - If stuck > 15 mins: Use Chat (Ctrl+L) -> "Here is my error. Give me a hint, NOT the solution."

## 🔴 Phase 3: The Review (The Roast)

_This is where the learning happens. Do this AFTER the feature works._

1. Highlight your code block.
2. Open Chat (Ctrl+L) or Edit (Ctrl+K).
3. **The Magic Prompt:**
   > "I have implemented this feature. It works, but I want to learn.
   >
   > 1. Roast this code. What is inefficient?
   > 2. Show me the 'Senior Dev' version of this using modern syntax (e.g., Python list comps, Java Streams, TS Generics).
   > 3. Explain WHY your version is better (memory, speed, or readability)."

## 🔵 Phase 4: The Optimization (The Polish)

_Apply the specific learnings._

- Re-write your code based on the feedback (don't just "Apply" the diff—type it out to build muscle memory).
- Ask: "How would this scale if we had 1 million users?" (Great for Spring Boot/SQL optimization learning).

## 💸 Budget Control (The $20 Limit)

- **Complex Logic / Review:** Use `Claude 3.5 Sonnet` (Expensive, Smart).
- **Simple Explanations / Bash Scripts:** Use `Cursor Small` or `GPT-4o` (Cheaper, Faster).
- **Toggle:** Switch model in the Chat dropdown to save 'Fast Requests' for the hard stuff.
