# Cursor Commands & Rules

This folder contains custom commands and AI behavior rules for your development workflow.

## 📋 What's Here

### Commands (Quick Reference Guides)
These are slash commands you can reference in Cursor chat. They provide instant syntax reminders and best practices.

| Command | Description |
|---------|-------------|
| `/setup-nextjs` | Scaffold new Next.js project with TypeScript + pnpm |
| `/setup-springboot` | Create Spring Boot project with Java 21+ |
| `/css-reference` | Flexbox, Grid, positioning patterns |
| `/ts-js-reference` | TypeScript/JavaScript modern features |
| `/nextjs-reference` | Next.js App Router patterns |
| `/spring-reference` | Spring Boot annotations & patterns |
| `/python-reference` | Python 3.10+ features (PEP8) |
| `/bash-reference` | Bash scripting with safety flags |
| `/monorepo-tasks` | pnpm workspace commands |
| `/git-workflow` | Git best practices & safe commands |

### Rules (AI Behavior)
The `rules/` folder can contain context-specific AI behavior. Since you prefer consistent behavior across your codebase, this is currently empty. You can add rules later if needed.

## 🚀 How to Use Commands

### Method 1: Type in Chat
```
Hey, show me /nextjs-reference for Server Actions
```

The AI will reference the command file and explain the pattern.

### Method 2: Direct Reference
```
According to /bash-reference, what does set -euo pipefail do?
```

### Method 3: Ask for Examples
```
I forgot CSS Grid syntax. Help me using /css-reference
```

## 🎯 Design Philosophy

These commands follow your `.cursorrules` principle: **TEACH, NOT JUST SOLVE**

Each reference includes:
- ✅ **What**: Syntax and patterns
- ✅ **Why**: Explanation of the approach
- ✅ **Trade-offs**: Performance/complexity costs
- ✅ **Context**: When to use (and when not to)

## 📝 Adding New Commands

Create a new `.md` file in `.cursor/commands/`:

```markdown
# Command Title

Brief description of what this helps with.

## Pattern Name
\`\`\`language
code example
\`\`\`

**Why?** Explanation of the approach.
**Trade-off:** What are the costs?

## Next Pattern
...
```

## 🔧 Adding Context-Specific Rules

If you want different AI behavior for different parts of your codebase:

```
.cursor/rules/
├── frontend.md       → Apply to apps/web/**
├── backend.md        → Apply to backend/**
└── infrastructure.md → Apply to infrastructure/**
```

Example rule file:
```markdown
# Frontend Rules

When working in the frontend:
- Always use React Server Components by default
- Remind me to use TypeScript strict mode
- Suggest tanstack/query for data fetching
```

## 💡 Productivity Tips

1. **Keep commands updated**: When you learn new patterns, add them!
2. **Link related commands**: Reference other commands in explanations
3. **Add your pain points**: If you forget something often, add it
4. **Version-specific notes**: Mark which features require specific versions

## 🎓 Teaching-First Approach

When you ask AI for help:
- ❌ "Fix this bug" → AI gives solution immediately
- ✅ "Why is this failing?" → AI explains root cause first
- ✅ "How should I approach X?" → AI asks clarifying questions

These commands support **self-sufficiency** - they're your external memory, not crutches.

## 🔄 Maintenance

Review these commands periodically:
- When tech stack versions update
- When you discover better patterns
- When new team members join (onboarding resource)

---

**Remember**: These commands reduce cognitive load so you can focus on **problem-solving**, not **syntax recall**.
