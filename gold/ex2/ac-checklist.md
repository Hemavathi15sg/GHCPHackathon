# AC Checklist — USER-142 User Registration
> Gold Standard Reference for Exercise 2, Step 1
> Source: jira/ticket.md

---

## Acceptance Criteria Checklist

- [ ] **AC-1:** POST `/api/users/register` with valid email and password (≥8 chars, ≥1 uppercase, ≥1 number) returns HTTP 201 with the new user's ID in the response body.

- [ ] **AC-2:** POST `/api/users/register` with an already-registered email returns HTTP 409 Conflict with a descriptive error message (not a generic 500).

- [ ] **AC-3:** The `users` table (or equivalent persistence layer) stores a bcrypt hash for the password field — verified by inspecting what `UserService.register()` passes to the repository. Plain-text password must never appear in any log line.

- [ ] **AC-4:** POST `/api/users/register` called more than 5 times per IP per minute returns HTTP 429 Too Many Requests on the 6th request.

- [ ] **AC-5:** On successful registration (HTTP 201 returned), a confirmation email is dispatched to the registered email address asynchronously — the registration response is not delayed by the email send.

---

## Notes on Verifiability

| AC | Verifiable from | What to check |
|----|----------------|---------------|
| AC-1 | `UserController.java`, `UserService.java`, unit tests | HTTP status code, response body contains `userId`, password validation logic |
| AC-2 | `UserService.java`, `UserController.java`, unit tests | 409 returned on duplicate; error message is human-readable |
| AC-3 | `UserService.java`, log statements | `BCryptPasswordEncoder.encode()` called; no `request.getPassword()` in any log |
| AC-4 | Controller / filter config, integration tests | Rate limiting filter/interceptor present; 429 response on excess requests |
| AC-5 | `UserService.java` or event publisher, notification service call | Async email dispatch present; response not blocked on email |
