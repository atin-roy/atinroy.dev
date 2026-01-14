# Blog Platform

**Tier:** 2-Intermediate  
**Stack:** Next.js Frontend, Spring Boot Backend with PostgreSQL

A blogging platform where users can create, read, and comment on blog posts.

## Learning Goals

- Database relationships (one-to-many, many-to-many)
- Pagination and infinite scroll
- Markdown rendering
- Comment systems and nested replies
- User authentication and authorization
- SEO optimization in Next.js

## User Stories

- [ ] User can view a list of published blog posts
- [ ] User can click on a post to read the full content
- [ ] Authenticated user can create a new blog post
- [ ] Post author can edit/delete their own posts
- [ ] User can read comments on a post
- [ ] Authenticated user can add comments to posts
- [ ] User can search posts by title or content
- [ ] Posts are paginated (10 per page)

## Bonus Features

- [ ] Support markdown formatting in post creation
- [ ] Rich text editor with preview
- [ ] Like/upvote posts and comments
- [ ] User profiles showing all their posts
- [ ] Tags/categories for organizing posts
- [ ] Nested comment replies
- [ ] Comment moderation for post authors
- [ ] Social sharing buttons
- [ ] RSS feed generation
- [ ] Reading time estimate
- [ ] Related posts suggestions
- [ ] Comment notifications for post authors

## Database Schema (Spring Boot JPA)

```
User
  - id (PK)
  - email (unique)
  - password (hashed)
  - username
  - createdAt

Post
  - id (PK)
  - title
  - content (markdown)
  - authorId (FK -> User)
  - createdAt
  - updatedAt
  - publishedAt

Comment
  - id (PK)
  - content
  - postId (FK -> Post)
  - authorId (FK -> User)
  - parentCommentId (FK -> Comment, nullable)
  - createdAt
  - updatedAt

Tag
  - id (PK)
  - name
  - slug (unique)

PostTag
  - postId (FK -> Post)
  - tagId (FK -> Tag)
```

## API Endpoints

```
GET    /api/posts              - List posts (with pagination/search)
GET    /api/posts/:id          - Get single post with comments
POST   /api/posts              - Create post (auth required)
PUT    /api/posts/:id          - Update post (owner only)
DELETE /api/posts/:id          - Delete post (owner only)

POST   /api/posts/:id/comments - Add comment
GET    /api/posts/:id/comments - Get post comments
PUT    /api/comments/:id       - Update comment (owner only)
DELETE /api/comments/:id       - Delete comment (owner only)

POST   /api/posts/:id/likes    - Like post
DELETE /api/posts/:id/likes    - Unlike post
```

## Useful Links & Resources

- [Spring Boot Security](https://spring.io/projects/spring-security)
- [Spring Data JPA Pagination](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
- [Markdown parsing in Java](https://github.com/commonmark/commonmark-java)
- [Next.js Dynamic Routing](https://nextjs.org/docs/app/building-your-application/routing/dynamic-routes)

## Implementation Hints

- Start with basic CRUD before adding complex features
- Implement pagination on the backend (not frontend)
- Cache frequently accessed posts
- Use database transactions for multi-table operations
- Implement proper error handling and validation
- Consider search optimization with database indexes
