---
name: Labs Web Dev Starter
overview: ""
todos: []
---

# Labs Web Dev Starter

## Overview

Build a very small "Lab Notes" stack where a Next.js App Router front-end lets you add/view short lab notes, and a Spring Boot + Gradle backend persists those notes in-memory (or on a lightweight H2 store) via a simple REST API. This keeps the project exploratory yet focused on fundamental full-stack integration points.

## Plan

1. **Scaffold the stacks**

- Create `labs/notes-project/frontend` (Next.js App Router, `pnpm` workspace) with a single page and global styles.
- Create `labs/notes-project/backend` (Spring Boot Gradle project) with Kotlin or Java records for note DTOs.
- Wire workspace `package.json/pnpm` and Gradle settings so both live in the repo without interfering.

2. **Implement backend note API**

- Define a `Note` record/DTO and a reactive `NoteController` that exposes `GET /notes` and `POST /notes` using an in-memory repository or H2.
- Add a `NoteService` to encapsulate business logic (validate non-empty text). Return created notes with IDs.
- Expose CORS from the Spring Boot app so the Next.js front can talk to `http://localhost:8080`.

3. **Build frontend experience**

- In `apps/labs-frontend/app/page.tsx`, build a small form to submit a note and display the returned list from `GET /notes`.
- Use `fetch` on the client (with `useEffect`/`useState`) and a layout that feels like a lab notebook card list.
- Add a simple `components/NoteList.tsx` to render cards, keep everything TypeScript strict.

4. **Document and reflect**

- Add README instructions for running both apps (pnpm dev, Gradle bootRun) plus env expectations.
- Write a short `notes.md` documenting what you got stuck on and how you resolved it so you can reuse it later.
- Optionally add a small `docker-compose` skeleton later if you want to revisit containerization later.

5. **Verify and iterate**

- Run `pnpm lint`/`pnpm test` (if any) and `./gradlew test` to validate both sides.
- Capture learnings in the notes file after each experiment.

## Todos

- `setup-workspace` – establish pnpm/Gradle build roots and scaffolding for each project.
- `backend-api` – implement Spring Boot note API with CORS enabled and basic validation.
- `frontend-ui` – build Next.js note form + list with typed API calls.
- `docs-notes` – document commands, problems encountered, and resolutions for future review.
- `verify-stack` – run both dev servers, test requests, and capture results in notes.