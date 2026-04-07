# Expert MR Reviewer Lab

> **Exercise 2 — GitHub Copilot Hackathon**

Build a Copilot-powered merge request review workflow using a real Jira ticket, a code diff, and quality tool reports. Produce the five deliverables that a senior engineer would write before approving or rejecting an MR.

---

## Delivery Options

| | Option A — Real Tools | Option B — Mock Files |
|---|---|---|
| Jira ticket | Fetch live via Atlassian MCP | Use `jira/ticket.md` |
| MR diff | Fetch live via GitHub MCP | Use `mr/diff.patch` |
| SonarQube/SonarLint | VS Code SonarLint extension (auto) | Use `reports/sonarqube.md` |
| JaCoCo | `mvn clean test jacoco:report` | Use `reports/jacoco.md` |
| PITest | `mvn pitest:mutationCoverage` | Use `reports/pitest.md` |
| Trivy | `trivy fs --scanners vuln .` | Use `reports/trivy.md` |

Use **Option A** when participants have GitHub and Jira access and Java/Maven installed.
Use **Option B** for time-constrained sessions or to skip tool setup entirely.

---

## Repo Structure

```
expert-mr-reviewer-lab/
  jira/
    ticket.md              ← Jira story with acceptance criteria (Option B)
  mr/
    diff.patch             ← Code diff for the feature (Option B)
  reports/
    sonarqube.md           ← SonarQube analysis output (Option B)
    jacoco.md              ← JaCoCo coverage report (Option B)
    pitest.md              ← PITest mutation testing report (Option B)
    trivy.md               ← Trivy security scan output (Option B)
  output/
    (empty — you'll fill this across Steps 1–5)
  .github/
    copilot-instructions.md
  README.md
```

---

## Before You Start — MCP Setup (Option A only)

Skip this section if you are using Option B (mock files).

### GitHub MCP — for fetching PR diff

Add to VS Code `settings.json` (`Ctrl+Shift+P` → _Open User Settings JSON_):

```json
{
  "mcp.servers": {
    "github": {
      "type": "http",
      "url": "https://api.githubcopilot.com/mcp/",
      "headers": {
        "Authorization": "Bearer <YOUR_GITHUB_TOKEN>"
      }
    }
  }
}
```

### Atlassian MCP — for fetching the Jira ticket

```json
{
  "mcp.servers": {
    "atlassian": {
      "type": "http",
      "url": "https://mcp.atlassian.com/v1/mcp",
      "headers": {
        "Authorization": "Bearer <YOUR_ATLASSIAN_API_TOKEN>"
      }
    }
  }
}
```

Generate an Atlassian API token at: https://id.atlassian.com/manage-profile/security/api-tokens

After adding either server, restart VS Code and verify the server appears in the Copilot Chat **Tools** panel.

---

## Before You Start — Read the Diff Format (5 min)

The `mr/diff.patch` file uses standard git diff format:

| Symbol | Meaning |
|--------|---------|
| `---` | Original file (before this MR) |
| `+++` | Modified file (after this MR) |
| `@@` | Context chunk header — shows file + line range |
| `-` (line prefix) | Line that was **removed** |
| `+` (line prefix) | Line that was **added** |

> **Tip:** You don't need exact line numbers — file name + method name is enough for Copilot to reason about the diff.

---

## Tool Setup (Option A only)

### SonarLint — VS Code extension (auto-runs, no server needed)

```powershell
code --install-extension SonarSource.sonarlint-vscode
```

Or search **SonarLint** in the VS Code Extensions panel (`Ctrl+Shift+X`).

Once installed:
1. Checkout the PR branch: `git checkout feature/USER-142-user-registration`
2. Open `src/main/java/com/example/service/UserService.java`
3. SonarLint highlights issues automatically — view them in the **Problems** panel (`Ctrl+Shift+M`)

**Expected findings:** `java:S2068` (plain-text password logged), `java:S2221` (broad Exception catch)

---

### JaCoCo — Maven coverage report

**Prerequisites:** Java 17+ and Maven 3.8+ installed. The `pom.xml` in `user-management-service/` already has JaCoCo configured.

```powershell
# From user-management-service directory (PR branch checked out)
mvn clean test jacoco:report
```

Open `target/site/jacoco/index.html` in a browser to view coverage.

**Expected result:** `UserService` ~40–45% line coverage (below 80% threshold).

---

### PITest — Mutation testing

```powershell
# Ensure tests pass first
mvn clean test

# Then run mutation testing (takes 3–5 minutes)
mvn pitest:mutationCoverage
```

Open `target/pit-reports/<timestamp>/index.html` to view the mutation report.

**Expected result:** ~72% mutation score, 3 surviving mutants in `UserService`.

---

### Trivy — Security vulnerability scan

**Install on Windows:**

```powershell
# With Chocolatey
choco install trivy

# With Scoop
scoop install trivy

# Or download directly from https://github.com/aquasecurity/trivy/releases
```

**Run scan:**

```powershell
# From user-management-service directory (PR branch checked out)
trivy fs --scanners vuln .

# Save as JSON for Copilot to analyze
trivy fs --scanners vuln --format json --output trivy-report.json .
```

**Expected finding:** HIGH CVE `CVE-2022-22978` in `spring-security-crypto:5.7.3` added by the PR.

---

## Steps

### Step 1 — Extract AC Checklist from the Ticket

**Create:** `output/ac-checklist.md`

#### Option A — via Jira MCP

```
Fetch the details of Jira ticket USER-142 including description, acceptance criteria,
and definition of done.
```

Then:

```
From the Jira ticket content above, extract the acceptance criteria and rewrite them
into a checkbox checklist where each item is objectively verifiable from code or tests.
```

#### Option B — via local file

```
Read #file:jira/ticket.md. Extract acceptance criteria and rewrite them into a
checkbox checklist where each item is objectively verifiable from code or tests.
```

---

### Step 2 — Map Diff to Acceptance Criteria (Traceability)

**Create:** `output/ac-traceability.md`

#### Option A — via GitHub MCP

```
List open pull requests on repository <org>/user-management-service.
```

```
Get the full diff for pull request #<number> on <org>/user-management-service.
```

Then map it:

```
Using the PR diff above and #file:output/ac-checklist.md, map each acceptance criterion
to the specific files and methods in the diff that implement it.
Mark each AC as: Complete / Partial / Missing.
Exact line numbers are optional — file and method name is sufficient.
```

#### Option B — via local file

```
Using #file:mr/diff.patch and #file:output/ac-checklist.md, map each acceptance
criterion to the specific files and methods in the diff that implement it.
Mark each AC as: Complete / Partial / Missing.
Exact line numbers are optional — file and method name is sufficient.
```

> **Expect iteration here.** If output is vague, try one AC at a time.

---

### Step 3 — Summarize Quality Tool Signals

**Create:** `output/quality-gates.md`

#### Option A — paste real tool output

After running SonarLint, JaCoCo, PITest, and Trivy, paste all findings into one prompt:

```
I have run all quality tools on the PR branch feature/USER-142-user-registration.
Here are the findings:

SonarLint (Problems panel):
[paste list from Problems panel]

JaCoCo: UserService 45% line coverage (threshold 80%), UserController 85%

PITest: 18 mutations generated, 13 killed, 3 survived (boundary condition on password
length, duplicate email negation, null return value). Mutation score: 72% (threshold 80%)

Trivy: 1 HIGH CVE — CVE-2022-22978 in spring-security-crypto:5.7.3

Summarize as quality-gates.md with sections: Blockers | Warnings | Informational.
Recommend: Approve or Request Changes, with justification.
```

#### Option B — via mock report files

```
Summarize #file:reports/sonarqube.md, #file:reports/jacoco.md,
#file:reports/pitest.md, and #file:reports/trivy.md.
Identify: (1) Blockers (2) Warnings (3) Informational items.
Recommend whether the MR should be approved or changes requested, with justification.
```

---

### Step 4 — Draft Inline Review Comments

**Create:** `output/inline-comments.md`

#### Option A

```
Act as a senior reviewer. Using the PR diff from GitHub MCP,
#file:output/ac-traceability.md, and #file:output/quality-gates.md, draft inline
review comments. Each comment must include: file, method/class, issue, why it matters,
suggested fix.
```

#### Option B

```
Act as a senior reviewer. Using #file:mr/diff.patch, #file:output/ac-traceability.md,
and #file:output/quality-gates.md, draft inline review comments.
Each comment must include: file, method/class, issue, why it matters, suggested fix.
```

> If output is degraded, break into sub-prompts: (a) diff only, (b) AC gaps, (c) tool issues — then combine.

---

### Step 5 — Write the Final MR Review Summary ⭐ WOW MOMENT

**Create:** `output/mr-review-summary.md`

```
You are a senior engineer writing the final review summary for this MR.
Using #file:output/ac-traceability.md, #file:output/quality-gates.md, and
#file:output/inline-comments.md, write a 300-word review summary covering:
overall verdict (APPROVE / REQUEST CHANGES / REJECT), top 3 strengths,
top 3 concerns, and next steps.
```

Five minutes ago you had a raw diff. Now you have a complete senior engineer's review.

---

## Success Criteria

| Deliverable | Description |
|-------------|-------------|
| `output/ac-checklist.md` | All ACs as verifiable checkboxes |
| `output/ac-traceability.md` | Each AC mapped to diff with Complete/Partial/Missing status |
| `output/quality-gates.md` | Blockers / Warnings / Informational with recommendation |
| `output/inline-comments.md` | Specific, actionable comments with suggested fixes |
| `output/mr-review-summary.md` | Final verdict + strengths + concerns + next steps |

## Scoring Rubric

| Criterion | Weight |
|-----------|--------|
| AC traceability quality | 35% |
| Correct prioritization of tool findings | 35% |
| Comment usefulness (specific, actionable, respectful) | 30% |

---

## Bonus — Connect Both Exercises

If you've completed Exercise 1, add this to your Step 4 prompt:

> "Also enforce `.github/copilot-instructions.md` during review."

That shows team-aligned Copilot + shared coding standards powering the review.
