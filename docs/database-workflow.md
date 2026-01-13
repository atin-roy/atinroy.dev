# Labs Monorepo Workflow (Databases & Persistence)

This document defines **how to run, connect to, and manage databases** in the WSL/monorepo environment.

The goal: **ephemeral runtimes, persistent data, zero system pollution**.

---

## Core Principles

- **Servers live in Docker**: Postgres, MySQL, MongoDB run in containers.
- **SQLite lives in the filesystem**: It is treated as a local artifact.
- **Data survives restarts**: Use Docker volumes or local mounts.
- **Credentials are secrets**: Never commit connection strings.
- **One GUI to rule them all**: Prefer unified tools (DBeaver, DataGrip) over installing five different clients.

---

## The Tooling Stack (WSL Edition)

1.  **Docker Engine**: The runtime for DB servers.
2.  **Docker Compose**: The orchestrator for defining DB services.
3.  **better-sqlite3**: The driver for local Node.js/Next.js labs.
4.  **DBeaver / TablePlus**: Universal database client (install in Windows, connect to WSL/localhost).

---

## Strategy A: The "Shared Infrastructure" (Recommended)

Run a single instance of each major database at the root level. This mimics a real "cloud provider" that your apps connect to.

### 1. Create a root `docker-compose.yml`

Place this in `repo-root/infrastructure/docker-compose.yml`:

```yaml
services:
  # PostgreSQL (Primary Relational)
  postgres:
    image: postgres:16-alpine
    container_name: labs-postgres
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: password
      POSTGRES_DB: labs_db
    volumes:
      - pg_data:/var/lib/postgresql/data

  # MySQL (Legacy/Alternative)
  mysql:
    image: mysql:8.0
    container_name: labs-mysql
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: labs_db
    volumes:
      - mysql_data:/var/lib/mysql

  # MongoDB (NoSQL)
  mongo:
    image: mongo:latest
    container_name: labs-mongo
    ports:
      - "27017:27017"
    volumes:
      - mongo_data:/data/db

volumes:
  pg_data:
  mysql_data:
  mongo_data:
```

### 2. Start the infrastructure

From `repo-root/`:

```bash
docker compose -f infrastructure/docker-compose.yml up -d

```

Your databases are now running on `localhost:5432`, `localhost:3306`, and `localhost:27017`.

---

## Strategy B: SQLite (`better-sqlite3`)

Used for: **Next.js labs, lightweight experiments, simple KV stores.**

### 1. Install (in the specific lab)

```bash
# In labs/my-next-app
pnpm add better-sqlite3

```

### 2. File Location

Place the `.db` file in a dedicated folder or root of the lab.

**CRITICAL RULE**: You must `.gitignore` the database file.

```text
# .gitignore
*.db
*.sqlite
*.sqlite3

```

### 3. Usage Pattern

```javascript
// db.js
const Database = require("better-sqlite3");
const db = new Database("local.db", { verbose: console.log });
```

---

## Connecting from Apps

### 1. Spring Boot (`apps/api`)

Connects to **PostgreSQL** (preferred) or MySQL.

`src/main/resources/application.properties`:

```properties
# Good (Env Vars)
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}

# Acceptable for local dev (Default to Docker)
spring.datasource.url=jdbc:postgresql://localhost:5432/labs_db
spring.datasource.username=admin
spring.datasource.password=password

```

### 2. Next.js (`apps/web` or `labs/*`)

Connects to **Postgres** (via Prisma/Drizzle), **Mongo** (Mongoose), or **SQLite**.

`.env.local` (DO NOT COMMIT):

```bash
# Postgres
DATABASE_URL="postgresql://admin:password@localhost:5432/labs_db"

# Mongo
MONGODB_URI="mongodb://localhost:27017/my_app"

```

---

## Database Management & Migrations

How to change the schema without breaking things.

### Spring Boot

Use **Flyway** or **Liquibase**.

- Defines SQL scripts in `src/main/resources/db/migration`.
- App applies changes on startup.

### Next.js / Node

Use **Prisma** or **Drizzle**.

```bash
# Prisma Example
npx prisma migrate dev --name init

```

### Manual (Labs only)

Connect via DBeaver and run SQL manually.
_Allowed in `labs/`, discouraged in `apps/`._

---

## Hard Rules (The "Don't Do This" List)

❌ **Do not install Postgres/MySQL via `apt-get` or `brew` inside WSL.**
(It creates background services you will forget to turn off.)

❌ **Do not commit `.db` files.**
(Binary blobs bloat git history forever.)

❌ **Do not hardcode passwords in code.**
(Even for local dev. Use environment variables.)

❌ **Do not use Docker for the database _inside_ a production deployment.**
(In prod, use a managed database service like RDS, Neon, or Supabase. Docker is for local dev.)

---

## Mental Model

- **Docker** = Your local "Cloud Provider". It gives you the servers.
- **Volumes** = Your local "Hard Drive". It keeps the data when containers die.
- **Localhost** = The network bridge.
- **WSL** = The client.

You are effectively "renting" a database from Docker for the duration of your coding session.
