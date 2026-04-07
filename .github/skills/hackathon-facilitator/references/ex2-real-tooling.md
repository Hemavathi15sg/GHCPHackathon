# Exercise 2 — Real Tooling Setup Guide

> This guide replaces simulated MD report files with REAL tool output on the actual Java repo.

---

## Architecture: One Real Java Repo, All Real Tools

```
user-management-service/   (GitHub repo — facilitator creates this)
├── pom.xml                (JaCoCo + PITest plugins pre-configured)
├── src/main/java/...      (Spring Boot application — main branch)
└── src/test/java/...      (Intentionally incomplete tests)

GitHub PR: feature/USER-142-user-registration
  → Adds password hashing, duplicate check
  → Plants bugs: logged plain-text password, missing validation
  → Missing ACs: rate limiting (AC-4), email confirmation (AC-5)
```

Participants:
1. Fetch the PR via GitHub MCP (no local clone needed for the review)
2. OR clone the repo and checkout the PR branch for tool runs
3. Run SonarLint (VS Code extension — no config needed)
4. Run `mvn verify` → JaCoCo report
5. Run `mvn pitest:mutationCoverage` → PITest report
6. Run `trivy fs .` → Trivy vulnerability report
7. Use Copilot Chat to analyze all findings

---

## Step 0 — Set Up the Java Repo (Facilitator Only)

### Create the repo on GitHub

1. Copy all files from `.github/skills/hackathon-facilitator/assets/java-repo/` into a new directory
2. Initialize git and push to GitHub as `user-management-service`:

```powershell
cd user-management-service
git init
git add .
git commit -m "feat: initial user management service (main branch)"
git remote add origin https://github.com/<org>/user-management-service.git
git push -u origin main
```

### Create the PR branch

```powershell
git checkout -b feature/USER-142-user-registration

# Apply the PR changes — either manually per PR_CHANGES.md
# OR apply the diff:
git apply ../.github/skills/hackathon-facilitator/assets/exercise2/diff.patch

git add .
git commit -m "feat(USER-142): add password hashing and duplicate email check"
git push origin feature/USER-142-user-registration
```

### Open the PR on GitHub

- Title: `feat(USER-142): Add secure user registration`
- Base: `main` → Compare: `feature/USER-142-user-registration`
- Description: Reference the Jira ticket (AC-1 through AC-5)

### Create the Jira ticket

In your Jira project, create a Story ticket matching the content of `assets/exercise2/ticket.md`. Note the ticket URL — participants will fetch it using Jira MCP.

---

## Step 1 — Fetch the PR via GitHub MCP

Participants use Copilot Chat with the GitHub MCP server configured.

**Prerequisite in VS Code `settings.json`:**
```json
{
  "mcp.servers": {
    "github": {
      "type": "http",
      "url": "https://api.githubcopilot.com/mcp/",
      "headers": {
        "Authorization": "Bearer <GITHUB_TOKEN>"
      }
    }
  }
}
```

**Copilot Chat prompts:**

```
List open pull requests on repository <org>/user-management-service.
```

```
Get the full diff for pull request #<number> on <org>/user-management-service. Save the diff content for analysis.
```

```
Get the contents of src/main/java/com/example/service/UserService.java from the feature/USER-142-user-registration branch of <org>/user-management-service.
```

---

## Step 2 — Fetch the Jira Ticket via Jira MCP

**Prerequisite:** Atlassian MCP configured (see `ex1-option-a-real-sources.md`).

```
Fetch the details of Jira ticket USER-142 including the description, acceptance criteria, and definition of done.
```

Then continue with the Exercise 2 Step 1 prompt to extract the AC checklist.

---

## Step 3 — SonarLint (VS Code Extension)

SonarLint runs automatically in the editor — no server or Maven command needed.

### Install

```powershell
code --install-extension SonarSource.sonarlint-vscode
```

Or search "SonarLint" in the VS Code Extensions panel.

### How to use

1. Open the PR branch files in VS Code (`git checkout feature/USER-142-user-registration`)
2. Open `src/main/java/com/example/service/UserService.java`
3. SonarLint automatically highlights issues with colored underlines
4. Open the **Problems** panel (`Ctrl+Shift+M` / `Cmd+Shift+M`) to see all issues

**Expected findings on the PR branch:**
- `java:S2068` — Password/credential logged in plain text (line 26 of UserService)
- `java:S2221` — Catching `Exception` is too broad (UserController)
- `java:S3776` — Cognitive complexity too high in `register()` method (if threshold crossed)

### Copilot prompt to analyze SonarLint output

```
Here are the SonarLint issues from the Problems panel for this PR branch:
[paste the list from Problems panel]

Categorize them as: Blockers (must fix before merge) | Warnings (should fix) | Informational (optional). For each blocker, explain the security or correctness risk.
```

---

## Step 4 — JaCoCo (Maven Coverage Report)

### Prerequisites

- Java 17+ installed
- Maven 3.8+ installed
- The `pom.xml` in `user-management-service/` already has JaCoCo configured

### Run the report

```powershell
# From the user-management-service directory (PR branch checked out)
mvn clean verify

# If JaCoCo minimum coverage check fails (expected — coverage is low)
# Use -DskipTests=false to ensure tests run but ignore the check gate:
mvn clean test jacoco:report
```

### View the report

Open `target/site/jacoco/index.html` in a browser. The report shows:
- `UserService`: ~40–45% line coverage (below 80% threshold)
- `UserController`: ~80–85% line coverage

### Copilot prompt to analyze JaCoCo output

```
Here is the JaCoCo coverage report summary for the PR branch:

UserService: 45% line coverage, 32% branch coverage
UserController: 85% line coverage, 75% branch coverage

The 80% threshold is NOT met for UserService. Identify:
1. Which code paths are untested based on the service logic
2. What test cases are missing
3. Whether the coverage gap is a risk for this PR
```

Or attach the HTML as text and paste into Copilot Chat for a more detailed analysis.

---

## Step 5 — PITest (Mutation Testing)

### Run PITest

```powershell
# From user-management-service directory (PR branch checked out)
# Ensure tests pass first:
mvn clean test

# Then run mutation testing:
mvn pitest:mutationCoverage
```

**Note:** PITest takes 3–5 minutes to run. It runs the tests multiple times with code mutations.

### View the report

Open `target/pit-reports/<timestamp>/index.html` in a browser.

### Copilot prompt to analyze PITest output

```
Here is the PITest mutation report for com.example.service.UserService:

Mutations generated: 18
Mutations killed: 13
Mutations survived: 3 (boundary condition on password length, duplicate email negation, null return value)
Mutation score: 72% (threshold: 80%)

For each surviving mutant:
1. Explain what logic gap the surviving mutant reveals
2. Write the specific test assertion needed to kill it
```

Or paste the XML from `target/pit-reports/.../mutations.xml` directly into Copilot Chat.

---

## Step 6 — Trivy (Security Vulnerability Scan)

### Install Trivy

**Windows (with Chocolatey):**
```powershell
choco install trivy
```

**Windows (with Scoop):**
```powershell
scoop install trivy
```

**Windows (direct download):**
Download from https://github.com/aquasecurity/trivy/releases

### Run Trivy on the PR branch

```powershell
# From user-management-service directory (PR branch checked out)

# Scan filesystem for vulnerabilities in dependencies
trivy fs --scanners vuln .

# Save as JSON for Copilot to analyze
trivy fs --scanners vuln --format json --output trivy-report.json .
```

### Expected findings on the PR branch

- **HIGH**: `CVE-2022-22978` in `spring-security-crypto:5.7.3` (added by the PR — should be 6.x managed by Spring Boot BOM)
- **MEDIUM**: One or more pre-existing CVEs in Spring Boot 3.2.4 dependencies

### Copilot prompt to analyze Trivy output

```
Here is the Trivy vulnerability scan output for the PR branch (the feature branch adds spring-security-crypto:5.7.3 to pom.xml):
[paste trivy-report.json content or terminal output]

Identify:
1. Which CVEs are BLOCKERS for this PR (HIGH or CRITICAL introduced by this PR's dependency changes)
2. Which CVEs are pre-existing and should be tracked separately
3. Recommended fix for each blocker
```

---

## Connecting All Tool Outputs (Step 3 of the Exercise)

After running all tools, participants create `output/quality-gates.md` by asking:

```
I have run all quality tools on the PR branch feature/USER-142-user-registration. Here are the findings:

SonarLint: [paste Problems panel]
JaCoCo: UserService 45% line coverage (below 80% threshold)
PITest: 72% mutation score, 3 surviving mutants
Trivy: 1 HIGH CVE in spring-security-crypto:5.7.3

Summarize as quality-gates.md with sections: Blockers | Warnings | Informational.
Recommend: Approve or Request Changes, with justification.
```

---

## Facilitator Pre-session Checklist (Real Tooling)

- [ ] `user-management-service` repo pushed to GitHub with `main` branch
- [ ] PR branch `feature/USER-142-user-registration` exists and has an open PR
- [ ] Jira ticket USER-142 created with 5 ACs
- [ ] GitHub MCP configured and tested on facilitator machine
- [ ] Atlassian MCP configured and tested (if using Jira MCP)
- [ ] Java 17 and Maven 3.8+ installed on all participant machines
- [ ] Trivy installed on all participant machines
- [ ] SonarLint VS Code extension installed on all participant machines
- [ ] Tested `mvn clean test jacoco:report` — runs without errors
- [ ] Tested `mvn pitest:mutationCoverage` — completes (allow 5 min)
- [ ] Tested `trivy fs .` — runs and shows CVE in spring-security-crypto
- [ ] Have Option B report MD files as backup in case tool setup fails
