# SonarQube Analysis Report
**Project:** user-management-service
**Branch:** feature/USER-142-registration
**Analysis date:** 2026-04-05
**Quality Gate:** ❌ FAILED

---

## Summary

| Metric | Value | Status |
|--------|-------|--------|
| Bugs | 1 | ❌ Blocker |
| Vulnerabilities | 0 | ✅ |
| Code Smells | 3 | ⚠️ |
| Coverage | 45% (UserService) | ❌ Below threshold (80%) |
| Duplications | 0% | ✅ |

---

## 🔴 Blocker (1)

**[BUG] Plain-text password exposed in log statement**
- **File:** `src/main/java/com/example/service/UserService.java`
- **Line:** 26
- **Rule:** `java:S2068` — Credentials should not be hard-coded or logged
- **Detail:** `log.info("Registering user: email={}, password={}", request.getEmail(), request.getPassword())` logs the raw password. This is a critical security violation.
- **Fix:** Remove `request.getPassword()` from all log statements.

---

## 🟠 Major Issues (2)

**[CODE SMELL] Missing input validation on email format**
- **File:** `src/main/java/com/example/service/UserService.java`
- **Line:** 28–35
- **Detail:** Email format is not validated before persistence. Malformed emails will be stored.
- **Fix:** Add `@Email` annotation on `RegisterRequest.email` and validate in service layer.

**[CODE SMELL] Cognitive complexity too high in `register()` method**
- **File:** `src/main/java/com/example/service/UserService.java`
- **Method:** `register(RegisterRequest)`
- **Complexity:** 12 (threshold: 10)
- **Fix:** Extract validation logic into a private `validateRequest()` method.

---

## 🟡 Minor Issues (1)

**[CODE SMELL] Unused import**
- **File:** `src/main/java/com/example/controller/UserController.java`
- **Line:** 9
- **Detail:** `import java.util.Map` is unused after refactoring.
- **Fix:** Remove the unused import.
