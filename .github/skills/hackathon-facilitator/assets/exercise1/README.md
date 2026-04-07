# Exercise 1 Asset Templates

This folder contains starter file templates for `standards-aggregator-lab`.

## Delivery Mode: Option A vs Option B

| | Option A (Real) | Option B (Mock — these files) |
|---|---|---|
| Confluence | Fetch via Atlassian MCP | `confluence_export.md` |
| Public guidelines | Fetch via web / curl | `public_guidelines.md` |
| Team notes | Fetch via Atlassian MCP or GitHub MCP | `team_notes.md` |
| Step 5 validation | PR branch of `user-management-service` Java repo | `sample/bad_example.java` |
| MCP required? | Yes | No |

For Option A setup: see [`../../references/ex1-option-a-real-sources.md`](../../references/ex1-option-a-real-sources.md)

---

## Option B: Files in this folder

| File | Purpose | Quality bar |
|------|---------|-------------|
| `sources/confluence_export.md` | Simulates a Confluence team export — verbose, partially outdated | Must have ≥3 duplicates and ≥2 contradictions with other source files |
| `sources/public_guidelines.md` | Simulates a public coding guidelines excerpt | Should be more formal; includes rules that overlap with confluence export but worded differently |
| `sources/team_notes.md` | Simulates informal Slack/Notion notes from the team | Must be vague, missing rationale, some rules are unmeasurable |
| `copilot-instructions-seed.md` | Facilitator gold standard for `.github/copilot-instructions.md` | MUST/SHOULD language, ≤50 lines, repo-specific |

## Rules for source file content

1. **Coverage areas** — all three sources must together touch: Naming, Error Handling, Logging, Security, Testing, Performance
2. **Contradictions to plant** — at least one contradiction between files (e.g., confluence says "use snake_case", team_notes says "use camelCase for all vars")
3. **Duplicates to plant** — same rule stated 2-3 times across files in different wording
4. **Vague rules to plant** — at least 2 rules that are unmeasurable (e.g., "write clean code", "handle errors properly")
5. **Security gap** — at least one file should be missing a security rule that another file has, so Copilot must synthesize
6. **Length** — each file should be 60–100 lines; long enough to feel real, short enough for Copilot to process in one prompt

## Content generation prompt (use with Copilot Chat)

```
Generate three messy coding standards files for a hackathon exercise:

1. confluence_export.md — simulates a Confluence team standards export. Include rules for: naming (snake_case), error handling (log and rethrow), logging (use structured JSON logs), security (no hardcoded secrets), testing (80% coverage minimum), performance (avoid N+1 queries). Make it verbose with some outdated rules and duplicates.

2. public_guidelines.md — simulates a public open-source coding guide excerpt. Cover: naming (camelCase for variables — contradiction with file 1), error handling (catch specific exceptions), security (validate all inputs, use parameterized queries), testing (write unit tests for all public methods). More formal tone.

3. team_notes.md — simulates informal team Slack notes. Include vague rules like "write clean code", "don't forget to handle errors", "make sure tests pass", alongside a few real rules about logging format and performance. No rationale. Inconsistent formatting.

Each file should be 60–80 lines. Plant cross-file contradictions and duplicates intentionally.
```
