# Copilot Instructions — Gold Standard Reference
# File: .github/copilot-instructions.md
# This is the FACILITATOR'S reference answer. Participants generate their own version.

---

## Naming
- MUST use camelCase for variables and local functions; PascalCase for classes; UPPER_SNAKE_CASE for constants.
- MUST NOT use generic names: `data`, `temp`, `obj`, `x`, `thing`.
- SHOULD name booleans as questions: `isActive`, `hasPermission`.

## Error Handling
- MUST catch specific exception types — never bare `catch (Exception e)` with empty body.
- MUST log full stack trace at ERROR level before rethrowing.
- MUST wrap third-party exceptions into internal domain exception types.

## Logging
- MUST use structured JSON logger — never `System.out.println` or `print()`.
- MUST include `traceId` / request correlation ID in every log line.
- MUST NOT log passwords, tokens, or PII under any circumstances.
- SHOULD log INFO for normal operations, WARN for recoverable issues, ERROR for failures.

## Security
- MUST NOT hardcode secrets, API keys, or passwords — use environment variables or a secrets manager.
- MUST use parameterized queries — never string-concatenated SQL.
- MUST validate all inputs at the API/service boundary.
- SHOULD apply principle of least privilege to service accounts.

## Testing
- MUST achieve ≥80% line coverage; CI enforces this gate.
- MUST write unit tests for all public service methods with mocked external dependencies.
- MUST follow naming: `methodName_scenario_expectedResult`.
- SHOULD cover at least one error path per integration test.

## Performance
- MUST NOT execute DB queries inside loops — use batch fetch or JOIN.
- MUST set timeouts on all external HTTP calls (default: 5s).
- SHOULD paginate all endpoints returning list results.
