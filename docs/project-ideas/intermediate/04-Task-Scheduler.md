# Task Scheduler API

**Tier:** 2-Intermediate  
**Stack:** Next.js Frontend, Spring Boot Backend with PostgreSQL

A scheduling system where users can create and manage recurring tasks, with email notifications and calendar integration.

## Learning Goals

- Scheduled jobs and background processing (Spring Scheduler)
- CRON expressions and job scheduling
- Email notifications (JavaMail)
- Calendar API integration
- Time zones handling
- Event-driven architecture basics
- Job state management

## User Stories

- [ ] User can create one-time or recurring tasks
- [ ] User can set frequency (daily, weekly, monthly, custom CRON)
- [ ] User can assign tasks to specific dates/times
- [ ] User receives email reminders before task time
- [ ] User can view all tasks in a calendar view
- [ ] User can mark tasks as complete
- [ ] User can reschedule or cancel tasks
- [ ] User sees task history

## Bonus Features

- [ ] Integrate with Google Calendar / Outlook
- [ ] Multiple notification channels (SMS, Slack, Discord)
- [ ] Smart scheduling (find best time for task)
- [ ] Task dependencies (Task B can only start after Task A)
- [ ] Analytics - track task completion rates
- [ ] Collaborative tasks with team members
- [ ] Mobile app notifications (Push notifications)
- [ ] Ical file import/export

## Database Schema

```
User
  - id (PK)
  - email (unique)
  - password
  - timezone
  - createdAt

Task
  - id (PK)
  - userId (FK -> User)
  - title
  - description
  - scheduledDate
  - status (pending, completed, cancelled)
  - createdAt
  - completedAt (nullable)

RecurringTask
  - id (PK)
  - userId (FK -> User)
  - title
  - description
  - cronExpression
  - timezone
  - isActive
  - startDate
  - endDate (nullable)
  - createdAt

TaskExecution
  - id (PK)
  - recurringTaskId (FK -> RecurringTask)
  - executionTime
  - status (pending, completed, failed, skipped)
  - completedAt (nullable)
  - nextExecution

Notification
  - id (PK)
  - taskId or recurringTaskId (FK)
  - userId (FK -> User)
  - type (email, sms, notification)
  - scheduledTime
  - sentAt (nullable)
  - status (pending, sent, failed)
```

## Spring Boot Components

```
Services:
  - TaskService (CRUD tasks)
  - SchedulerService (manage scheduled jobs)
  - NotificationService (send notifications)
  - CalendarIntegrationService (external calendar sync)

Scheduled Jobs:
  - @Scheduled for recurring task execution
  - Job for email notifications
  - Job for calendar sync

Controllers:
  - TaskController
  - RecurringTaskController
  - NotificationController
  - CalendarController
```

## API Endpoints

```
TASKS:
POST   /api/tasks                    - Create one-time task
POST   /api/recurring-tasks          - Create recurring task
GET    /api/tasks                    - List user's tasks
GET    /api/tasks/:id                - Get task details
PUT    /api/tasks/:id                - Update task
DELETE /api/tasks/:id                - Delete task
PATCH  /api/tasks/:id/complete       - Mark complete

CALENDAR:
GET    /api/calendar/:month          - Get tasks for month
GET    /api/calendar/week/:start     - Get tasks for week
POST   /api/calendar/sync            - Sync with Google Calendar

NOTIFICATIONS:
GET    /api/notifications            - List notifications
PUT    /api/notifications/:id/read   - Mark as read

RECURRING:
GET    /api/recurring-tasks          - List recurring tasks
POST   /api/recurring-tasks/:id/skip - Skip next execution
```

## Key Challenges

- **Timezone handling:** User's local timezone vs server time
- **Job persistence:** Handle server restarts without losing scheduled jobs
- **Concurrency:** Multiple instances executing same job
- **Notification timing:** Accurate scheduling across timezones

## Useful Technologies

- **Spring Scheduler:** `@Scheduled`, `TaskScheduler`
- **Quartz Scheduler:** Advanced job scheduling (optional)
- **JavaMail:** Send emails
- **Google Calendar API:** Calendar integration
- **Cron Expression Library:** Validate and parse CRON

## Useful Links & Resources

- [Spring Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks/)
- [Quartz Scheduler](http://www.quartz-scheduler.org/)
- [CRON Expression Reference](https://crontab.guru/)
- [JavaMail API](https://javamail.java.net/)
- [Google Calendar API](https://developers.google.com/calendar)

## Implementation Hints

- Start with one-time tasks before recurring
- Use `@Scheduled` initially, graduate to Quartz for complexity
- Store completed task history for analytics
- Test timezone handling extensively
- Consider database job storage for failure recovery
