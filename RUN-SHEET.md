# Session Run Sheet — GitHub Copilot Hackathon
> Prepared: April 6, 2026 | Both exercises | Total: ~195 min

---

## Pre-Session Checklist (complete before participants arrive)

**All participants:**
- [ ] Copilot extension installed and signed in
- [ ] "Use Instruction Files" setting ENABLED (VS Code Settings → search "copilot instructions")
- [ ] Starter repos distributed (zip or GitHub link): `standards-aggregator-lab/` and `expert-mr-reviewer-lab/`
- [ ] `CHEAT-SHEET.md` shared with all participants (link in chat or printed)

**Option A only — MCP config:**
- [ ] GitHub MCP configured in VS Code `settings.json` (GitHub token, `https://api.githubcopilot.com/mcp/`)
- [ ] Atlassian MCP configured in VS Code `settings.json` (Atlassian API token, `https://mcp.atlassian.com/v1/mcp`)
- [ ] Both MCP servers tested in Copilot Chat Tools panel (⚙️ icon shows servers active)
- [ ] Confirmed Atlassian API tokens generated on all participant machines
- [ ] `user-management-service` pushed to GitHub with `main` and `feature/USER-142-user-registration` branches, PR open
- [ ] Jira ticket USER-142 created with 5 ACs

**Option A only — real tool stack (per participant machine):**
- [ ] Java 17+ installed (`java -version`)
- [ ] Maven 3.8+ installed (`mvn -version`)
- [ ] SonarLint VS Code extension installed (`SonarSource.sonarlint-vscode`)
- [ ] Trivy CLI installed (`trivy --version`) — install via `choco install trivy` or `scoop install trivy`
- [ ] `mvn clean test jacoco:report` runs cleanly on the PR branch (coverage gate fail is expected)
- [ ] `mvn pitest:mutationCoverage` completes and shows surviving mutants
- [ ] `trivy fs --scanners vuln .` shows HIGH CVE in `spring-security-crypto:5.7.3`
- [ ] SonarLint highlights `java:S2068` in `UserService.java` on the PR branch

**Facilitator:**
- [ ] Gold standard answers in `gold/ex1/` and `gold/ex2/` on facilitator machine — ready to share if needed
- [ ] Facilitator has run ALL prompts end-to-end at least once the day before
- [ ] Projector / screen share working — Copilot Chat visible to room
- [ ] Confirm delivery mode with participants: **Option A (MCP/real tools)** or **Option B (mock files)**

---

## EXERCISE 1 — Standards Aggregator Lab (~95 min)

---

### Step 0 — Setup + File Context Demo *(10 min)*

**Live demo required.** Show the room:
1. Open `standards-aggregator-lab/` as VS Code workspace
2. Open Copilot Chat panel (`Ctrl+Alt+I`)
3. Type `#file:` → show the dropdown → pick `sources/confluence_export.md`
4. Say: _"This is how every file reference in today's prompts works. If the dropdown doesn't show your file, drag the tab or paste the content."_
5. Verify "Use Instruction Files" setting is enabled — show the toggle

**Energy cue:** Keep this light and fast. The goal is zero file-context blockers for the next 85 min.

---

### Step 1 — Generate Standards Schema *(15 min)*

**Prompt to paste (from README Step 1):**
```
Create a markdown schema for coding standards that is actionable and reviewable.
Include sections: Naming, Error Handling, Logging, Security, Testing, Performance.
Each rule must have: Rule, Rationale, Good example, Bad example, How to verify in code review.
```

**Facilitator actions:**
- Walk the room — confirm everyone gets a schema with all 6 sections
- Reference answer: `gold/ex1/standards.schema.md`

**Common question:** _"Does the output need to look exactly like yours?"_ → No. Quality of structure matters, not exact wording.

---

### Step 2 — Fetch Sources (Option A) or Use Seed Files (Option B) *(5 min)*

**Option A — MCP fetch prompts (demo these quickly, ~3 min):**
```
Search Confluence for pages in the "<space-key>" space that contain coding standards or quality rules. List page titles and IDs.
```
```
Fetch the full content of Confluence page "<page-title>" from space "<space-key>". Save as sources/confluence_export.md.
```
```
Using the GitHub MCP, fetch the wiki page "Coding-Standards" from repository "<org>/<repo>". Save as sources/team_notes.md.
```

**Option B** — Sources already exist in `sources/`. Tell participants to skip to Step 3.

---

### Step 3 — Aggregate Sources *(30 min)* ⚠️ Announce the time box

**Prompt (from README Step 3):**
```
Using #file:sources/confluence_export.md, #file:sources/public_guidelines.md, and #file:sources/team_notes.md:
Merge them into one standards document following #file:output/standards.schema.md.
Remove duplicates, resolve contradictions by choosing the most strict security-safe option,
and rewrite vague rules into measurable rules.
```

**Live demo:** Show one full iteration: paste prompt → review output → add refinement → show improved output.

**Key contradictions Copilot should resolve:**
- Naming: `snake_case` (Confluence) vs `camelCase` (team notes + public guidelines) → **choose camelCase**
- Test naming: two different patterns → **choose one**
- Security: vague "security is everyone's responsibility" → **replace with specific rules from other sources**

**Rescue prompt (if output is generic):**
> "Resolve ALL contradictions explicitly, labelling the chosen rule and why. Mark superseded rules as [DEPRECATED]."

**Reference answer:** `gold/ex1/coding-standards.md`

---

### Step 4 — Generate Copilot Instructions File *(15 min)*

**Prompt:**
```
Convert #file:output/coding-standards.md into a concise instruction file for GitHub Copilot.
Make it directive, short, and optimized for AI coding help.
Use MUST/SHOULD language. Include repo-specific conventions. Keep under 50 lines.
```

**Live demo:** Show the MUST/SHOULD format — 30 seconds. Show `gold/ex1/copilot-instructions.md` as an example of done.

**Save to:** `.github/copilot-instructions.md`

**Remind participants:** This file does nothing unless "Use Instruction Files" is enabled.

---

### Step 5 — Generate PR Template *(10 min)*

**Prompt:**
```
Create a PR template checklist derived from #file:output/coding-standards.md.
Make it quick to complete. Include: security, tests, logging, error handling, performance, backward compatibility.
```

**Save to:** `.github/pull_request_template.md`

**Reference answer:** `gold/ex1/pull_request_template.md`

---

### Step 6 — Validate Against the Real PR Branch *(15 min)* ⭐ WOW MOMENT

> **PAUSE AND ANNOUNCE: "This is the payoff moment everyone."**

Validation always uses `user-management-service` PR branch files — **no synthetic file needed**.

**Option A — GitHub MCP (no local clone required):**
```
Get the contents of src/main/java/com/example/service/UserService.java from the
feature/USER-142-user-registration branch of <org>/user-management-service.
```
```
Get the contents of src/main/java/com/example/controller/UserController.java from the
feature/USER-142-user-registration branch of <org>/user-management-service.
```
Then:
```
Review the two files above against the rules in #file:.github/copilot-instructions.md
and #file:output/coding-standards.md.
List every violation as: Rule Violated | File | Method | Why it matters | Corrected code.
```

**Option B — local checkout:**
```powershell
cd ../user-management-service
git checkout feature/USER-142-user-registration
```
Then in Copilot Chat:
```
Review #file:src/main/java/com/example/service/UserService.java and
#file:src/main/java/com/example/controller/UserController.java against
#file:.github/copilot-instructions.md and #file:output/coding-standards.md.
List every violation as: Rule Violated | File | Method | Why it matters | Corrected code.
```

**What to expect — 3 violations (real code, real bugs):**
- V1: `request.getPassword()` in log statement — Logging blocker (SonarLint `java:S2068`)
- V2: Password validation checks length only — missing uppercase + digit enforcement
- V3: `catch (Exception e)` in `UserController` — too broad (SonarLint `java:S2221`)

**Reference answer:** `gold/ex1/violation-output.md`

> **Energy script:** _"You just caught real bugs, in real code, against standards YOU wrote — standards Copilot helped you write. That is the full loop. That's what this tool does on your actual PRs every day."_

**Protect this time box** — it is the most engaging step. Do not let earlier steps run into it.

---

## BREAK *(10 min)*

---

## EXERCISE 2 — Expert MR Reviewer Lab (~100 min)

---

### Intro — Option A or B + Diff Format Primer *(5 min)*

**Announce delivery mode.** If running Option A:
- Confirm GitHub MCP and Atlassian MCP are active in the Tools panel
- Confirm `user-management-service` is cloned and PR branch is available
- Confirm Java/Maven/Trivy/SonarLint are installed (quick show of hands)

**If anyone is missing tools → switch them to Option B.** Do not block the room.

**Live demo required.** Open `mr/diff.patch` in VS Code and explain:

| Symbol | Meaning |
|--------|---------|
| `---` | Old file (before this MR) |
| `+++` | New file (after this MR) |
| `@@` | Chunk header — file and line range |
| `-` prefix | Line removed |
| `+` prefix | Line added |

> _"You don't need to find exact line numbers. File name and method name is enough for Copilot to reason about the diff."_

---

### Step 1 — Extract AC Checklist *(15 min)*

**Option A — via Jira MCP:**
```
Fetch the details of Jira ticket USER-142 including description, acceptance criteria, and definition of done.
```
Then:
```
From the Jira ticket content above, extract the acceptance criteria and rewrite them into a
checkbox checklist where each item is objectively verifiable from code or tests.
```

**Option B — via local file:**
```
Read #file:jira/ticket.md. Extract acceptance criteria and rewrite them into a
checkbox checklist where each item is objectively verifiable from code or tests.
```

**Save to:** `output/ac-checklist.md`

**Reference answer:** `gold/ex2/ac-checklist.md`

---

### Step 2 — Map Diff to ACs *(25 min)* ⚠️ Announce the time box

**Option A — fetch diff via GitHub MCP then map:**
```
List open pull requests on repository <org>/user-management-service.
```
```
Get the full diff for pull request #<number> on <org>/user-management-service.
```
Then:
```
Using the PR diff above and #file:output/ac-checklist.md, map each acceptance
criterion to the specific files and methods in the diff that implement it.
Mark each AC as: Complete / Partial / Missing.
Exact line numbers are optional — file and method name is sufficient.
```

**Option B — via local file:**
```
Using #file:mr/diff.patch and #file:output/ac-checklist.md, map each acceptance
criterion to the specific files and methods in the diff that implement it.
Mark each AC as: Complete / Partial / Missing.
Exact line numbers are optional — file and method name is sufficient.
```

**What Copilot should find:**
- AC-1: Partial (endpoint exists, but uppercase/digit validation missing)
- AC-2: Complete
- AC-3: Partial (bcrypt used, but password logged in plain text)
- AC-4: Missing
- AC-5: Missing

**Rescue prompt** if Copilot hallucinates line numbers:
> "Exact line numbers are optional — file and method name is sufficient for mapping."

**Reference answer:** `gold/ex2/ac-traceability.md`

---

### Step 3 — Summarize Quality Tool Signals *(20 min)*

**Option A — Run real tools first, then paste findings:**

In terminal (`user-management-service/`, PR branch):
```powershell
# JaCoCo
mvn clean test jacoco:report
# PITest (3–5 min)
mvn pitest:mutationCoverage
# Trivy
trivy fs --scanners vuln .
```
SonarLint findings: open Problems panel (`Ctrl+Shift+M`) while `UserService.java` is open.

Then paste all output into Copilot Chat:
```
I ran all quality tools on the PR branch. Here are the findings:
SonarLint: [paste Problems panel]
JaCoCo: UserService 45% line coverage (threshold 80%), UserController 85%
PITest: 72% mutation score, 3 surviving mutants
Trivy: 1 HIGH CVE — CVE-2022-22978 in spring-security-crypto:5.7.3

Summarize as quality-gates.md: Blockers | Warnings | Informational.
Recommend: Approve or Request Changes, with justification.
```

**Option B — via mock report files:**
```
Summarize #file:reports/sonarqube.md, #file:reports/jacoco.md,
#file:reports/pitest.md, and #file:reports/trivy.md.
Identify: (1) Blockers (2) Warnings (3) Informational items.
Recommend whether the MR should be approved or changes requested, with justification.
```

**Rescue if context overflows:**
> Break into: (a) SonarQube alone → (b) JaCoCo + PITest → (c) Trivy → then ask Copilot to synthesize the three outputs.

**Reference answer:** `gold/ex2/quality-gates.md`

---

### Step 4 — Draft Inline Review Comments *(20 min)*

**Live demo:** Show one good comment vs one vague comment.

**Good:**
> "File: `UserService.java`, Method: `register()`. Issue: plain-text password logged on line 26 (`request.getPassword()`). Why it matters: GDPR breach + SonarQube blocker. Fix: remove `request.getPassword()` from log call."

**Vague (do not accept):**
> "The logging could be improved."

**Prompt:**
```
Act as a senior reviewer. Using #file:mr/diff.patch, #file:output/ac-traceability.md,
and #file:output/quality-gates.md, draft inline review comments.
Each comment must include: file, method/class, issue, why it matters, suggested fix.
```

**Reference answer:** `gold/ex2/inline-comments.md`

---

### Step 5 — Write Final MR Review Summary *(15 min)* ⭐ WOW MOMENT

> **PAUSE AND ANNOUNCE: "Five minutes ago you had a raw .patch file."**

**Prompt:**
```
You are a senior engineer writing the final review summary for this MR.
Using #file:output/ac-traceability.md, #file:output/quality-gates.md, and
#file:output/inline-comments.md, write a 300-word review summary covering:
overall verdict (APPROVE / REQUEST CHANGES / REJECT), top 3 strengths,
top 3 concerns, and next steps.
```

**Reference answer:** `gold/ex2/mr-review-summary.md`

> **Energy script:** _"Five minutes ago you had a raw .patch file and four tool reports. Now you have a senior engineer's complete MR review: AC status, quality gates, security flags, inline comments, and a go/no-go decision. That is what Copilot as an analyst looks like."_

**Show the full output** on the projector. Pause for the room to read it.

---

## Bonus — Connect Both Exercises *(optional, 5 min)*

Ask participants to add this to their Step 4 prompt:
> "Also enforce `.github/copilot-instructions.md` from Exercise 1 during review."

> _"That's team-aligned Copilot — the same standards your team wrote in Exercise 1 are now enforcing the review in Exercise 2. One coherent workflow, end to end."_

---

## Rescue Prompts — 7 Most Common Blockers

| Blocker | Symptom | Fix |
|---------|---------|-----|
| `FILE_CONTEXT` | "I don't have access to the file" | _"Type `#file:` and pick from dropdown. If empty, paste file content directly before your prompt."_ |
| `SETTING_DISABLED` | copilot-instructions.md has no effect | _"VS Code Settings → search 'copilot instructions' → enable Use Instruction Files. Restart Chat."_ |
| `VAGUE_OUTPUT` | Generic rules, no contradictions resolved | _"Add: 'Resolve ALL contradictions explicitly, label which rule you chose and why.'"_ |
| `CONTEXT_OVERFLOW` | Step 4 output is truncated / partial | _"Break into 3 sub-prompts: (a) diff quality issues alone, (b) AC gaps using traceability, (c) tool issues using quality-gates. Then combine."_ |
| `PATCH_FORMAT` | "What does `@@` mean?" | _"60-second primer: `---` = before, `+++` = after, `@@` = chunk, `-` line = removed, `+` line = added."_ |
| `MCP_NOT_WORKING` | MCP server not appearing in Tools panel / auth error | _"Check `settings.json` has the correct token. Restart VS Code. If still failing, switch to Option B — paste the seed file content directly."_ |
| `TOOL_NOT_INSTALLED` | `mvn` or `trivy` not found in terminal | _"Switch to Option B — use the mock report files in `reports/`. No tool install needed. The prompts work identically."_ |

---

## Judging Criteria (quick reference)

### Exercise 1
| Criterion | Weight |
|-----------|--------|
| Clarity — rules are measurable + examples exist | 30% |
| Completeness — covers security / testing / logging / performance | 30% |
| Copilot usability — instructions are concise + enforceable | 40% |

### Exercise 2
| Criterion | Weight |
|-----------|--------|
| AC traceability quality | 35% |
| Correct prioritization of tool findings | 35% |
| Comment usefulness (specific, actionable, respectful) | 30% |
