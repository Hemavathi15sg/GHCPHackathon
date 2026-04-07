# Inline Review Comments — USER-142
> Gold Standard Reference for Exercise 2, Step 4
> Sources: mr/diff.patch + output/ac-traceability.md + output/quality-gates.md

---

## Comment 1 — Critical Security Violation

**File:** `src/main/java/com/example/service/UserService.java`
**Method:** `register(RegisterRequest)`
**Issue:** Plain-text password logged in production log line (line 26)
**Why it matters:** This is a blocker in SonarQube (`java:S2068`) and directly violates AC-3. Raw passwords in log files are accessible to anyone with log aggregator access (Splunk, Kibana, CloudWatch) and create a critical compliance breach under GDPR and PCI-DSS.

```java
// Current (VIOLATION):
log.info("Registering user: email={}, password={}", request.getEmail(), request.getPassword());

// Suggested fix:
log.info("Registering user: email={}", request.getEmail());
```

---

## Comment 2 — Incomplete AC-1 Implementation

**File:** `src/main/java/com/example/service/UserService.java`
**Method:** `register(RegisterRequest)`
**Issue:** Password validation only checks length (`< 8`); AC-1 also requires ≥1 uppercase and ≥1 digit
**Why it matters:** A password like `password1234` passes the current check but violates the stated acceptance criterion. The AC will be marked Partial until this is fixed.

```java
// Current (PARTIAL):
if (request.getPassword().length() < 8) {
    throw new IllegalArgumentException("Password too short");
}

// Suggested fix:
private void validatePassword(String password) {
    if (password.length() < 8
            || !password.matches(".*[A-Z].*")
            || !password.matches(".*[0-9].*")) {
        throw new IllegalArgumentException(
            "Password must be ≥8 characters, contain at least one uppercase letter and one digit");
    }
}
```

---

## Comment 3 — HIGH CVE Introduced by This MR

**File:** `pom.xml`
**Location:** `spring-security-crypto:5.7.3` dependency (added in this MR)
**Issue:** CVE-2022-22978 — BCrypt authentication bypass (CVSS 8.1 HIGH)
**Why it matters:** Trivy flags this as a HIGH severity CVE introduced by this MR. Versions prior to 5.7.8 have a flaw that can allow null-byte injection to bypass password comparison. Since this MR's core feature is password hashing, the vulnerability is directly relevant and must be fixed before merge.

```xml
<!-- Current (VULNERABLE): -->
<version>5.7.3</version>

<!-- Suggested fix: -->
<version>5.7.8</version>
```

---

## Comment 4 — Missing AC-4: Rate Limiting Not Implemented

**File:** N/A (missing feature)
**Issue:** AC-4 (rate limiting: max 5 requests/IP/minute, HTTP 429 on excess) has no implementation in the diff
**Why it matters:** Without rate limiting, this endpoint is trivially brute-forceable for password guessing and vulnerable to account enumeration attacks. This is a functional gap, not just a style issue.

**Suggested approach:**
- Add `spring-boot-starter-data-redis` dependency
- Implement a `RateLimitingFilter` using token bucket pattern, keyed by `X-Forwarded-For` / `RemoteAddr`
- Return `429 Too Many Requests` with `Retry-After` header

> This AC cannot be marked Complete until the feature is implemented and tested.

---

## Comment 5 — Missing AC-5: Async Email Not Implemented

**File:** N/A (missing feature)
**Issue:** AC-5 (send confirmation email asynchronously on registration) has no implementation in the diff
**Why it matters:** The ticket explicitly requires fire-and-forget email dispatch. Leaving it out means the acceptance criterion is unmet. If this was intentionally deferred, it must be tracked as a follow-up ticket and the AC marked as out-of-scope for this MR with the team's agreement.

**Suggested approach:**
- Publish a `UserRegisteredEvent` after `userRepository.save()`
- Handle in `@EventListener` in a separate `EmailNotificationService` with `@Async`

---

## Comment 6 — Insufficient Test Assertions (PITest Survivors)

**File:** (test classes — not in diff but implied)
**Issue:** 3 PITest mutants survived, indicating weak test assertions
**Why it matters:** Surviving mutants mean the tests would pass even if the production code had subtle logic errors. Three specific gaps exist:
1. No test uses password of exactly 8 chars (boundary condition)
2. Duplicate email test doesn't assert exception type and message precisely
3. Success test doesn't assert `response.getId()` is non-null

**Suggested fixes:**
```java
@Test
void register_passwordExactly8Chars_succeeds() { /* boundary test */ }

@Test
void register_duplicateEmail_throwsDuplicateEmailException() {
    assertThrows(DuplicateEmailException.class, () -> userService.register(request));
    // assert specific message too
}

@Test
void register_validInput_returnsNonNullId() {
    RegisterResponse response = userService.register(validRequest);
    assertNotNull(response.getId()); // kills Mutant 3
}
```
