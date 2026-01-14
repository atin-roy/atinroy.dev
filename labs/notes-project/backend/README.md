# Notes Project Backend

This directory holds the Spring Boot/Gradle backend that the frontend will talk to. It is intentionally
minimal so you can experiment with Spring annotations, Gradle toolchains, and validation logic.

## Learning reference

- `build.gradle` teaches you how Gradle plugins (`java`, `spring-boot`, `dependency-management`) declare dependencies and configure a Java toolchain.
- `src/main/java/dev/atinroy/notesbackend/NotesBackendApplication.java` boots the application via `SpringApplication.run(...)`.
- `NoteController` demonstrates `@RestController`, `@CrossOrigin`, and how Spring maps JSON to Java records.
- `NoteService` shows a simple service layer that keeps state in memory using `CopyOnWriteArrayList` and `AtomicLong`.
- `CreateNoteRequest` uses Jakarta Bean Validation annotations to enforce request shape before hitting controller logic.

## Manual commands

```bash
cd labs/notes-project/backend
./gradlew tasks          # shows available Gradle tasks, good for learning
./gradlew bootRun        # starts the server on localhost:8080
./gradlew test           # runs the unit tests we added with the starter project
```

If you change the Java target, update the `java.toolchain.languageVersion` in `build.gradle` and rerun `./gradlew --stop` before the next build so Gradle reuses the correct JDK.

## API overview

- `GET /notes`
  - Returns a JSON array of `{ "id": <number>, "text": "<note>" }`.
- `POST /notes`
  - Request body example: `{ "text": "My lab note" }`
  - Uses validation to reject blank text; invalid requests get HTTP 400 with a human message.
  - Responds with HTTP 201 and the newly created note (auto-incremented `id`).
  - CORS is enabled for `http://localhost:3000` so the Next.js frontend can call this backend during development.
