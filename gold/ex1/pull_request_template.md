## Pull Request Checklist

> Complete all items before requesting review. Leave unchecked items with a comment explaining why.

### Security
- [ ] No secrets, API keys, or passwords hardcoded in source or config files
- [ ] All user inputs validated at the API/service boundary
- [ ] Parameterized queries used — no SQL string concatenation
- [ ] No PII, passwords, or tokens logged
- [ ] Security-sensitive changes have a second reviewer assigned

### Testing
- [ ] All new/changed public methods have unit tests with mocked dependencies
- [ ] Line coverage for changed classes meets ≥80% threshold (CI gate passes)
- [ ] At least one error path tested per service method
- [ ] Test names follow `methodName_scenario_expectedResult` convention
- [ ] No flaky tests (no reliance on system time, external services, or random values)

### Error Handling
- [ ] No empty catch blocks
- [ ] Specific exception types caught (no bare `catch (Exception e)`)
- [ ] Stack trace logged at ERROR level before rethrowing
- [ ] Third-party exceptions wrapped in internal domain types

### Logging
- [ ] Structured JSON logger used throughout — no `System.out.println`
- [ ] `traceId` / correlation ID included in all log statements
- [ ] No passwords, tokens, or PII in any log statement

### Performance
- [ ] No DB queries inside loops (N+1 pattern absent)
- [ ] Timeouts set on all external HTTP calls
- [ ] List-returning endpoints are paginated

### Code Quality
- [ ] Naming follows camelCase (variables/methods), PascalCase (classes), UPPER_SNAKE_CASE (constants)
- [ ] No generic variable names (`data`, `temp`, `obj`, `x`)
- [ ] Self-reviewed before requesting review

### Backward Compatibility
- [ ] No breaking changes to public API contracts
- [ ] Database migration scripts included if schema changed
- [ ] Dependent services notified if contracts changed
