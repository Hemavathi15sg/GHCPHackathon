# PITest Mutation Testing Report
**Project:** user-management-service
**Branch:** feature/USER-142-registration
**Report date:** 2026-04-05

---

## Summary

| Metric | Value |
|--------|-------|
| Mutations Generated | 18 |
| Mutations Killed | 13 |
| Mutations Survived | 3 |
| Mutations Timed Out | 2 |
| **Mutation Score** | **72%** (threshold: 80%) |
| **Status** | ❌ Below threshold |

---

## Surviving Mutants (requires investigation)

### Mutant 1 — Boundary condition not killed
- **File:** `UserService.java`
- **Line:** 31
- **Original:** `if (request.getPassword().length() < 8)`
- **Mutant:** `if (request.getPassword().length() <= 8)` *(changed `<` to `<=`)*
- **Status:** SURVIVED
- **Meaning:** No test uses a password of exactly 8 characters — the boundary case is untested.
- **Fix:** Add a test with a password of exactly 8 characters (should pass) and 7 characters (should fail).

### Mutant 2 — Negation of duplicate check not killed
- **File:** `UserService.java`
- **Line:** 28
- **Original:** `if (userRepository.existsByEmail(request.getEmail()))`
- **Mutant:** `if (!userRepository.existsByEmail(request.getEmail()))` *(negated condition)*
- **Status:** SURVIVED
- **Meaning:** The test for duplicate email does not assert the correct HTTP status or exception type precisely enough for the mutant to be caught.
- **Fix:** Assert specifically on exception type and message in the duplicate-email test.

### Mutant 3 — Return value mutation not killed
- **File:** `UserService.java`
- **Line:** 47
- **Original:** `return new RegisterResponse(user.getId());`
- **Mutant:** `return new RegisterResponse(null);` *(replaced return value)*
- **Status:** SURVIVED
- **Meaning:** The test for successful registration does not assert that the returned ID is non-null.
- **Fix:** Add `assertNotNull(response.getId())` to the success test case.
