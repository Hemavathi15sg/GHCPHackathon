# Quality Gates Summary — USER-142
> Gold Standard Reference for Exercise 2, Step 3
> Sources: reports/sonarqube.md, reports/jacoco.md, reports/pitest.md, reports/trivy.md

---

## Overall Recommendation: ❌ REQUEST CHANGES

Two blockers must be resolved before this MR can be merged.

---

## 🔴 Blockers (must fix before merge)

| # | Tool | Issue | File / Location |
|---|------|-------|----------------|
| B1 | SonarQube | Plain-text password logged (`request.getPassword()` in log statement) — `java:S2068` | `UserService.java` line 26 |
| B2 | Trivy | HIGH CVE-2022-22978 in `spring-security-crypto:5.7.3` (BCrypt auth bypass, CVSS 8.1) — introduced by this MR | `pom.xml` |

---

## 🟠 Warnings (should fix before merge)

| # | Tool | Issue | File / Location |
|---|------|-------|----------------|
| W1 | SonarQube | Missing email format validation before persistence | `UserService.java`, `register()` method |
| W2 | SonarQube | Cognitive complexity 12 in `register()` (threshold: 10) | `UserService.java` |
| W3 | JaCoCo | `UserService` line coverage 45% — below 80% CI gate | `UserService.java` |
| W4 | JaCoCo | `UserService` branch coverage 32% — duplicate email path and password-too-short false path not tested | `UserService.java` |
| W5 | PITest | Mutation score 72% — boundary condition on password length not killed | `UserService.java` line 31 |
| W6 | PITest | Negation of duplicate check not killed — test assertions too weak | `UserService.java` line 28 |
| W7 | PITest | Return value mutation not killed — `response.getId()` not asserted as non-null | `UserService.java` line 47 |

---

## 🟡 Informational (track, not blocking)

| # | Tool | Issue |
|---|------|-------|
| I1 | SonarQube | Unused import in `UserController.java` |
| I2 | Trivy | MEDIUM CVE-2023-34034 in `spring-boot-starter-web:3.0.6` (path traversal) — pre-existing, not introduced here |
| I3 | Trivy | 2 LOW CVEs in pre-existing transitive dependencies |

---

## Tool-by-Tool Summary

| Tool | Gate | Status | Key Finding |
|------|------|--------|-------------|
| SonarQube | No new blockers | ❌ FAILED | 1 blocker: password logged in plain text |
| JaCoCo | ≥80% line coverage | ❌ FAILED | UserService at 45% |
| PITest | ≥80% mutation score | ❌ FAILED | 72% — 3 surviving mutants |
| Trivy | No HIGH CVEs introduced | ❌ FAILED | HIGH CVE in dependency added by this MR |
