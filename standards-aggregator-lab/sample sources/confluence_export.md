# Confluence Standards Export — Team Wiki
*Last updated: March 2024 | Owner: Platform Team*

> Note: This page was exported from Confluence. Some sections may be outdated.

---

## Naming Conventions

- Use **snake_case** for all variable and function names.
- Class names must use **PascalCase**.
- Constants must be **UPPER_SNAKE_CASE**.
- Avoid abbreviations unless universally understood (e.g., `url`, `id`, `dto`).
- File names must match the primary class name, e.g., `UserService.java`.

### Old rule (superseded — do not follow)
- Prefix boolean variables with `is_` or `has_` (e.g., `is_active`).

---

## Error Handling

- **Always log before rethrowing** an exception.
- Use custom exception types for domain errors (e.g., `ValidationException`, `NotFoundException`).
- Never swallow exceptions with an empty catch block.
- Log the full stack trace at ERROR level.
- Wrap third-party calls in try-catch and convert to internal exception types.

```java
// Example
try {
    externalService.call();
} catch (ExternalException e) {
    log.error("External service failed", e);
    throw new ServiceException("Downstream failure", e);
}
```

---

## Logging

- Use structured JSON logging.
- Always include: `timestamp`, `level`, `service`, `traceId`, `message`.
- Do **not** log passwords, tokens, or PII.
- Log at INFO for normal operations, WARN for recoverable issues, ERROR for failures.
- Use correlation IDs for all cross-service calls.

---

## Security

- Never hardcode secrets, passwords, or API keys in source code.
- Use environment variables or a secrets manager (e.g., Vault, AWS Secrets Manager).
- Validate all user input at the API boundary.
- Use parameterized queries — never string-concatenate SQL.

---

## Testing

- Minimum **80% line coverage** enforced in CI.
- Write unit tests for all service-layer methods.
- Use mocks for external dependencies.
- Integration tests must cover happy path + at least one error path.
- Test names must follow: `methodName_scenario_expectedResult`.

---

## Performance

- Avoid N+1 query patterns — use JOIN or batch fetch.
- Cache results for repeated read operations (TTL must be documented).
- Set timeouts on all external HTTP calls (default: 5 seconds).

---

## Duplicate Rule (intentional — also in team_notes.md)
- Never log sensitive user data including passwords or personal information.

---

*This document may conflict with team_notes.md on naming conventions. Team notes take precedence for greenfield projects.*

<!-- FACILITATOR NOTE:
Contradictions planted in this file vs others:
1. NAMING: says snake_case for variables — conflicts with team_notes.md + public_guidelines.md (both say camelCase)
2. BOOLEAN PREFIX: old rule says is_/has_ prefix with snake_case — conflicts with public_guidelines.md which says isActive/hasPermission (camelCase booleans)
3. DUPLICATE: "Never log sensitive user data" also appears in team_notes.md with different wording
Copilot should resolve contradiction #1 toward camelCase (team consensus + modern Java convention).
-->
