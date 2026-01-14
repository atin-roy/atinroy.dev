# Next.js Monorepo Workflow (pnpm)

This document defines **how Next.js projects work** in this monorepo, **how to add dependencies**, and **how to handle external starter projects** (Udemy, GitHub templates, etc.).

Applies to both `apps/` (production) and `labs/` (learning).

The goal: **modern tooling, deterministic installs, zero chaos**.

---

## Core Principles

- **One package manager**: pnpm only
- **One lockfile**: `pnpm-lock.yaml` at repo root
- **All installs run from repo root**
- **Labs are isolated, but share infrastructure**
- **Latest stable versions by default**

Breaking these rules leads to dependency drift and pain.

---

## Folder Structure

```
atinroy.dev/
├─ package.json
├─ pnpm-workspace.yaml
├─ pnpm-lock.yaml
├─ node_modules/.pnpm/           # actual dependency store
├─ apps/
│  └─ web/                       # production frontend
│     ├─ package.json
│     ├─ src/
│     └─ node_modules/           # symlinks (not real installs)
├─ labs/
│  ├─ learn-data-mutation/       # Next.js learning
│  │  ├─ package.json
│  │  └─ node_modules/
│  └─ notes-project/frontend/    # Next.js experiment
│     ├─ package.json
│     └─ node_modules/
└─ docs/
```

Each project **appears** to have its own `node_modules`, but pnpm stores dependencies once and links them.

---

## Adding a New Next.js Project

### For labs (learning projects)

1. Create the folder:

```bash
labs/my-next-app/
```

2. Add a `package.json` to the lab.

3. Install dependencies **from repo root**:

```bash
pnpm install
```

The `pnpm-workspace.yaml` already includes `labs/**`.

### For apps (production projects)

Same process, but place in `apps/` instead. These are projects you stand behind.

---

## Copying a Udemy / External Next.js Starter

### Step 1: Copy files only

Copy the project into:

```
labs/udemy-project/
```

Do **not** install dependencies yet.

---

### Step 2: Do NOT run installs inside the lab

Never do:

```bash
cd labs/udemy-project
npm install        ❌
pnpm install       ❌
```

This breaks the monorepo contract.

---

### Step 3: Decide version strategy

Most starters ship with older versions.

#### Option A — Preserve course versions

Leave dependencies as-is in the lab’s `package.json`.

Use when:

- Course relies on deprecated APIs
- You want historical accuracy

pnpm will isolate this lab safely.

---

#### Option B — Upgrade to latest (recommended)

Edit the lab’s `package.json`:

```json
"dependencies": {
  "next": "^15",
  "react": "^19",
  "react-dom": "^19"
}
```

Then, from repo root:

```bash
pnpm install
```

If things break, that’s expected—and educational.

---

### Step 4: Run the project

From repo root:

```bash
pnpm --filter udemy-project dev
```

Alternative:

```bash
pnpm -C labs/udemy-project dev
```

For production apps:

```bash
pnpm --filter web dev
```

---

## Adding Dependencies to a Specific Project

Always run from **repo root**.

### Add a dependency

```bash
pnpm --filter web add axios              # for apps/web
pnpm --filter learn-data-mutation add axios  # for labs
```

### Add a dev dependency

```bash
pnpm --filter web add -D tailwindcss
```

### Add multiple

```bash
pnpm --filter web add zod react-hook-form
```

### Upgrade a dependency

```bash
pnpm --filter web up next
```

---

## What NOT To Do (Hard Rules)

❌ Do not run `npm install` anywhere  
❌ Do not run `pnpm install` inside `apps/*` or `labs/*`  
❌ Do not commit `node_modules/`  
❌ Do not mix package managers  
❌ Do not edit `pnpm-lock.yaml` manually

---

## Installing / Reinstalling Dependencies

### Clean reinstall (safe & deterministic)

```bash
rm -rf node_modules
pnpm install --frozen-lockfile
```

If this fails, something is inconsistent and must be fixed before committing.

---

## Before Committing (Checklist)

Run these from repo root:

```bash
pnpm install --frozen-lockfile
pnpm format:check
pnpm -r build   # if builds exist
```

If all pass → commit with confidence.

---

## Mental Model

- **Repo root** = control center
- **Apps** = production projects you stand behind
- **Labs** = isolated learning sandboxes
- **pnpm** = dependency orchestrator
- **Lockfile** = single source of truth
- **Filters** = precision tools

You always act from the root.  
You always target explicitly.

---

## Philosophy

- Starters are time capsules
- Modernizing them teaches more than following them
- Pain from upgrades = real learning
- Determinism beats convenience

This workflow optimizes for long-term sanity.
