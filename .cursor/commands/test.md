# Unit Test Generation

Generate strict unit tests for the selected code.

## Tech Stack Awareness

- **Spring Boot:** Use JUnit 5 + Mockito. Focus on Service layer testing.
- **Next.js/React:** Use Vitest + React Testing Library. Focus on user interactions.
- **Python:** Use `pytest`.

## Requirements

1. **Happy Path:** Test the standard use case.
2. **Edge Cases:** Null inputs, empty lists, negative numbers.
3. **Mocking:** Mock external dependencies (DB, APIs). Do not rely on real services.

## Style

- Use descriptive test names (e.g., `shouldThrowExceptionWhenUserNotFound`).
- Follow "Arrange, Act, Assert".
