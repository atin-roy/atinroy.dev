# Counter 🧪

A cross-platform experimental lab to learn **shared application logic, offline-first data, and sync** across **Web, Mobile, and Desktop** clients backed by a **Spring Boot** backend.

This project intentionally uses a _simple domain_ (counters) to focus on **architecture, data flow, and synchronization**, not UI complexity.

---

## 🎯 Goals

The primary goal of this lab is to learn how to:

- Connect **React Native (mobile)**, **Electron (desktop)**, and **Web (React/Next.js)** apps
- Allow each client to:
  - Work **offline**
  - Maintain its **own local database**
- Sync local data with a **Spring Boot backend** when connectivity is available
- Design a system that supports:
  - Optional authentication
  - Multiple counters per user
  - Anonymous usage (no account required)
  - Account-based syncing when the user opts in

A counter app is used because it:

- Minimizes UI and domain complexity
- Still exposes real-world problems like:
  - State reconciliation
  - Conflict resolution
  - Auth vs non-auth flows
  - Cross-platform shared logic

---

## 🧠 Core Concepts Being Explored

- Offline-first architecture
- Eventual consistency
- Client-side persistence
- Sync-on-connect patterns
- Shared domain logic across platforms
- Optional authentication flows
- Monorepo structure for multi-platform apps

---

## 🗂 Project Structure

```

labs/counter-app/
├── apps/
│ ├── web/ #Nice choice of project scope. A counter app sounds trivial, but architecturally it’s _sneaky-hard_ in exactly the right ways 😄
│ ├── mobile/ # Mobile client (React Native)
│ └── desktop/ # Desktop client (Electron)
│
├── packages/
│ ├── shared/ # Shared domain logic, types, utils
│ └── ui/ # (Optional) Shared UI primitives
│
├── backend/
│ ├── api/ # Spring Boot application
│ ├── db/ # Database config / migrations
│ └── infra/ # Postgres, Docker, etc.
│
└── README.md

```

---

## 🔁 Data Model (Conceptual)

- **Counter**
  - id
  - name
  - value
  - updatedAt
  - owner (optional)

Each client stores counters locally first.
When authenticated and online, counters are synced to the backend.

---

## 🔐 Authentication Philosophy

- **No auth required** to use the app locally
- Users can:
  - Use counters anonymously (local-only)
  - Later sign in and sync existing counters
- Authentication enables:
  - Cloud persistence
  - Multi-device sync
  - User-scoped counters

---

## 🛠 Backend Stack

- Spring Boot
- PostgreSQL
- REST (initially)
- Auth-ready (JWT / session-based later)
- Designed for incremental evolution, not premature complexity

---

## 🚧 Non-Goals (for now)

- Fancy UI/animations
- Complex domain logic
- Premature optimization
- Full real-time sync

This is a **learning lab**, not a production product.

---

## 📈 Future Exploration Ideas

- Conflict resolution strategies
- Background sync
- Push-based updates
- CRDT-style counters
- WebSockets or SSE
- End-to-end testing across clients

---

## 🧪 Why This Exists

This lab exists to answer one question:

> _How do real-world apps actually stay consistent across devices without falling apart?_

Everything else is just implementation detail.
