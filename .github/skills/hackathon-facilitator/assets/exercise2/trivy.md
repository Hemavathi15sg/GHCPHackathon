# Trivy Security Scan Report
**Project:** user-management-service
**Branch:** feature/USER-142-registration
**Scan date:** 2026-04-05
**Scanner:** Trivy v0.49.1

---

## Summary

| Severity | Count |
|----------|-------|
| CRITICAL | 0 |
| HIGH | 1 |
| MEDIUM | 1 |
| LOW | 2 |
| UNKNOWN | 0 |

---

## 🔴 HIGH Severity CVE

**CVE-2022-22978 — Spring Security BCrypt Authentication Bypass**
- **Affected dependency:** `org.springframework.security:spring-security-crypto:5.7.3`
- **Fixed in:** `5.7.8` or later
- **CVSS Score:** 8.1 (HIGH)
- **Description:** In versions prior to 5.7.8, BCryptPasswordEncoder has a flaw in input validation that can allow an attacker to bypass password checks under specific conditions related to null byte injection.
- **Location:** `pom.xml` (introduced in this MR)
- **Recommendation:** Upgrade `spring-security-crypto` to `5.7.8` or `6.1.x`. Audit all password comparison logic.

---

## 🟠 MEDIUM Severity CVE

**CVE-2023-34034 — Spring Boot Path Traversal**
- **Affected dependency:** `org.springframework.boot:spring-boot-starter-web:3.0.6`
- **Fixed in:** `3.0.9` or later
- **CVSS Score:** 5.3 (MEDIUM)
- **Description:** Under specific WebMVC configurations, path traversal is possible via crafted request URLs. Affects applications that serve static resources.
- **Location:** `pom.xml` (pre-existing dependency, not introduced in this MR)
- **Recommendation:** Upgrade Spring Boot to `3.0.9+`. Lower priority as static resource serving may not be enabled.

---

## 🟡 LOW Severity (2)

- **CVE-2023-20860** — Spring Framework Expression Injection (LOW, pre-existing)
- **CVE-2022-1471** — SnakeYAML deserialization (LOW, transitive dependency)

---

## Recommendation

Block merge until HIGH CVE (`CVE-2022-22978`) is resolved. MEDIUM CVE should be tracked in backlog with target fix in next sprint.
