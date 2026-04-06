Below are two hackathon-ready exercises that are meant to be done using GitHub Copilot (Copilot Chat + repo artifacts).

---

## ✅ Exercise 1 (Copilot): "Coding Standards Aggregator & Enhancer"

### Goal

Participants build a repo-based "standards pack" that GitHub Copilot can use during coding and review:

- Pull standards from mixed sources (Confluence export text, public docs excerpt, local notes)
- Normalize into a single, structured, actionable standard
- Generate Copilot instruction files + PR checklist

This matches the "multi-source integration + non-repetitive HOW TO format + instruction file updates" idea.

---

### What you give participants (starter repo)

Create a repo called: `standards-aggregator-lab`

**Repo structure (starter)**

```
standards-aggregator-lab/
  sources/
    confluence_export.md
    public_guidelines.md
    team_notes.md
  output/
    (empty)
  .github/
    (empty)
  README.md
```

Populate `sources/*` with messy, inconsistent standards (duplicates, contradictions, vague rules).

---

### Step-by-step tasks (done with GitHub Copilot)

#### Step 0 — Enable Copilot

Participants must have:

- GitHub Copilot enabled in VS Code (or JetBrains)
- Copilot Chat panel open

#### Step 1 — Create a standards schema (with Copilot Chat)

**Task:** In `output/`, create `standards.schema.md`

**Copilot Chat prompt (paste as-is):**

> "Create a markdown schema for coding standards that is actionable and reviewable. Include sections: Naming, Error Handling, Logging, Security, Testing, Performance. Each rule must have: Rule, Rationale, Good example, Bad example, How to verify in code review."

**Expected output:** A template that forces clarity and examples.

#### Step 2 — Aggregate sources into a single standards doc (Copilot-assisted)

**Task:** Create `output/coding-standards.md`

**Copilot Chat prompt:**

> "Read the files in /sources. Merge them into one standards document following output/standards.schema.md. Remove duplicates, resolve contradictions by choosing the most strict security-safe option, and rewrite vague rules into measurable rules."

**What participants learn**

- How to use Copilot as a synthesizer (not just code completion)
- How to turn "tribal knowledge" into enforceable guidance

#### Step 3 — Generate a Copilot instructions file (key hackathon deliverable)

**Task:** Create `.github/copilot-instructions.md`

**Copilot Chat prompt:**

> "Convert output/coding-standards.md into a concise instruction file for GitHub Copilot. Make it directive, short, and optimized for AI coding help. Use MUST/SHOULD language. Include repo-specific conventions."

**Why this is aligned:** repo-specific instructions are explicitly called out as a productivity accelerator concept.

#### Step 4 — Add a PR checklist auto-template

**Task:** Create `.github/pull_request_template.md`

**Copilot Chat prompt:**

> "Create a PR template checklist derived from output/coding-standards.md. Make it quick to complete. Include: security, tests, logging, error handling, performance, backward compatibility."

#### Step 5 — "Prove it works" (mini validation)

**Task:** Add a deliberately non-compliant sample file: `sample/bad_example.js` or `sample/bad_example.java`

Then ask Copilot:

**Copilot Chat prompt:**

> "Review sample/bad_example.* against .github/copilot-instructions.md and output/coding-standards.md. List violations and propose corrected code."

---

### Success criteria (easy to judge in hackathon)

Participants deliver:

- `output/coding-standards.md` (clean, structured, examples included)
- `.github/copilot-instructions.md` (short directive rules)
- `.github/pull_request_template.md` (checklist)

### Scoring rubric (optional)

| Criterion | Weight |
|---|---|
| Clarity — rules are measurable + examples exist | 30% |
| Completeness — covers key areas (security/testing/logging/etc.) | 30% |
| Copilot usability — instructions are concise + enforceable | 40% |

---

## ✅ Exercise 2 (Copilot): "Expert MR Code Reviewer (GitHub flow simulation)"

### Goal

Participants build a Copilot-powered MR review workflow that:

- Uses Jira acceptance criteria
- Uses quality/security outputs (SonarQube, JaCoCo, PITest, Trivy)
- Produces:
  - Inline comments (as suggestions)
  - A final review summary
  - Next actions

This aligns directly with the "Expert MR Review Mode" concept and the integrated tooling list.

> **Note:** Your original use case mentions GitLab MR. This exercise keeps the same logic but runs inside a GitHub PR/MR simulation so it's fully doable with Copilot in a hackathon setting.

---

### What you give participants (starter repo)

**Repo:** `expert-mr-reviewer-lab`

**Repo structure (starter)**

```
expert-mr-reviewer-lab/
  jira/
    ticket.md
  mr/
    diff.patch
  reports/
    sonarqube.md
    jacoco.md
    pitest.md
    trivy.md
  output/
    (empty)
  .github/
    copilot-instructions.md
  README.md
```

Fill these with realistic sample text:

- `jira/ticket.md`: story + acceptance criteria
- `mr/diff.patch`: a patch/diff of changes
- `reports/*.md`: tool outputs with issues

_(Your internal material explicitly calls out this end-to-end flow and toolset.)_

---

### Step-by-step tasks (done with GitHub Copilot)

#### Step 1 — Convert acceptance criteria into a review checklist (Copilot)

**Task:** Create `output/ac-checklist.md`

**Copilot Chat prompt:**

> "Read jira/ticket.md. Extract acceptance criteria and rewrite them into a checkbox checklist where each item is objectively verifiable from code or tests."

#### Step 2 — Map diff changes to acceptance criteria (traceability)

**Task:** Create `output/ac-traceability.md`

**Copilot Chat prompt:**

> "Using mr/diff.patch and output/ac-checklist.md, map each acceptance criterion to the specific files/lines in the diff that implement it. Mark any AC as Missing/Partial/Complete."

This demonstrates the "traceability for every acceptance criterion" principle.

#### Step 3 — Pull in quality signals (Sonar/coverage/mutation/security)

**Task:** Create `output/quality-gates.md`

**Copilot Chat prompt:**

> "Summarize reports/sonarqube.md, reports/jacoco.md, reports/pitest.md, reports/trivy.md. Identify: (1) blockers (2) warnings (3) informational. Recommend whether the MR should be approved or changes requested, with justification."

This directly matches the "integrates SonarQube, JaCoCo, PITest… Trivy… actionable feedback and summary" concept.

#### Step 4 — Draft inline review comments (Copilot generates, human approves)

**Task:** Create `output/inline-comments.md`

**Copilot Chat prompt:**

> "Act as a senior reviewer. Using mr/diff.patch, output/ac-traceability.md, and output/quality-gates.md, draft inline review comments. Each comment should include: file, approximate location (function/class), issue, why it matters, and suggested fix."

#### Step 5 — Produce the final MR review summary

**Task:** Create `output/mr-review-summary.md`

**Copilot Chat prompt:**

> "Write a final MR review summary with sections: AC Status, Quality Gates, Security, Testing, Risk, Decision (Approve/Request changes), and Next Actions. Keep it concise and actionable."

---

### Success criteria (clear deliverables)

Participants deliver:

- `output/ac-checklist.md`
- `output/ac-traceability.md`
- `output/quality-gates.md`
- `output/inline-comments.md`
- `output/mr-review-summary.md`

### Hackathon judging rubric (optional)

| Criterion | Weight |
|---|---|
| AC traceability quality | 35% |
| Correct prioritization of tool findings | 35% |
| Comment usefulness (specific, actionable, respectful) | 30% |

---

## Bonus: Make both exercises feel like "one product"

If you want an end-to-end story, ask teams to connect outputs:

- **Exercise 1** produces: `.github/copilot-instructions.md`
- **Exercise 2** uses it by adding to prompt:
  > "Also enforce .github/copilot-instructions.md during review."

That shows "team-aligned Copilot" + standardized practices.
