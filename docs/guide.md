# atinroy.dev — Long-Term Project & Repo Guide

This document is a **living guide** for how this repo is structured, why it exists, and how decisions should be made over time.

It is not a rulebook. It is a compass.

---

## 1. What this repo *is* (and is not)

### This repo **is**:
- A long-lived workshop
- A place where projects evolve over months and years
- A record of growth, decisions, refactors, and experiments
- A single source of truth for personal work

### This repo **is not**:
- A single Next.js app
- A polished product snapshot
- A tutorial dump
- A resume-only artifact

The goal is continuity, not perfection.

---

## 2. High-level architecture philosophy

> **Architecture should match responsibility, not ambition.**

- Introduce complexity only when it earns its existence
- Avoid premature splitting of services
- Prefer boring, understandable systems
- Optimize for clarity and momentum

Uniformity is not the goal. Appropriateness is.

---

## 3. Monorepo mental model (critical)

### Core idea

- The **repo** is a container
- **Apps** live inside it
- Each app runs independently
- Tooling at the root only coordinates

The repo itself does not "run" anything.

---

## 4. Folder structure (canonical)

```
atinroy.dev/
├── apps/
│   ├── web/        # Main Next.js frontend (atinroy.dev)
│   └── api/        # Spring Boot backend (api.atinroy.dev)
│
├── labs/           # Experiments & practice projects
│   ├── counter/
│   ├── ui-tests/
│   └── animations/
│
├── packages/       # Shared code (only when needed)
│
├── docs/           # Notes, decisions, ADRs
│
├── pnpm-workspace.yaml
├── package.json
└── README.md
```

### Important boundaries
- `apps/` = things you *stand behind*
- `labs/` = things you *learn from*

Nothing in `labs/` needs to be deployed or showcased.

---

## 5. apps/web — the frontend platform

### Purpose

- Serves `atinroy.dev`
- Hosts all real user-facing projects
- Owns routing, layouts, and presentation

### Routing philosophy

Projects live as routes, not deployments:

```
/            → landing
/weather     → weather app
/todo        → todo app
/blog        → blog
```

Multiple projects ≠ multiple frontends.

---

## 6. apps/api — the backend platform

### Purpose

- One backend for all apps
- Handles:
  - authentication
  - authorization
  - persistence
  - business logic

### Key principle

> One backend can support many apps with different auth needs.

Auth is applied **per route**, not per server.

Examples:
- `/weather/**` → public
- `/todo/**` → user auth
- `/admin/**` → admin only

---

## 7. When *not* to use the backend

Do **not** introduce Spring Boot when:
- there is no auth
- there is no user data
- there is no business logic

Example: weather app

Correct approach:
- Fetch data server-side in Next.js
- Keep API keys secret
- Add caching at the frontend layer

This is not cutting corners. This is correct engineering.

---

## 8. labs/ — experiments & practice

### What belongs here

- small Next.js apps
- UI experiments
- counters, demos, spikes
- learning projects

### Rules

- Labs may be messy
- Labs may be abandoned
- Labs do not need polish
- Labs do not appear on homepage

Labs exist to accelerate learning, not signal expertise.

---

## 9. Git & repo visibility

### Repo visibility

- The repo is **public**
- Learning is visible
- Growth is documented naturally

Public does not mean performative.

### Commit philosophy

- Honest commit messages
- No history rewriting obsession
- Let evolution be visible

Progress beats polish.

---

## 10. pnpm & tooling

### pnpm workspaces

The root uses pnpm **only** to coordinate Node projects.

Java is unaffected.

Example `pnpm-workspace.yaml`:

```yaml
packages:
  - "apps/*"
  - "labs/*"
  - "packages/*"
```

### Important clarification

- The repo root is NOT a Next.js project
- `apps/web` IS the Next.js project
- `apps/api` IS the Spring Boot project

They are siblings, not parent/child.

---

## 11. Deployment philosophy

### Frontend

- Hosted on Netlify (free tier)
- CDN-backed, static-friendly
- Custom domain

### Backend

- Paid hosting
- Focus on uptime and stability

Pay where it matters. Avoid paying where it doesn’t.

---

## 12. Domains & URLs

- `atinroy.dev` → frontend
- `api.atinroy.dev` → backend

Subdomains represent **responsibility boundaries**, not tech requirements.

---

## 13. What goes on the homepage

Homepage shows:
- mature projects
- well-thought work
- things you stand behind

Homepage does NOT show:
- counters
- practice demos
- basic experiments

Those live quietly in history and labs.

---

## 14. Growth & refactoring rules

### Do this
- Refactor when pain appears
- Extract projects only when necessary
- Let structure emerge

### Avoid
- Premature microservices
- Over-modularization
- Tool-driven architecture

---

## 15. Final principles (non-negotiable)

- Clarity over cleverness
- Boring over fragile
- One backend until forced otherwise
- One frontend platform
- Experiments are allowed
- Growth is visible

---

## Closing note

This repo is not a snapshot of who you are.
It is a **timeline** of how you think.

Respect the work. Let it evolve.
