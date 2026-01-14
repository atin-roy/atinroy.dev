# Real-Time Chat Application

**Tier:** 3-Advanced  
**Stack:** Next.js Frontend, Spring Boot Backend, WebSockets, PostgreSQL

A full-featured chat application with real-time messaging, user presence, and room management.

## Learning Goals

- WebSocket implementation and management
- Real-time state synchronization
- Database transactions and consistency
- Authentication and authorization at scale
- Performance optimization for high-volume messages
- User presence tracking
- Message encryption (bonus)

## User Stories

- [ ] User can register and login to the application
- [ ] User can create or join chat rooms
- [ ] User can send messages in real-time to a room
- [ ] User can see who else is currently in the room (presence)
- [ ] User can see message history when entering a room
- [ ] User can leave a room
- [ ] User sees typing indicators when others are typing
- [ ] User can see notifications for new messages

## Bonus Features

- [ ] Direct messaging between users
- [ ] Message search with full-text search
- [ ] File/image sharing in messages
- [ ] Message reactions (emojis)
- [ ] Admin features for room moderation
- [ ] Read receipts
- [ ] End-to-end encryption for messages
- [ ] User profiles with custom avatars
- [ ] Ban/mute users from rooms
- [ ] Message pinning
- [ ] Drafts autosave

## Database Schema

```
User
  - id (PK)
  - email (unique)
  - password (hashed)
  - username (unique)
  - avatar_url
  - createdAt

Room
  - id (PK)
  - name
  - description
  - isPrivate
  - createdBy (FK -> User)
  - createdAt

RoomMember
  - roomId (FK -> Room)
  - userId (FK -> User)
  - joinedAt
  - role (admin, member, guest)

Message
  - id (PK)
  - roomId (FK -> Room)
  - authorId (FK -> User)
  - content
  - createdAt
  - editedAt (nullable)
  - deletedAt (nullable, soft delete)

DirectMessage
  - id (PK)
  - senderId (FK -> User)
  - recipientId (FK -> User)
  - content
  - createdAt
  - readAt (nullable)

UserPresence
  - userId (PK -> User)
  - status (online, away, offline)
  - lastSeen
  - currentRoomId (FK -> Room, nullable)

TypingIndicator
  - roomId (FK -> Room)
  - userId (FK -> User)
  - timestamp
```

## Spring Boot Architecture

```
Controllers:
  - AuthController (register, login, logout)
  - RoomController (CRUD rooms, list members)
  - MessageController (post message, search, delete)
  - UserController (profile, presence, settings)
  - WebSocketController (handle real-time events)

Services:
  - AuthService (JWT tokens, password hashing)
  - RoomService (room management, membership)
  - MessageService (message persistence, validation)
  - PresenceService (track online users)
  - NotificationService (broadcast events)

WebSocket Events:
  - user.join_room
  - user.leave_room
  - message.new
  - message.edited
  - message.deleted
  - user.typing
  - user.presence_changed
```

## WebSocket Protocol (Spring Boot with STOMP)

```
Client -> Server:
/app/chat/sendMessage
  { roomId, content }

Server -> Clients:
/topic/room/:roomId
  { messageId, authorId, content, createdAt, authorName }

/topic/room/:roomId/presence
  { userId, status, username }

/topic/room/:roomId/typing
  { userId, username, isTyping }
```

## API Endpoints

```
AUTH:
POST   /api/auth/register         - Register user
POST   /api/auth/login            - Login user
POST   /api/auth/logout           - Logout user

ROOMS:
POST   /api/rooms                 - Create room
GET    /api/rooms                 - List joined rooms
GET    /api/rooms/:id             - Get room details
PUT    /api/rooms/:id             - Update room (admin)
DELETE /api/rooms/:id             - Delete room (admin)
POST   /api/rooms/:id/join        - Join room
POST   /api/rooms/:id/leave       - Leave room
GET    /api/rooms/:id/members     - List room members

MESSAGES:
GET    /api/messages/:roomId      - Get message history (paginated)
PUT    /api/messages/:id          - Edit message (owner)
DELETE /api/messages/:id          - Delete message (owner/admin)

DIRECT MESSAGES:
GET    /api/dms/:userId           - Get DM history
POST   /api/dms/:userId           - Send DM

PRESENCE:
GET    /api/users/:id/presence    - Get user presence
GET    /api/rooms/:id/presence    - Get all online users in room
```

## Useful Links & Resources

- [Spring Boot WebSocket Guide](https://spring.io/guides/gs/messaging-stomp-websocket/)
- [STOMP Protocol](https://stomp.github.io/)
- [SockJS (WebSocket fallback)](https://github.com/sockjs/sockjs-client)
- [Next.js WebSocket Client](https://github.com/TooTallNate/ws)
- [Spring Security for WebSockets](https://docs.spring.io/spring-security/reference/servlet/integrations/websocket.html)

## Implementation Hints

- Start with HTTP endpoints before adding WebSocket
- Use a message queue (RabbitMQ) for distributed systems
- Implement database indexes on frequently queried columns
- Use connection pooling for database
- Consider Redis for user presence (faster than database)
- Test WebSocket disconnections and reconnections
- Implement proper cleanup for stale connections
- Use optimistic locking for concurrent message edits
