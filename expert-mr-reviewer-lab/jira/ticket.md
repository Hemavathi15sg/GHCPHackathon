# JIRA Ticket — USER-142
**Project:** User Management Service
**Type:** Story
**Priority:** High
**Sprint:** Sprint 14
**Assignee:** Dev Team

---

## Story

**As a** new user,
**I want to** register an account using my email address and password,
**So that** I can access the platform's features.

---

## Acceptance Criteria

- **AC-1:** Given a valid email and password (≥8 chars, ≥1 uppercase, ≥1 number), when POST `/api/users/register` is called, then a new user is created and HTTP 201 is returned with the user ID.
- **AC-2:** Given a duplicate email address, when POST `/api/users/register` is called, then HTTP 409 Conflict is returned with a descriptive error message.
- **AC-3:** Passwords must be stored as a bcrypt hash — plain text passwords must never be stored or logged.
- **AC-4:** The registration endpoint must enforce rate limiting: max 5 requests per IP per minute; excess requests receive HTTP 429.
- **AC-5:** On successful registration, a confirmation email must be sent asynchronously via the notification service.

---

## Definition of Done

- Unit tests cover the happy path and all error scenarios (AC-1, AC-2, AC-3)
- Code coverage for `UserService` ≥ 80%
- SonarQube shows no new blocker or critical issues
- No HIGH CVEs introduced in dependency changes
- PR reviewed and approved by a senior engineer

---

## Notes

- Use `BCryptPasswordEncoder` from Spring Security
- Email sending should be fire-and-forget — do not block the registration response
- Rate limiting can use `spring-boot-starter-data-redis` with token bucket pattern
