# Standards Aggregator Lab

> **Exercise 1 — GitHub Copilot Hackathon**

Use GitHub Copilot Chat to consolidate messy, multi-source coding standards into a single structured document, then generate a Copilot instructions file and a PR checklist template.

---

## Repo Structure

```
standards-aggregator-lab/
  sources/
    confluence_export.md     ← old Confluence wiki export
    public_guidelines.md     ← public style guide excerpt
    team_notes.md            ← informal Slack / retro notes
  output/
    (empty — you'll fill this)
  .github/
    (empty — you'll fill this)
  sample/
    bad_example.java         ← used in Step 5
  README.md
```

---

## Before You Start — Enable Copilot File Context

Three ways to reference a file in Copilot Chat:

| Method | How |
|--------|-----|
| `#file:` syntax | Type `#file:` in chat → pick file from dropdown |
| Drag tab | Drag the editor tab into the chat input box |
| Copy-paste | Copy file content → paste before your prompt |

> **Tip:** If Copilot says it can't see a file, paste the content directly — it always works.

---

## Steps

### Step 0 — Enable Copilot in VS Code

- Open the Copilot Chat panel (`Ctrl+Alt+I`)
- Confirm you can type a prompt and get a response
- Enable **"Use Instruction Files"** setting: VS Code Settings → search _copilot instructions_ → toggle on

---

### Step 1 — Create a Standards Schema

**Create:** `output/standards.schema.md`

Paste this prompt into Copilot Chat:

```
Create a markdown schema for coding standards that is actionable and reviewable.
Include sections: Naming, Error Handling, Logging, Security, Testing, Performance.
Each rule must have: Rule, Rationale, Good example, Bad example, How to verify in code review.
```

Save Copilot's output to `output/standards.schema.md`.

---

### Step 2 — Aggregate Sources into a Single Standards Doc

**Create:** `output/coding-standards.md`

Attach all three source files using `#file:`, then paste:

```
Using #file:sources/confluence_export.md, #file:sources/public_guidelines.md, and #file:sources/team_notes.md:
Merge them into one standards document following #file:output/standards.schema.md.
Remove duplicates, resolve contradictions by choosing the most strict security-safe option,
and rewrite vague rules into measurable rules.
```

> **Expect 2–3 iterations.** If output is generic, add: _"Resolve ALL contradictions explicitly, labelling the chosen rule and why."_

Save Copilot's output to `output/coding-standards.md`.

---

### Step 3 — Generate a Copilot Instructions File

**Create:** `.github/copilot-instructions.md`

```
Convert #file:output/coding-standards.md into a concise instruction file for GitHub Copilot.
Make it directive, short, and optimized for AI coding help.
Use MUST/SHOULD language. Include repo-specific conventions.
Keep it under 50 lines.
```

Save to `.github/copilot-instructions.md`.

---

### Step 4 — Generate a PR Checklist Template

**Create:** `.github/pull_request_template.md`

```
Create a PR template checklist derived from #file:output/coding-standards.md.
Make it quick to complete. Include: security, tests, logging, error handling, performance, backward compatibility.
```

Save to `.github/pull_request_template.md`.

---

### Step 5 — Validate With a Bad Example ⭐ WOW MOMENT

**Use:** `sample/bad_example.java`

```
Review #file:sample/bad_example.java against the rules in
#file:.github/copilot-instructions.md and #file:output/coding-standards.md.
List each violation as:
| Rule Violated | Line | Why it matters | Corrected code |
```

Watch Copilot catch every violation against the standards **you** wrote. That's the full loop.

---

## Success Criteria

| Deliverable | Description |
|-------------|-------------|
| `output/standards.schema.md` | Schema with all 6 sections |
| `output/coding-standards.md` | Clean, structured, examples included |
| `.github/copilot-instructions.md` | Short directive MUST/SHOULD rules |
| `.github/pull_request_template.md` | Checklist covering all key areas |

## Scoring Rubric

| Criterion | Weight |
|-----------|--------|
| Clarity — rules are measurable + examples exist | 30% |
| Completeness — covers security / testing / logging / performance | 30% |
| Copilot usability — instructions are concise + enforceable | 40% |
