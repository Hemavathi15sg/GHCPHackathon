# Public Coding Guidelines — Excerpt
*Source: Adapted from open-source team standards (CC-BY 4.0)*

---

## Naming

- Use **camelCase** for all variables and local function names. *(Conflicts with confluence_export.md: snake_case)*
- Class names must use **PascalCase**.
- Name booleans as questions: `isActive`, `hasPermission`, `canRetry`.
- Avoid generic names: `data`, `info`, `temp`, `obj`, `thing`.
- Constants: use `UPPER_SNAKE_CASE`.

---

## Error Handling

- Catch the **most specific exception type** possible, not `Exception`.
- Always provide a meaningful message when throwing.
- Use `finally` blocks to release resources (or prefer try-with-resources).
- Do not catch exceptions purely to log and re-throw unless you add context.

```python
# Good
try:
    result = parse_config(path)
except FileNotFoundError as e:
    raise ConfigurationError(f"Config not found at {path}") from e

# Bad
try:
    result = parse_config(path)
except Exception:
    pass
```

---

## Security

- **Validate all inputs** at the system boundary before processing.
- Use **parameterized queries** or ORMs — never build SQL by string concatenation.
- Apply the **principle of least privilege** for service accounts and API tokens.
- Rotate secrets regularly; never commit secrets to version control.
- Sanitize output in web contexts to prevent XSS.

---

## Testing

- Write **unit tests for all public methods**.
- Tests must be deterministic — no reliance on system time, random values, or external services unless mocked.
- Aim for **high branch coverage**, not just line coverage.
- Test names should describe behaviour: `givenInvalidInput_whenValidate_thenThrowsException`.

---

## Code Review

- All PRs must have at least one approval before merge.
- Comments must be actionable — no vague feedback like "fix this" without explanation.
- Security-sensitive changes require a second reviewer.

---

*Note: These guidelines are a baseline. Team-specific rules in confluence_export.md or team_notes.md take precedence where they are more specific.*

<!-- FACILITATOR NOTE:
Contradictions planted in this file vs others:
1. NAMING: says camelCase for variables — conflicts with confluence_export.md (snake_case)
2. TEST NAMING: says givenX_whenY_thenZ pattern — conflicts with confluence_export.md (methodName_scenario_expectedResult)
3. SECURITY: adds XSS sanitization and principle of least privilege — NOT mentioned in team_notes.md (security gap for Copilot to synthesize)
Copilot should consolidate test naming conventions and choose one. BOTH security rules should survive.
-->
