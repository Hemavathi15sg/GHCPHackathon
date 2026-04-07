# PR Changes — feature/USER-142-user-registration

## Summary

This PR partially implements Jira ticket USER-142: "Add secure user registration endpoint."

**Implements:** AC-1, AC-2, AC-3 (with bugs)
**Missing:** AC-4 (rate limiting), AC-5 (email confirmation)

---

## Files Changed

### 1. `pom.xml`
**Change:** Adds `spring-security-crypto:5.7.3` dependency explicitly.

> ⚠️ BUG PLANTED: This version is NOT managed by Spring Boot BOM and contains CVE-2022-22978.
> Fix: Remove explicit version and let Spring Boot BOM manage it (ships with 6.x in Boot 3.2.4).

### 2. `src/main/java/com/example/service/UserService.java`
**Changes:**
- Adds `BCryptPasswordEncoder` to hash passwords before saving
- Adds duplicate email check using `userRepository.existsByEmail()`
- Adds password length validation (≥8 chars)
- Throws `DuplicateEmailException` on duplicate email

> ⚠️ BUG PLANTED #1: Line `log.info("Registering user: email={}, password={}", request.getEmail(), request.getPassword())` logs the plain-text password. Violates AC-3 and security rules.
> ⚠️ BUG PLANTED #2: Password validation only checks length — missing uppercase and digit requirement stated in AC-1.
> ⚠️ MISSING: AC-4 (rate limiting) — no `@RateLimiter` or Redis token bucket implemented.
> ⚠️ MISSING: AC-5 (email confirmation) — no async notification service call.

### 3. `src/main/java/com/example/controller/UserController.java`
**Changes:**
- Adds `catch (DuplicateEmailException e)` handler returning HTTP 409
- Adds broad `catch (Exception e)` handler returning HTTP 500

> ⚠️ CODE QUALITY: Broad `catch (Exception e)` is flagged by SonarLint (java:S2221).

### 4. `src/main/java/com/example/exception/DuplicateEmailException.java`
**Change:** New class — `RuntimeException` subclass for duplicate email scenario.

---

## What participants should find

### Via GitHub MCP (Step 1–2)
- PR diff shows all 4 files changed
- AC-4 and AC-5 are not traceable to any diff lines → mark as **Missing**
- AC-1 is **Partial** (length check only, no complexity validation)
- AC-2, AC-3 are **Complete** (but AC-3 has a bug that contradicts its intent)

### Via SonarLint (VS Code — Step 3)
- `java:S2068` → plain-text credential in log (BLOCKER)
- `java:S2221` → overly broad exception catch (MAJOR)
- `java:S3776` → cognitive complexity (MAJOR, if register() exceeds threshold)

### Via JaCoCo — `mvn clean verify` (Step 3)
- `UserService`: ~40–45% line coverage (only happy path tested on main branch)
- Coverage gate FAILS at 80% threshold

### Via PITest — `mvn pitest:mutationCoverage` (Step 3)  
- Mutation score: ~70–72%
- Surviving mutants: boundary on password length check, negated duplicate email condition, null return value

### Via Trivy — `trivy fs .` (Step 3)
- HIGH CVE: `CVE-2022-22978` in `spring-security-crypto:5.7.3`
- MEDIUM CVE: Pre-existing in transitive Spring Boot dependencies

---

## Creating This PR (Facilitator Steps)

```powershell
# 1. Start from main branch
git checkout main

# 2. Create feature branch
git checkout -b feature/USER-142-user-registration

# 3. Apply changes manually based on this file, OR apply the diff:
git apply .github/skills/hackathon-facilitator/assets/exercise2/diff.patch

# 4. Add the new exception class (not in diff.patch — add manually)
# Create: src/main/java/com/example/exception/DuplicateEmailException.java

# 5. Commit
git add .
git commit -m "feat(USER-142): add BCrypt password hashing and duplicate email detection"

# 6. Push and open PR
git push origin feature/USER-142-user-registration
# Open PR on GitHub: base=main, compare=feature/USER-142-user-registration
```
