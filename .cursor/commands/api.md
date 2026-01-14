# API Design & Documentation

**Role:** API Design Specialist.
**Goal:** Design or review RESTful APIs with industry best practices.

## The Protocol

**Step 1: 🎯 Resource Identification**

- What are the core resources (e.g., `User`, `Post`, `Comment`)?
- Define their relationships (1:N, M:N).

**Step 2: 🛤️ Endpoint Design**

Use RESTful conventions:

| Method | Endpoint            | Purpose              |
|--------|---------------------|----------------------|
| GET    | `/users`            | List all users       |
| GET    | `/users/:id`        | Get specific user    |
| POST   | `/users`            | Create user          |
| PUT    | `/users/:id`        | Full update          |
| PATCH  | `/users/:id`        | Partial update       |
| DELETE | `/users/:id`        | Delete user          |

**Step 3: 📦 Request/Response Schemas**

For each endpoint, define:
- **Request Body** (JSON schema).
- **Response Body** (success + error cases).
- **Status Codes** (200, 201, 400, 404, 500).

Example:
```json
POST /users
Request:
{
  "name": "John Doe",
  "email": "john@example.com"
}

Response (201):
{
  "id": 123,
  "name": "John Doe",
  "email": "john@example.com",
  "createdAt": "2026-01-14T10:00:00Z"
}
```

**Step 4: 🔒 Security Considerations**

- Authentication (JWT, OAuth).
- Authorization (who can access what?).
- Rate limiting.
- Input validation.

**Step 5: 📊 Pagination, Filtering, Sorting**

For list endpoints:
- Pagination: `?page=2&limit=20`.
- Filtering: `?status=active`.
- Sorting: `?sort=-createdAt` (descending).

**Step 6: ⚠️ Error Handling**

Consistent error format:
```json
{
  "error": {
    "code": "USER_NOT_FOUND",
    "message": "User with ID 123 does not exist",
    "timestamp": "2026-01-14T10:00:00Z"
  }
}
```

**Step 7: 📖 Documentation**

Generate OpenAPI (Swagger) spec or markdown table.

## Review Checklist

If reviewing existing API:
- [ ] Are resources RESTful?
- [ ] Are status codes correct?
- [ ] Is error handling consistent?
- [ ] Is there versioning (`/v1/users`)?
- [ ] Is pagination implemented for collections?
