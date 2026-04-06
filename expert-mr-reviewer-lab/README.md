# Expert MR Reviewer Lab

> **Exercise 2 — GitHub Copilot Hackathon**

Build a Copilot-powered merge request review workflow using a real Jira ticket, a code diff, and quality tool reports. Produce the five deliverables that a senior engineer would write before approving or rejecting an MR.

---

## Repo Structure

```
expert-mr-reviewer-lab/
  jira/
    ticket.md              ← Jira story with acceptance criteria
  mr/
    diff.patch             ← Code diff for the feature
  reports/
    sonarqube.md           ← SonarQube analysis output
    jacoco.md              ← JaCoCo coverage report
    pitest.md              ← PITest mutation testing report
    trivy.md               ← Trivy security scan output
  output/
    (empty — you'll fill this across Steps 1–5)
  .github/
    copilot-instructions.md
  README.md
```

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

## Steps

### Step 1 — Extract AC Checklist from the Ticket

**Create:** `output/ac-checklist.md`

```
Read #file:jira/ticket.md. Extract acceptance criteria and rewrite them into a
checkbox checklist where each item is objectively verifiable from code or tests.
```

---

### Step 2 — Map Diff to Acceptance Criteria (Traceability)

**Create:** `output/ac-traceability.md`

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

```
Summarize #file:reports/sonarqube.md, #file:reports/jacoco.md,
#file:reports/pitest.md, and #file:reports/trivy.md.
Identify: (1) Blockers (2) Warnings (3) Informational items.
Recommend whether the MR should be approved or changes requested, with justification.
```

---

### Step 4 — Draft Inline Review Comments

**Create:** `output/inline-comments.md`

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
