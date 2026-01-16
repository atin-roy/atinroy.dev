## **Personal Book Review Library**

A simple app where you create, read, update, and delete book reviews.

### **Core Features (Keep it minimal!)**

1. **Book List Page** - View all books you've reviewed
2. **Book Detail Page** - See full review + rating
3. **Add/Edit Review** - Form to create or update reviews
4. **Simple Auth** - Login/logout (just username/password, no OAuth complexity)
5. **Search** - Filter books by title or author

### **What You'll Learn**

**Next.js Side:**

- **Routing**: App router with dynamic routes (`/books/[id]`)
- **Data Fetching**: Server components fetching from your API
- **Mutations**: Server actions for forms (add/edit reviews)
- **Caching**: ISR for book list, on-demand revalidation after edits
- **Optimizations**: Image optimization for book covers, loading states, error boundaries

**Spring Boot Side:**

- **REST Endpoints**: CRUD operations (`/api/books`, `/api/reviews`)
- **Auth**: Spring Security with JWT tokens
- **JPA/Hibernate**: `Book` and `Review` entities with relationships
- **Controllers**: `BookController`, `AuthController`
- **Repository pattern**: JpaRepository interfaces

### **Database Schema (Super Simple)**

```
User: id, username, password (hashed)
Book: id, title, author, coverUrl, userId
Review: id, bookId, rating (1-5), reviewText, createdAt
```

### **Why This Works for Learning:**

- **Small scope** - You can finish in a weekend or two
- **Hits all concepts** - Without artificial complexity
- **Familiar domain** - No business logic confusion
- **Room to expand** - Can add tags, comments, etc. if you want more practice
- **AI-friendly** - Clear requirements make it easy to get help

### **Tech Stack**

- Next.js 15+ (App Router)
- Spring Boot 4.x
- PostgreSQL or H2 (for simplicity)
- Tailwind CSS (optional, for quick styling)
