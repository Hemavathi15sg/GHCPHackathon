# Exercise 2 Asset Templates

This folder contains files for `expert-mr-reviewer-lab`.

## Delivery Mode: Real Tools vs Mock Files

| | Real (Preferred) | Mock (Fallback — these files) |
|---|---|---|
| PR diff | GitHub MCP → fetch from `user-management-service` PR | `diff.patch` |
| Jira ticket | Atlassian MCP → fetch USER-142 | `ticket.md` |
| SonarQube/SonarLint | VS Code SonarLint extension on PR branch | `sonarqube.md` |
| JaCoCo | `mvn clean test jacoco:report` on PR branch | `jacoco.md` |
| PITest | `mvn pitest:mutationCoverage` on PR branch | `pitest.md` |
| Trivy | `trivy fs --scanners vuln .` on PR branch | `trivy.md` |
| Java repo | `user-management-service/` pushed to GitHub | N/A |

For real tooling setup: see [`../../references/ex2-real-tooling.md`](../../references/ex2-real-tooling.md)
Java repo template: see [`../../../../user-management-service/`](../../../../user-management-service/)

**The mock files in this folder remain the fallback** — use them if tool setup fails during the session.

---

## Mock Files (Option B fallback)

| File | Purpose | Quality bar |
|------|---------|-------------|
| `jira/ticket.md` | Simulated Jira story with acceptance criteria | Must have 4–6 specific, verifiable ACs |
| `mr/diff.patch` | Simulated code diff implementing the ticket | ≤150 lines; must implement some ACs fully, some partially, leave ≥1 AC missing |
| `reports/sonarqube.md` | Simulated SonarQube output | Include 1 blocker, 2 major issues, 1 minor |
| `reports/jacoco.md` | Simulated JaCoCo coverage report | Show coverage below threshold in at least one class |
| `reports/pitest.md` | Simulated PITest mutation report | Show mutation score and ≥2 surviving mutants |
| `reports/trivy.md` | Simulated Trivy security scan | Include ≥1 HIGH CVE and ≥1 MEDIUM CVE |
| `copilot-instructions-seed.md` | Seed `.github/copilot-instructions.md` for this repo | Review-focused MUST/SHOULD rules |

## Coherence rules (critical)

The 6 files must tell a **single consistent story**:

1. `ticket.md` defines 5 ACs for a feature (e.g., "Add user authentication endpoint")
2. `diff.patch` modifies real-looking files that implement that feature — partially
3. `sonarqube.md` flags issues visible in the diff (same file names, same method names)
4. `jacoco.md` shows low coverage for the new service class introduced in the diff
5. `pitest.md` shows surviving mutants in the same service class
6. `trivy.md` flags a CVE in a dependency added or used in the diff

**If the story is incoherent, Step 2 traceability will produce nonsense output.**

## Diff size guideline

- Target: **80–120 lines** of diff content
- Max: **150 lines** — beyond this, Copilot context quality degrades in Step 4
- Must include: new file additions, method changes, and at least one dependency reference

## Content generation prompt

```
Generate a coherent set of starter files for a GitHub Copilot hackathon exercise simulating an MR code review. The feature being reviewed is: "Add a POST /api/users/register endpoint to a Java Spring Boot application."

Create:
1. jira/ticket.md — Story: "As a new user, I want to register with email and password." Include 5 acceptance criteria that are specific and verifiable from code.

2. mr/diff.patch — A realistic git diff implementing the endpoint. Include: UserController.java (new POST endpoint), UserService.java (register method with validation), UserRepository.java (save call), pom.xml (add bcrypt dependency). Keep the diff under 130 lines. Intentionally leave AC #4 (rate limiting) and AC #5 (email confirmation) unimplemented so traceability shows Missing.

3. reports/sonarqube.md — SonarQube output. Flag: 1 blocker (password logged in plain text in UserService), 2 major (missing input validation on email format, cognitive complexity too high in register method), 1 minor (unused import).

4. reports/jacoco.md — JaCoCo output. Show UserService at 45% line coverage (below 80% threshold), UserController at 85%.

5. reports/pitest.md — PITest mutation report. Show 3 surviving mutants in UserService.register() — boundary condition on password length check not killed.

6. reports/trivy.md — Trivy scan. Flag HIGH CVE in bcrypt library version added in pom.xml; flag MEDIUM CVE in Spring Boot version.

Make all file names and class names consistent across all 6 files.
```
