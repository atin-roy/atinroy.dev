# Setup New Next.js Project

Create a new Next.js project with TypeScript, App Router, and best practices.

## What This Does
1. Initializes Next.js with App Router (not Pages Router - explain the difference if asked)
2. Configures TypeScript in strict mode (catches more errors at compile time)
3. Sets up pnpm (faster, more efficient than npm/yarn due to content-addressable storage)
4. Adds recommended project structure

## Command
```bash
# In your monorepo apps/ directory
cd apps
pnpm create next-app@latest <project-name> \
  --typescript \
  --app \
  --no-src-dir \
  --import-alias "@/*" \
  --use-pnpm

# Explain: 
# --app = App Router (React Server Components, nested layouts)
# --no-src-dir = Keep app/ at root for clarity
# --import-alias = Use @/ for absolute imports (cleaner than ../../..)
```

## Post-Setup Checklist
- [ ] Update `tsconfig.json` to extend from workspace root
- [ ] Add to `pnpm-workspace.yaml` if in monorepo
- [ ] Configure path aliases in `tsconfig.json`
- [ ] Set up `.env.local` for environment variables

## Teaching Notes
**Why App Router over Pages Router?**
- Server Components by default (better performance, smaller bundle)
- Nested layouts (DRY principle - shared UI without prop drilling)
- Built-in data fetching patterns (no more getServerSideProps/getStaticProps confusion)

**Trade-off:** Steeper learning curve, but more scalable architecture.
