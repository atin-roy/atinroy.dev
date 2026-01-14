# GitHub User Explorer

**Tier:** 2-Intermediate  
**Stack:** Next.js Frontend, Spring Boot Backend (optional caching)

An application that fetches and displays GitHub user profiles, repositories, and activity.

## Learning Goals

- Calling external APIs from Next.js and Spring Boot
- Handling API rate limiting
- Caching strategies
- Complex data transformations
- Pagination with external APIs
- Error handling and fallbacks

## User Stories

- [ ] User can search for a GitHub username
- [ ] User sees the GitHub profile with avatar, bio, follower count
- [ ] User can see a list of the user's public repositories
- [ ] Each repo shows stars, forks, description, and primary language
- [ ] User can click a repo to see more details (commits, releases)
- [ ] User can see the user's most used programming languages
- [ ] Search results are cached to avoid rate limiting

## Bonus Features

- [ ] Show contribution graph/streak
- [ ] Display trending repositories (not just user's)
- [ ] Compare two GitHub users side by side
- [ ] Show repository topics/tags
- [ ] Display recent activity (issues, PRs, commits)
- [ ] (Spring Boot) Create caching layer to store user profiles and reduce API calls
- [ ] Dark mode toggle
- [ ] Favorites - save favorite profiles for quick access
- [ ] Export profile data as PDF

## GitHub API Endpoints Used

```
GET https://api.github.com/users/:username
GET https://api.github.com/users/:username/repos
GET https://api.github.com/users/:username/events
GET https://api.github.com/search/repositories
```

## Spring Boot Caching Layer (Optional)

```
GET  /api/github/users/:username        - Get cached user profile
GET  /api/github/users/:username/repos  - Get cached repos
POST /api/github/users/:username/cache  - Refresh cache
```

## Useful Links & Resources

- [GitHub REST API](https://docs.github.com/en/rest)
- [GitHub GraphQL API](https://docs.github.com/en/graphql)
- [Spring Boot HTTP Client](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#io.rest-client)
- [Next.js Caching](https://nextjs.org/docs/app/building-your-application/caching)

## Implementation Hints

- Start with REST API before considering GraphQL
- Implement exponential backoff for rate limits
- Store cache in database with expiration timestamps
- Handle users that don't exist gracefully
- Consider using REST vs GraphQL based on query needs
- Test with rate limit scenarios
