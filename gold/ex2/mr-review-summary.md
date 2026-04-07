# MR Review Summary — USER-142: Add User Registration Endpoint
> Gold Standard Reference for Exercise 2, Step 5
> Reviewer: Senior Engineer | Date: 2026-04-05

---

## Overall Verdict: ❌ REQUEST CHANGES

This MR cannot be merged in its current state. Two blockers must be resolved: a critical security vulnerability (plain-text password in logs) and a HIGH CVE introduced in the new dependency. Additionally, two acceptance criteria (AC-4, AC-5) are entirely unimplemented.

---

## AC Status

| AC | Status | Notes |
|----|--------|-------|
| AC-1 | ⚠️ Partial | Endpoint returns 201 with user ID ✅; password validation missing uppercase + digit check ❌ |
| AC-2 | ✅ Complete | 409 Conflict returned with descriptive message on duplicate email |
| AC-3 | ⚠️ Partial | BCrypt hashing implemented ✅; plain-text password logged on line 26 ❌ |
| AC-4 | ❌ Missing | Rate limiting not implemented — no Redis, no filter, no 429 response |
| AC-5 | ❌ Missing | Async email confirmation not implemented — no event publisher, no notification call |

---

## Top 3 Strengths

1. **Correct bcrypt usage:** `BCryptPasswordEncoder` is properly used for password hashing, satisfying the core security intent of AC-3. The encoder is injected via constructor — good design.
2. **Clean error mapping:** `DuplicateEmailException` is caught and correctly mapped to HTTP 409 with a human-readable message. AC-2 is fully satisfied.
3. **Repository design:** `UserRepository` interface is minimal and correct. Using Spring Data JPA's `existsByEmail` is idiomatic and avoids unnecessary data fetching.

---

## Top 3 Concerns

1. **Plain-text password in logs (Blocker):** `UserService.java` line 26 logs `request.getPassword()` directly. This is the highest-severity issue in the MR — it is flagged as a SonarQube blocker (`java:S2068`) and is a GDPR / PCI-DSS compliance breach. Any log aggregator access (Kibana, Splunk, CloudWatch) exposes user passwords.
2. **HIGH CVE in new dependency (Blocker):** `spring-security-crypto:5.7.3` introduced in `pom.xml` has a confirmed BCrypt authentication bypass (CVE-2022-22978, CVSS 8.1). This vulnerability is directly relevant to the feature being implemented. Upgrade to `5.7.8` immediately.
3. **2 of 5 ACs entirely missing:** AC-4 (rate limiting) and AC-5 (async email) are not implemented at all. If these are intentionally deferred, the ticket must be updated and the team must agree before merge. Shipping without rate limiting leaves the registration endpoint open to brute-force and enumeration attacks.

---

## Quality Gate Summary

| Tool | Status | Key Issue |
|------|--------|-----------|
| SonarQube | ❌ FAILED | Blocker: password logged in plain text |
| JaCoCo | ❌ FAILED | UserService at 45% coverage (threshold: 80%) |
| PITest | ❌ FAILED | 72% mutation score — 3 surviving mutants |
| Trivy | ❌ FAILED | HIGH CVE in dependency introduced by this MR |

---

## Next Steps

1. **Fix immediately (before re-review):**
   - Remove `request.getPassword()` from all log statements
   - Upgrade `spring-security-crypto` to `5.7.8` in `pom.xml`

2. **Fix before merge:**
   - Complete AC-1 password validation (add uppercase + digit checks)
   - Bring `UserService` test coverage to ≥80%
   - Add boundary test for 8-char password and assert non-null ID in success test

3. **Agree with team or track as follow-up:**
   - AC-4: Rate limiting — implement or create follow-up ticket with agreed delivery sprint
   - AC-5: Async email — implement or document as agreed stub with follow-up ticket

**This MR shows solid structural foundation. Resolve the two blockers and complete the missing ACs, and it will be in strong shape to merge.**
