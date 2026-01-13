# Labs Monorepo Workflow (Next.js + pnpm)

This document defines **how labs projects work**, **how to add dependencies**, and **how to handle external starter projects** (Udemy, GitHub templates, etc.) inside the monorepo.

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

repo-root/
├─ package.json
├─ pnpm-workspace.yaml
├─ pnpm-lock.yaml
├─ node_modules/.pnpm/ # actual dependency store
├─ labs/
│ ├─ weather-app/
│ │ ├─ package.json
│ │ └─ node_modules/ # symlinks (not real installs)
│ └─ udemy-blog/
│ ├─ package.json
│ └─ node_modules/
└─ docs/

```

Each lab **appears** to have its own `node_modules`, but pnpm stores dependencies once and links them.

---

## Adding a New Next.js Lab

1. Create the folder:

```

labs/my-next-app/

```

2. Ensure `pnpm-workspace.yaml` includes labs:

```yaml
packages:
  - "labs/*"
```

3. Add a `package.json` to the lab.

4. Install dependencies **from repo root**:

   ```bash
   pnpm install
   ```

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

### Step 4: Run the lab

From repo root:

```bash
pnpm --filter udemy-project dev
```

Alternative:

```bash
pnpm -C labs/udemy-project dev
```

---

## Adding Dependencies to a Specific Lab

Always run from **repo root**.

### Add a dependency

```bash
pnpm --filter weather-app add axios
```

### Add a dev dependency

```bash
pnpm --filter weather-app add -D tailwindcss
```

### Add multiple

```bash
pnpm --filter weather-app add zod react-hook-form
```

### Upgrade a dependency

```bash
pnpm --filter weather-app up next
```

---

## What NOT To Do (Hard Rules)

❌ Do not run `npm install` anywhere
❌ Do not run `pnpm install` inside `labs/*`
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
- **Labs** = isolated sandboxes
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
