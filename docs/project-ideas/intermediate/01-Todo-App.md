# Todo App

**Tier:** 2-Intermediate  
**Stack:** Next.js Frontend, Spring Boot Backend with PostgreSQL

A comprehensive todo management application with persistence and advanced features.

## Learning Goals

- Next.js Server Actions vs client-side mutations
- Database modeling (Spring Boot + JPA)
- API design (RESTful endpoints)
- Authentication/Authorization basics
- Complex filtering and sorting
- Optimistic UI updates

## User Stories

- [ ] User can create a new todo item with title and optional description
- [ ] User can see all todos in a list view
- [ ] User can mark a todo as completed/incomplete
- [ ] User can delete a todo item
- [ ] User can edit a todo's title and description
- [ ] User can filter todos by status (all, active, completed)
- [ ] Todos persist across browser sessions

## Bonus Features

- [ ] User can set due dates on todos
- [ ] User can organize todos into categories/projects
- [ ] User can set priority levels (low, medium, high)
- [ ] Sort todos by due date, priority, or creation date
- [ ] (Spring Boot) Implement user authentication with JWT
- [ ] Collaborative todos - share with other users
- [ ] Recurring todos (daily, weekly, monthly)
- [ ] Email reminders for due todos
- [ ] Search functionality with full-text search

## API Endpoints (Spring Boot)

```
POST   /api/todos              - Create todo
GET    /api/todos              - List all todos (with filters)
GET    /api/todos/:id          - Get single todo
PUT    /api/todos/:id          - Update todo
DELETE /api/todos/:id          - Delete todo
PATCH  /api/todos/:id/complete - Mark as complete
```

## Useful Links & Resources

- [Spring Boot REST API](https://spring.io/guides/gs/rest-service/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Next.js Server Actions](https://nextjs.org/docs/app/building-your-application/data-fetching/server-actions)
- [TanStack Query (React Query)](https://tanstack.com/query/latest)

## Implementation Hints

- Start with localStorage persistence, then add backend
- Use Server Actions for seamless frontend-backend integration
- Implement optimistic updates for better UX
- Consider using a query library (TanStack Query) for server state
- Handle race conditions when multiple edits occur
