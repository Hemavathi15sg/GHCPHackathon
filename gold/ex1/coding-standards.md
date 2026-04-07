# Coding Standards — User Management Service
> Gold Standard Reference for Exercise 1, Step 2
> Consolidated from: confluence_export.md, public_guidelines.md, team_notes.md
> Contradictions resolved toward strictest security-safe option.

---

## 1. Naming

| Rule | Rationale |
|------|-----------|
| Use **camelCase** for all variable names and local function names | Team consensus (team_notes.md + public_guidelines.md); modern Java convention |
| Use **PascalCase** for all class names | Universal Java convention — no conflict across sources |
| Use **UPPER_SNAKE_CASE** for all constants | Universal Java convention — no conflict across sources |
| Do NOT use generic names: `data`, `temp`, `obj`, `x`, `thing`, `info` | Generic names require reading the surrounding code to understand purpose |
| Name boolean variables as questions: `isActive`, `hasPermission`, `canRetry` | Conflict resolved: old snake_case prefix `is_active` is [DEPRECATED]; camelCase `isActive` is the standard |
| File names must match the primary class name | No conflict across sources |

> **Conflict resolved:** `confluence_export.md` specified `snake_case`; `public_guidelines.md` and `team_notes.md` both specified `camelCase`. **Chosen: camelCase** — team consensus and Java community standard.

---

## 2. Error Handling

| Rule | Rationale |
|------|-----------|
| Catch the **most specific exception type** available; never use bare `catch (Exception e)` unless handling all modes | Broad catches hide bugs and make debugging difficult |
| Never swallow exceptions — every catch block must log, rethrow, or take corrective action | Empty catch blocks silently discard failure information |
| Log the full stack trace at ERROR level before rethrowing | Stack traces are essential for root-cause analysis |
| Wrap third-party exceptions into internal domain exception types (e.g., `ServiceException`) | Isolates external API contracts from internal contracts |
| Use try-with-resources for resource management; `finally` only when try-with-resources is insufficient | Prevents resource leaks |

**Good example:**
```java
try {
    externalService.call();
} catch (ExternalException e) {
    log.error("External service failed", e);
    throw new ServiceException("Downstream failure", e);
}
```

**Bad example:**
```java
try {
    externalService.call();
} catch (Exception e) {
    // swallowed
}
```

---

## 3. Logging

| Rule | Rationale |
|------|-----------|
| Use structured JSON logger exclusively — never `System.out.println` or `print()` | Structured logs are machine-parseable; raw println is lost in production aggregators |
| Always include `traceId` / correlation ID in every log line | Enables distributed tracing across services |
| Always include: `timestamp`, `level`, `service`, `traceId`, `message` | Standard fields required for log aggregation (ELK / Splunk) |
| **MUST NOT** log passwords, tokens, or PII under any circumstances | Compliance (GDPR, PCI-DSS); passwords in logs are a critical security breach |
| Use INFO for normal operations, WARN for recoverable issues, ERROR for failures | Consistent log levels enable accurate alerting thresholds |

**Bad example:**
```java
System.out.println("Registering: " + email + " password=" + password); // VIOLATION: println + PII
```

---

## 4. Security

| Rule | Rationale |
|------|-----------|
| Never hardcode secrets, API keys, passwords, or tokens — use environment variables or a secrets manager | Hardcoded secrets are trivially discoverable in git history |
| Use parameterized queries or ORM — never concatenate user input into SQL | SQL injection is OWASP Top 10 |
| Validate all user input at the API/service boundary | Defense-in-depth; untrusted input must never reach the persistence layer unvalidated |
| Apply principle of least privilege to service accounts and API tokens | Limits blast radius of a compromised credential |
| Sanitize output in web contexts to prevent XSS | OWASP Top 10; prevents script injection into browser contexts |
| Rotate secrets regularly; never commit secrets to version control | Reduces window of exposure for leaked credentials |

---

## 5. Testing

| Rule | Rationale |
|------|-----------|
| Maintain ≥80% line coverage — CI gate enforced | Minimum safety net for regression detection |
| Write unit tests for all public service methods with mocked external dependencies | Ensures fast, deterministic test suite |
| Test names must follow `methodName_scenario_expectedResult` (e.g., `register_duplicateEmail_throwsException`) | Conflict resolved: both naming patterns from sources carry same intent; chosen one consistent form |
| Cover at least one error path per integration test in addition to the happy path | Happy-path-only tests hide edge case bugs |
| Tests must be deterministic — no reliance on system time, random values, or real external services | Flaky tests erode confidence |
| Assert on specific return values and exception types — not just that no exception was thrown | Weak assertions allow mutants to survive |

---

## 6. Performance

| Rule | Rationale |
|------|-----------|
| Never execute DB queries inside loops — use batch fetch, JOIN, or `findAllById` | N+1 query patterns caused a production incident; this is a hard ban |
| Set timeouts on all external HTTP calls (default: 5 seconds) | Prevents thread exhaustion from unresponsive downstream services |
| Use pagination for all list-returning endpoints | Unbounded queries degrade DB and crash JVM heap on large datasets |
| Cache results for repeated read operations; document the TTL | Avoid caching without expiry; stale data must be bounded |
