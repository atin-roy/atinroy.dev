# Labs Monorepo Workflow (Spring Boot Backends)

This document defines **how Spring Boot backend labs work**, **how to add dependencies**, and **how to handle external starter projects** inside the monorepo.

The goal: **modern Java, reproducible builds, zero “works on my machine” energy**.

---

## Core Principles

- **One build tool per project** (Gradle preferred)
- **Each backend is fully isolated**
- **No shared build caches committed**
- **Latest stable Java & Spring Boot by default**
- **Root repo does NOT manage Java dependencies**

Unlike JS, Java builds are intentionally siloed.

---

## Recommended Tooling Baseline

- **Java**: LTS only (25 preferred)
- **Spring Boot**: latest stable
- **Build tool**: Gradle (Kotlin DSL preferred, Groovy acceptable)
- **JDK management**: SDKMAN or system-wide JDK

---

## Folder Structure

```

repo-root/
├─ labs/
│ ├─ weather-api/
│ │ ├─ build.gradle(.kts)
│ │ ├─ settings.gradle(.kts)
│ │ ├─ gradlew
│ │ ├─ gradle/
│ │ └─ src/
│ └─ auth-service/
│ ├─ build.gradle(.kts)
│ └─ src/
├─ docs/
└─ .gitignore

```

Each backend is a **self-contained JVM project**.

---

## Creating a New Spring Boot Lab

### Option A — Spring Initializr (recommended)

Generate with:

- Project: Gradle
- Language: Java
- Java: 25
- Spring Boot: latest stable

Then place it in:

```

labs/my-backend/

```

---

### Option B — Copy an existing project

Copy files only:

```

labs/external-backend/

```

Do **not** build yet.

---

## Handling External / Course Backends

Many courses use:

- Java 8 / 11
- Old Spring Boot
- Maven

You have two valid strategies.

---

### Option A — Preserve versions (historical accuracy)

Use when:

- Course relies on deprecated APIs
- You want to follow along exactly

Result:

- That lab stays old
- Other labs can be modern
- No conflict, because isolation

---

### Option B — Upgrade immediately (recommended)

Update:

- Java → 21
- Spring Boot → latest stable
- Migrate Maven → Gradle if needed

Expect:

- Compilation errors
- Deprecated config warnings
- Learning by friction (the good kind)

---

## Running a Backend Lab

Always run commands **inside the lab folder**:

```bash
cd labs/weather-api
./gradlew bootRun
```

Or (Windows):

```powershell
gradlew bootRun
```

Each backend owns its lifecycle.

---

## Adding Dependencies (Gradle)

### Kotlin DSL

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
}
```

### Groovy DSL

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

Then:

```bash
./gradlew build
```

---

## Build Artifacts & Git Rules

### NEVER commit:

- `build/`
- `.gradle/`
- `out/`
- IDE files (`.idea/`, `.classpath`, etc.)

Only commit:

- Source code
- Gradle wrapper
- Build scripts
- Config files

---

## Environment Configuration

- Use `application.yml` or `application.properties`
- Secrets go in:
  - `.env` (ignored)
  - environment variables

- Never hardcode credentials

---

## Java Version Enforcement

Use **Gradle toolchains**:

```kotlin
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
```

This guarantees:

- Same Java version everywhere
- CI and local parity
- Fewer “JDK mismatch” bugs

---

## Before Committing (Checklist)

From inside the lab:

```bash
./gradlew clean
./gradlew build
```

Optional but recommended:

```bash
./gradlew test
```

If this passes, the backend is commit-safe.

---

## What NOT To Do (Hard Rules)

❌ Do not share Gradle builds across labs
❌ Do not centralize Java dependencies at repo root
❌ Do not commit build artifacts
❌ Do not rely on IDE-managed builds
❌ Do not mix Maven and Gradle in the same lab

---

## Mental Model

- **Each backend is a sealed box**
- **Gradle wrapper = source of truth**
- **Java versions are explicit**
- **Isolation beats convenience**

Frontend monorepo ≠ backend monorepo.
That asymmetry is intentional.

---

## Philosophy

- Old Spring projects are fossils
- Upgrading them teaches architecture, not syntax
- JVM stability rewards discipline
- Predictable builds > clever setups

This workflow optimizes for clarity and long-term maintainability.
