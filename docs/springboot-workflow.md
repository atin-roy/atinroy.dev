# Spring Boot Monorepo Workflow

This document defines **how Spring Boot backends work** in this monorepo, **how to add dependencies**, and **how to handle external starter projects**.

Applies to both `apps/api` (production, when implemented) and `labs/` (learning).

The goal: **modern Java, reproducible builds, zero "works on my machine" energy**.

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

- **Java**: Java 21 (current LTS) or Java 25 (latest)
- **Spring Boot**: latest stable
- **Build tool**: Gradle (Kotlin DSL preferred, Groovy acceptable) or Maven
- **JDK management**: SDKMAN or system-wide JDK

**Note:** Current projects use Java 25. Java 21 is the LTS recommendation for production.

---

## Folder Structure

```
atinroy.dev/
├─ apps/
│  └─ api/                    # production backend (planned, not yet implemented)
│     ├─ build.gradle.kts
│     ├─ settings.gradle.kts
│     ├─ gradlew
│     └─ src/
├─ labs/
│  ├─ dsa-java/               # DSA practice (Maven, Java 25)
│  │  ├─ pom.xml
│  │  └─ src/
│  └─ notes-project/backend/  # Spring Boot experiment (Gradle, Java 25)
│     ├─ build.gradle.kts
│     ├─ gradlew
│     └─ src/
├─ docs/
└─ .gitignore
```

Each backend is a **self-contained JVM project**.

---

## Creating a New Spring Boot Project

### For labs (learning projects)

#### Option A — Spring Initializr (recommended)

Generate with:

- Project: Gradle (Kotlin DSL) or Maven
- Language: Java
- Java: 21 (LTS) or 25 (latest)
- Spring Boot: latest stable

Then place it in:

```bash
labs/my-backend/
```

#### Option B — Copy an existing project

Copy files only:

```bash
labs/external-backend/
```

Do **not** build yet.

### For apps (production)

When `apps/api` is created, it will follow the same process but live in `apps/api/`.

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

- Java → 21 (LTS) or 25 (latest)
- Spring Boot → latest stable
- Migrate Maven → Gradle if desired

Expect:

- Compilation errors
- Deprecated config warnings
- Learning by friction (the good kind)

**Current projects** (`dsa-java`, `notes-project/backend`) use Java 25.

---

## Running a Backend Project

Always run commands **inside the project folder**:

### Gradle projects

```bash
cd labs/notes-project/backend
./gradlew bootRun
```

Or (Windows):

```powershell
gradlew bootRun
```

### Maven projects

```bash
cd labs/dsa-java
./mvnw spring-boot:run    # if it were a Spring Boot project
mvn test                  # for running tests
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
        languageVersion.set(JavaLanguageVersion.of(25))  // or 21 for LTS
    }
}
```

Or for **Maven**:

```xml
<properties>
    <maven.compiler.source>25</maven.compiler.source>
    <maven.compiler.target>25</maven.compiler.target>
</properties>
```

This guarantees:

- Same Java version everywhere
- CI and local parity
- Fewer "JDK mismatch" bugs

---

## Before Committing (Checklist)

From inside the project:

### Gradle

```bash
./gradlew clean
./gradlew build
```

Optional but recommended:

```bash
./gradlew test
```

### Maven

```bash
mvn clean
mvn compile
mvn test
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

- **Apps** = production backends (when implemented)
- **Labs** = learning backends (sealed boxes)
- **Gradle/Maven wrapper** = source of truth
- **Java versions are explicit**
- **Isolation beats convenience**

Frontend monorepo ≠ backend monorepo.  
That asymmetry is intentional.

Node projects share dependencies via pnpm.  
Java projects are fully independent.

---

## Philosophy

- Old Spring projects are fossils
- Upgrading them teaches architecture, not syntax
- JVM stability rewards discipline
- Predictable builds > clever setups

This workflow optimizes for clarity and long-term maintainability.
