# Cursor Configuration Overview

Complete guide to your Cursor AI setup.

## 📁 Structure

```
.cursor/
├── commands/              # Quick reference guides (syntax, patterns)
│   ├── README.md         # How to use commands
│   ├── setup-nextjs.md
│   ├── setup-springboot.md
│   ├── css-reference.md
│   ├── ts-js-reference.md
│   ├── nextjs-reference.md
│   ├── spring-reference.md
│   ├── python-reference.md
│   ├── bash-reference.md
│   ├── monorepo-tasks.md
│   └── git-workflow.md
│
├── rules/                 # Context-specific AI behavior (currently unused)
│   └── EXAMPLE.md        # Template for future use
│
└── OVERVIEW.md           # This file

.cursorrules              # Global AI behavior (teaching-first philosophy)
.cursorignore            # Files to exclude from AI context
```

## 🎯 Your Configuration Philosophy

Based on your `.cursorrules`:

### Core Principles

1. **TEACH, NOT SOLVE**: AI explains root causes before solutions
2. **SOCRATIC METHOD**: Asks clarifying questions, points to concepts
3. **CODE QUALITY**: Type safety, PEP8, DI best practices
4. **EXPLAIN WHY**: Always include trade-offs and reasoning
5. **BASH SAFETY**: Explain every flag in scripts

### Tech Stack

- Frontend: Next.js (App Router), TypeScript, pnpm
- Backend: Spring Boot (Java 21+)
- Scripting: Python, Bash

## 🚀 Quick Start

### Using Commands

```
# In Cursor chat:
"Show me /nextjs-reference for data fetching"
"I forgot CSS Grid, use /css-reference to help me"
"Set up a new Spring Boot project with /setup-springboot"
```

### Common Workflows

#### Starting a New Next.js Project

```bash
# Reference: /setup-nextjs
cd apps/
pnpm create next-app@latest my-app --typescript --app --use-pnpm
```

#### Forgetting TypeScript Utility Types

```
Ask AI: "What utility types exist for objects? Use /ts-js-reference"
```

#### Safe Bash Scripting

```
Ask AI: "Explain this bash script using /bash-reference safety patterns"
```

## 📚 Command Categories

### Project Setup

- `/setup-nextjs` - Next.js with TypeScript + App Router
- `/setup-springboot` - Spring Boot with Java 21+

### Language References

- `/ts-js-reference` - Modern JS/TS features
- `/python-reference` - Python 3.10+ patterns
- `/bash-reference` - Safe shell scripting

### Framework References

- `/nextjs-reference` - App Router, RSC, Server Actions
- `/spring-reference` - Annotations, JPA, validation
- `/css-reference` - Flexbox, Grid, modern CSS

### Workflow Tools

- `/monorepo-tasks` - pnpm workspace commands
- `/git-workflow` - Safe git practices

## 🎓 Learning-First Features

Every command includes:

✅ **Syntax Examples**: Copy-paste ready code  
✅ **Why Explanations**: Understand the reasoning  
✅ **Trade-offs**: Performance/complexity costs  
✅ **When to Use**: Context for application  
✅ **Alternatives**: Different approaches

## 🔧 Customization

### Adding New Commands

1. Create file in `.cursor/commands/`:

```bash
touch .cursor/commands/my-command.md
```

2. Follow the template:

```markdown
# Command Title

What this helps with.

## Pattern

\`\`\`language
code
\`\`\`

**Why?** Explanation
**Trade-off:** Costs
```

3. Use in chat:

```
"Help me with X using /my-command"
```

### Adding Context Rules (Future)

If you want different AI behavior for different code areas:

1. Create rule file:

```bash
# Example: Different behavior in frontend vs backend
touch .cursor/rules/frontend.md
touch .cursor/rules/backend.md
```

2. Define scope and behavior in the file

## 💡 Pro Tips

1. **Reduce Context Switching**: Ask AI to reference commands instead of Googling
2. **Update as You Learn**: Add patterns you discover to commands
3. **Combine Commands**: "Use /nextjs-reference and /ts-js-reference to build a form"
4. **Ask for Explanations**: "Why does /bash-reference recommend set -euo pipefail?"

## 🎯 Productivity Benefits

### Before Commands

```
You: "How do I do async/await in Python?"
→ Open browser
→ Search docs
→ Read through examples
→ Return to IDE
→ Lost context
```

### With Commands

```
You: "Show Python async/await from /python-reference"
AI: [Immediately shows patterns with explanations]
→ Stay in flow state
```

## 🔄 Maintenance

### Weekly

- [ ] Add new patterns you learned
- [ ] Update version-specific notes

### Monthly

- [ ] Review for outdated patterns
- [ ] Add pain points you encountered

### On Tech Stack Updates

- [ ] Update commands with new features
- [ ] Mark deprecated patterns

## 📊 Command Coverage

Your current setup covers:

| Area        | Coverage                                |
| ----------- | --------------------------------------- |
| Next.js     | ✅ Setup, routing, RSC, data fetching   |
| TypeScript  | ✅ Types, utilities, async patterns     |
| Spring Boot | ✅ Setup, annotations, JPA, validation  |
| Python      | ✅ Modern features, type hints, async   |
| CSS         | ✅ Flexbox, Grid, positioning, modern   |
| Bash        | ✅ Safety patterns, common idioms       |
| Git         | ✅ Safe workflows, conventional commits |
| Monorepo    | ✅ pnpm workspace management            |

## 🚦 Next Steps

1. **Try a command**: Ask AI to use one in your next question
2. **Add your patterns**: Create a custom command for project-specific patterns
3. **Share with team**: These commands work as onboarding docs
4. **Iterate**: Update as you discover what you actually forget

---

**Remember**: These tools exist to **amplify your productivity**, not replace your learning. They're your external memory, freeing mental space for complex problem-solving.

Questions? Ask AI: "Explain how I should use the .cursor folder effectively"

# Models to use

### 1. The "Daily Driver" (Code Generation & Autocomplete)

**🏆 Model:** `Grok Code`

- **Cost:** `$0.20` (Input) / `$1.50` (Output)
- **Why:** It is the **cheapest model on the entire list**. For raw code generation, refactoring functions, or writing boilerplate, this is your workhorse. It costs 15x less than Claude 4 Sonnet.

### 2. General Chat & Debugging ("How do I...?" questions)

**🏆 Model:** `GPT-5 Mini`

- **Cost:** `$0.25` (Input) / `$2.00` (Output)
- **Why:** It beats Gemini 2.5 Flash slightly on price and is likely better tuned for general instruction following than Grok. Use this for explaining concepts, writing documentation, or basic logic checks.

### 3. The "Smart" Model (Complex Architecture & Hard Bugs)

**🏆 Model:** `Claude 4.5 Haiku`

- **Cost:** `$1.00` (Input) / `$5.00` (Output)
- **Why:** This is the "Value King." It is significantly smarter than the "Mini/Flash" models but costs **half as much** as GPT-5 or Composer 1, and **one-third** the price of Sonnet. Use this when the cheap models get stuck.

### 4. Heavy Context (Reading massive docs)

**🏆 Model:** `Grok Code` or `GPT-5 Mini`

- **Cost (Cache Read):** `$0.02` (Grok) / `$0.025` (Mini)
- **Why:** If you are using `@Docs` to read those massive libraries you just indexed, you pay for "Cache Read".
- **Claude 4 Sonnet** charges `$0.30` for cache reads.
- **Grok Code** charges `$0.02`.
- **Result:** Using Grok/Mini to read docs is **15x cheaper** than using Sonnet.

### Summary Configuration

| Use Case           | Recommended Model  | Cost Estimate |
| ------------------ | ------------------ | ------------- |
| **Coding / Tab**   | `Grok Code`        | 📉 Lowest     |
| **Chat / Q&A**     | `GPT-5 Mini`       | 📉 Very Low   |
| **Complex Logic**  | `Claude 4.5 Haiku` | ⚖️ Medium     |
| **Emergency Only** | `Claude 4 Sonnet`  | 💰 Highest    |
