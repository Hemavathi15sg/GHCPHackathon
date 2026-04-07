# AC Traceability Matrix — USER-142
> Gold Standard Reference for Exercise 2, Step 2
> Sources: mr/diff.patch mapped against output/ac-checklist.md

---

## Traceability Table

| AC | Status | File(s) in Diff | Method / Location | Evidence / Gap |
|----|--------|----------------|-------------------|----------------|
| AC-1 | ⚠️ Partial | `UserController.java`, `UserService.java` | `UserController.register()`, `UserService.register()` | HTTP 201 and user ID returned ✅; but password validation only checks length (`< 8`) — uppercase and digit requirements are **not implemented** |
| AC-2 | ✅ Complete | `UserController.java`, `UserService.java` | `UserController.register()` catch block, `UserService.register()` duplicate check | `DuplicateEmailException` thrown in service; mapped to HTTP 409 in controller with `e.getMessage()` |
| AC-3 | ⚠️ Partial | `UserService.java` | `UserService.register()` | `BCryptPasswordEncoder.encode()` used for storage ✅; however **`request.getPassword()` is logged in plain text** on line 26 — direct violation of AC-3 |
| AC-4 | ❌ Missing | N/A | N/A | No rate limiting logic in diff. No Redis dependency added. No `@RateLimiter` or filter present. |
| AC-5 | ❌ Missing | N/A | N/A | No notification service call, no async event publisher, no email dispatch of any kind in diff. |

---

## Summary

| Status | Count | ACs |
|--------|-------|-----|
| ✅ Complete | 1 | AC-2 |
| ⚠️ Partial | 2 | AC-1, AC-3 |
| ❌ Missing | 2 | AC-4, AC-5 |

**Overall AC coverage: 40% complete, 40% partial, 20% missing.**

---

## Key Gaps for Review

1. **AC-1 gap:** Password must enforce ≥1 uppercase AND ≥1 digit — currently only length is checked. Regex or `@Pattern` annotation needed.
2. **AC-3 gap (critical):** Plain-text password logged on `UserService.java` line 26 is both an AC violation and the SonarQube blocker. Must be fixed before merge.
3. **AC-4:** Entire rate-limiting feature is absent. REQUEST CHANGES required.
4. **AC-5:** Async email dispatch not implemented. Depending on sprint scope, this may be an agreed stub — but must be documented or tracked.
