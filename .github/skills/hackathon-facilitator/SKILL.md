---
name: hackathon-facilitator
description: 'Facilitate GitHub Copilot hackathon sessions. Use when: preparing starter repos, building source files, writing instruction files, creating copilot-instructions.md, setting up exercise1 standards-aggregator-lab Option A real Confluence MCP Option B mock MD files, setting up exercise2 expert-mr-reviewer-lab real Java GitHub repo PR MCP SonarLint JaCoCo PITest Trivy, resolving participant blockers, running training workshops, generating messy source standards files, real tooling setup Java Spring Boot Maven.'
argument-hint: "exercise1-optionA | exercise1-optionB | exercise2-real | exercise2-mock | both | risks | tips"
---

# Hackathon Facilitator Skill

Builds and validates all content for the two GitHub Copilot hackathon exercises. Supports two delivery modes:

- **Option A (Real)** — live MCP connections to Confluence, Jira, and GitHub; real tool runs (SonarLint, JaCoCo, PITest, Trivy)
- **Option B (Mock)** — pre-built MD seed files; no external connections required

Both exercises share a single Java Spring Boot repo: [`user-management-service`](../../user-management-service/).

---

## When to Use

- Preparing starter repos before a session
- Setting up the real Java GitHub repo and PR for Exercise 2
- Configuring MCP servers (Atlassian, GitHub) for Exercise 1 Option A / Exercise 2
- Running real tool commands (JaCoCo, PITest, Trivy) and analyzing results with Copilot
- Resolving common participant blockers during the session
- Planning session timing and demo scripts

---

## Exercise 1 — Two-Track Design

### Option A: Real Sources (MCP + terminal)

Participants fetch LIVE data:

| Source | Tool | Instructions |
|--------|------|-------------|
| Confluence pages | Atlassian MCP | [`./references/ex1-option-a-real-sources.md`](./references/ex1-option-a-real-sources.md) |
| Public guidelines | Copilot web fetch / curl | Same reference |
| Team notes (Confluence/Wiki) | Atlassian MCP / GitHub MCP | Same reference |
| Step 5 validation | PR branch of `user-management-service` | Same reference |

### Option B: Mock MD Files

Participants use pre-built seed files in `standards-aggregator-lab/sources/`:

| File | Content |
|------|---------|
| `confluence_export.md` | Messy Confluence export with contradictions + duplicates |
| `public_guidelines.md` | Conflicting public coding guide |
| `team_notes.md` | Vague informal team notes |
| Step 5 validation | Single `sample/bad_example.java` file |

Seed file templates: [`./assets/exercise1/`](./assets/exercise1/)

---

## Exercise 2 — Real Java Repo + Real Tools

Exercise 2 uses a **real Spring Boot GitHub repo** instead of simulated report files.

### The shared Java repo: `user-management-service`

| Branch | Description |
|--------|-------------|
| `main` | Baseline — simple registration, plain text passwords, 1 unit test |
| `feature/USER-142-user-registration` | PR branch — adds BCrypt hashing + duplicate check; contains planted bugs and missing ACs |

Repo template: [`../../user-management-service/`](../../user-management-service/)
PR documentation: [`../../user-management-service/PR_CHANGES.md`](../../user-management-service/PR_CHANGES.md)

### Real tool stack

| Tool | What it finds | How to run |
|------|--------------|-----------|
| **GitHub MCP** | PR diff, changed files | Copilot Chat: `list_pull_requests`, `get_pull_request` |
| **Jira MCP** | Acceptance criteria from ticket USER-142 | Copilot Chat: Atlassian MCP prompts |
| **SonarLint** | `java:S2068` (logged password), `java:S2221` (broad catch) | VS Code extension — auto-runs on open files |
| **JaCoCo** | 40–45% coverage on UserService (below 80% gate) | `mvn clean test jacoco:report` |
| **PITest** | 3 surviving mutants, ~72% mutation score | `mvn pitest:mutationCoverage` |
| **Trivy** | HIGH CVE in `spring-security-crypto:5.7.3` added by PR | `trivy fs --scanners vuln .` |

Real tooling setup guide: [`./references/ex2-real-tooling.md`](./references/ex2-real-tooling.md)
Mock report files (Option B fallback): [`./assets/exercise2/`](./assets/exercise2/)

---

## Procedure

### Step 1 — Determine scope and delivery mode

- Exercise 1 choice: **Option A** (MCP) or **Option B** (seed files)?
- Exercise 2 choice: **Real tools** (preferred) or **mock reports** (fallback)?

### Step 2 — Set up the Java repo (for Exercise 2 and Ex1 Option A Step 5)

1. Copy `user-management-service/` to a new directory
2. Push to GitHub as a new repo
3. Create the PR branch by applying changes from `PR_CHANGES.md`
4. Open a PR on GitHub
5. Create the Jira ticket (USER-142) referencing the acceptance criteria

Full instructions: [`./references/ex2-real-tooling.md`](./references/ex2-real-tooling.md) → Step 0

### Step 3 — Configure MCP servers (Option A / real tooling)

- **GitHub MCP** — needed for Exercise 2 PR fetching and Exercise 1 Option A team notes
- **Atlassian MCP** — needed for Exercise 1 Option A Confluence + Exercise 2 Jira ticket

Full setup: [`./references/ex1-option-a-real-sources.md`](./references/ex1-option-a-real-sources.md) → Prerequisite section

### Step 4 — Prepare tool environments on participant machines

For Exercise 2 real tooling, confirm per machine:
- Java 17+ and Maven 3.8+
- Trivy CLI (`choco install trivy` or direct download)
- SonarLint VS Code extension (`SonarSource.sonarlint-vscode`)

### Step 5 — Review risks and apply mitigations

- Exercise 1: [`./references/ex1-risks-solutions.md`](./references/ex1-risks-solutions.md)
- Exercise 2: [`./references/ex2-risks-solutions.md`](./references/ex2-risks-solutions.md)

### Step 6 — Prepare session materials

Load [`./references/facilitator-tips.md`](./references/facilitator-tips.md) and produce:

- Time-boxed run sheet
- Live demo script
- Participant cheat sheet (file context injection + tool commands)

### Step 7 — Final validation checklist

Before the session:

- [ ] `user-management-service` pushed to GitHub with both branches and open PR
- [ ] Jira ticket USER-142 created with 5 ACs
- [ ] `mvn clean test jacoco:report` runs cleanly (coverage gate fails — expected)
- [ ] `mvn pitest:mutationCoverage` completes and shows surviving mutants
- [ ] `trivy fs .` on PR branch shows HIGH CVE in spring-security-crypto
- [ ] SonarLint highlights the plain-text password log on PR branch files
- [ ] GitHub MCP fetches the PR diff via Copilot Chat (test this yourself)
- [ ] Atlassian MCP returns the Jira ticket details (if using Option A / real Jira)
- [ ] Gold standard output files prepared for all 5 deliverables per exercise
- [ ] Option B seed files available as fallback if real tools fail

---

## Output

When invoked, this skill produces one or more of:

| Output | Description |
|--------|-------------|
| Starter repo files | All source/input files participants receive |
| Gold standard outputs | Reference answers for each exercise deliverable |
| Session run sheet | Time-boxed facilitation guide |
| Participant cheat sheet | One-page Copilot Chat file context guide |
| Risk mitigation notes | Per-exercise blocker solutions |
