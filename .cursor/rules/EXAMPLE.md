# Example Rule File

This is an **example** of how context-specific rules work. You don't need this currently since you prefer consistent AI behavior.

## When to Use Context-Specific Rules

Create separate rule files when different parts of your codebase require:
- Different frameworks (React vs Spring Boot)
- Different coding standards
- Different levels of explanation detail
- Language-specific preferences

## Example: Frontend-Specific Rules

**File**: `.cursor/rules/frontend.md`

```markdown
# Frontend Rules (Next.js/React)

When working in `apps/web/`:

## Component Patterns
- Default to Server Components unless interactivity needed
- Extract reusable components to `packages/ui`
- Use Tailwind classes, avoid inline styles

## Data Fetching
- Server Components: Direct fetch with caching
- Client Components: Use tanstack/query
- Always type API responses

## Testing Reminders
- Test user interactions, not implementation
- Use React Testing Library
- Mock external API calls
```

## Example: Backend-Specific Rules

**File**: `.cursor/rules/backend.md`

```markdown
# Backend Rules (Spring Boot)

When working in Spring Boot projects:

## Architecture
- Controller → Service → Repository (no shortcuts)
- Always use constructor injection
- Prefer records for DTOs

## Security Reminders
- Never log sensitive data
- Validate all inputs
- Use parameterized queries (JPA does this)

## Testing
- Unit test services with mocked repositories
- Integration tests for controllers
```

## How Rules Are Applied

Rules can be scoped by:
1. **File path patterns**: `apps/web/**/*.tsx`
2. **File types**: `*.java`, `*.ts`
3. **Specific directories**: `infrastructure/`

Cursor automatically applies relevant rules based on the file you're working in.

## Your Current Setup

Since you prefer consistent behavior, you're using:
- **Root `.cursorrules`**: Global teaching-first philosophy
- **`.cursor/commands/`**: Quick reference (not behavior changes)

This gives you maximum flexibility without context fragmentation.

---

**Note**: If you later want to add context-specific rules, delete this example file and create real ones!
