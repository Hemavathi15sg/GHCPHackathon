# Exercise 1 — Full Audit Report
> Audited: April 7, 2026 | Scope: All source files, README, CHEAT-SHEET, RUN-SHEET, gold/ex1/
> Updated: `bad_example.java` approach retired — validation now always uses `user-management-service` PR branch

---

## Summary

| Severity | Count | Items |
|----------|-------|-------|
| ~~🔴 CRITICAL~~ | ~~1~~ | ~~Missing `bad_example.java`~~ → **Resolved: no longer needed** |
| 🟠 BUG — Wrong label | 1 | Step number mismatch fixed in README + CHEAT-SHEET |
| 🟡 MINOR — UX gap | 2 | No `output/` or `.github/` placeholders |
| ✅ OK | 9 | See passing checks below |

---

## ✅ C1 — RESOLVED (design change)

**`sample/bad_example.java` is no longer required.**

Validation (Step 6) now uses real `user-management-service` PR branch files for both delivery options:
- **Option A**: GitHub MCP fetches `UserService.java` + `UserController.java` from `feature/USER-142-user-registration`
- **Option B**: Local `git checkout feature/USER-142-user-registration` + `#file:` references

`gold/ex1/violation-output.md` has been updated to reference the 3 real violations from the PR branch:
| Violation | File | Issue |
|-----------|------|-------|
| V1 | `UserService.java` `register()` | `request.getPassword()` logged in plain text — Logging + Security blocker |
| V2 | `UserService.java` `register()` | Password validates length only; missing uppercase + digit check |
| V3 | `UserController.java` `register()` | `catch (Exception e)` — overly broad, swallows programming errors |

The `sample/` directory and `bad_example.java` references have been removed from README, CHEAT-SHEET, RUN-SHEET, and agent.md.

---

## ✅ B1 — RESOLVED

README Delivery Options table now correctly says **"Step 6 validation"**.

---

## ✅ B2 — RESOLVED

CHEAT-SHEET Exercise 1 Option B table aggregation row now correctly labelled **"Step 3"**.

---

## 🟡 MINOR — Missing Directory Placeholders (still open)

### M1: `standards-aggregator-lab/output/` directory does not exist

**Impact:** When participants follow Step 1 and try to save `output/standards.schema.md`, VS Code will require them to manually create the directory.

**Fix:** Add `standards-aggregator-lab/output/.gitkeep`

---

### M2: `standards-aggregator-lab/.github/` directory does not exist

**Impact:** Step 4 asks participants to save to `.github/copilot-instructions.md` without prior instruction to create the directory.

**Fix:** Add `standards-aggregator-lab/.github/.gitkeep`

---

## ✅ Passing Checks

| Check | Result |
|-------|--------|
| `confluence_export.md` contradictions planted (snake_case, boolean prefix, duplicate PII rule) | ✅ Correct |
| `public_guidelines.md` contradictions planted (camelCase vs snake_case, test naming pattern) | ✅ Correct |
| `team_notes.md` contradictions planted (camelCase tiebreaker, vague security, duplicate PII rule) | ✅ Correct |
| Facilitator comments present in all 3 source files explaining planted contradictions | ✅ Present |
| Cross-source contradictions ≥3 as required by SKILL spec | ✅ 4 distinct contradictions |
| `gold/ex1/coding-standards.md` resolves camelCase contradiction with explicit label | ✅ Correct |
| `gold/ex1/coding-standards.md` covers all 6 sections | ✅ All 6 present |
| `gold/ex1/copilot-instructions.md` uses MUST/SHOULD language and ≤50 lines | ✅ Compliant |
| `gold/ex1/pull_request_template.md` covers all 6 required areas | ✅ All 6 present |
| `gold/ex1/violation-output.md` references real PR branch violations (V1–V3) | ✅ Updated |
| README step prompts are copy-pasteable and work against source files | ✅ Verified |
| MCP setup blocks (Atlassian + GitHub) are correct per reference docs | ✅ Correct |
| Option A / Option B split clear at every step | ✅ Clear |
| Step 6 uses `user-management-service` PR branch for both options | ✅ Confirmed |
| `bad_example.java` references fully removed from all participant-facing files | ✅ Confirmed |

---

## Remaining Actions

| # | Priority | Action |
|---|----------|--------|
| 1 | 🟡 Nice to have | Add `standards-aggregator-lab/output/.gitkeep` |
| 2 | 🟡 Nice to have | Add `standards-aggregator-lab/.github/.gitkeep` |
> Audited: April 7, 2026 | Scope: All source files, README, CHEAT-SHEET, RUN-SHEET, gold/ex1/

---

## Summary

| Severity | Count | Items |
|----------|-------|-------|
| 🔴 CRITICAL — Blocker | 1 | Missing `bad_example.java` |
| 🟠 BUG — Wrong label | 2 | Step number mismatches in README table + CHEAT-SHEET |
| 🟡 MINOR — UX gap | 2 | No `output/` or `.github/` placeholders |
| ✅ OK | 9 | See passing checks below |

---

## 🔴 CRITICAL — Blocker

### C1: `sample/bad_example.java` does not exist

**Affected files:**
- `standards-aggregator-lab/README.md` — Step 6 Option B
- `CHEAT-SHEET.md` — Exercise 1 Option B table, Step 6
- `RUN-SHEET.md` — Exercise 1 Step 6 Option B
- `gold/ex1/violation-output.md` — entire file is a review of this missing file

**Impact:** Option B Step 6 (the WOW MOMENT) cannot run. `#file:sample/bad_example.java` will produce an error in Copilot Chat. The gold standard answer (`violation-output.md`) becomes unusable as a reference.

**Required:** Create `standards-aggregator-lab/sample/bad_example.java` containing exactly the 10 violations cited in `gold/ex1/violation-output.md`:

| Violation | Code pattern to include |
|-----------|------------------------|
| V1 | `class user_registration_handler` (snake_case class) |
| V2 | `void register_user(...)` (snake_case method) |
| V3 | `Object data, String raw_password` (generic + snake_case params) |
| V4 | `String user_email = ...` (snake_case local variable) |
| V5 | `String api_key = "sk-prod-abc123secret"` (hardcoded secret) |
| V6 | `System.out.println(...)` on 3+ lines (instead of structured logger) |
| V7 | `System.out.println("... password=" + raw_password)` (PII in log) |
| V8 | `catch (Exception e) { }` (empty catch block) |
| V9 | `"SELECT * FROM users WHERE email = '" + user_email + "'"` (SQL injection) |
| V10 | `saveUserRole(userEmail, r)` inside a `for` loop (N+1 query) |

---

## 🟠 BUG — Step Number Mismatches

### B1: README Delivery Options table says "Step 5 validation" — should be "Step 6"

**File:** `standards-aggregator-lab/README.md`
**Location:** Delivery Options table, row 4

After the step renumbering (Step 2 = Fetch Sources added; everything shifted +1 from old Step 2 onward), the Delivery Options table was not updated.

```
Current:  | Step 5 validation | Real PR branch files | `sample/bad_example.java` |
Correct:  | Step 6 validation | Real PR branch files | `sample/bad_example.java` |
```

---

### B2: CHEAT-SHEET Exercise 1 Option B table labels aggregation as "Step 2" — should be "Step 3"

**File:** `CHEAT-SHEET.md`
**Location:** Exercise 1 → Option B table

In the README, Step 2 is "Fetch / Gather Source Files" (Option B users skip it and go to Step 3). The CHEAT-SHEET Option B table skips straight from Step 1 to a row labelled "Step 2" whose prompt is the aggregation prompt — but aggregation is Step 3.

```
Current label: Step 2 → aggregation prompt
Correct label: Step 3 → aggregation prompt
```

Note: The missing Step 2 row for Option B is correct (Option B users skip it) — only the label on the next row is wrong.

---

## 🟡 MINOR — Missing Directory Placeholders

### M1: `standards-aggregator-lab/output/` directory does not exist

**Impact:** When participants follow Step 1 and try to save `output/standards.schema.md`, VS Code or the Copilot "Save to file" flow will fail silently or need them to manually `mkdir output`. The README says "(empty — you'll fill this)" in the repo structure diagram, but the directory doesn't exist in a fresh clone.

**Fix:** Add `standards-aggregator-lab/output/.gitkeep`

---

### M2: `standards-aggregator-lab/.github/` directory does not exist

**Impact:** Same as M1 — Step 4 asks participants to save to `.github/copilot-instructions.md` without prior instruction to create the directory.

**Fix:** Add `standards-aggregator-lab/.github/.gitkeep`

---

## ✅ Passing Checks

| Check | Result |
|-------|--------|
| `confluence_export.md` contradictions planted (snake_case, boolean prefix, duplicate PII rule) | ✅ Correct |
| `public_guidelines.md` contradictions planted (camelCase vs snake_case, test naming pattern) | ✅ Correct |
| `team_notes.md` contradictions planted (camelCase tiebreaker, vague security, duplicate PII rule) | ✅ Correct |
| Facilitator comments present in all 3 source files explaining planted contradictions | ✅ Present |
| Cross-source contradictions add up to ≥3 as required by the SKILL spec | ✅ 4 distinct contradictions across files |
| `gold/ex1/coding-standards.md` correctly resolves camelCase contradiction with explicit label | ✅ Correct |
| `gold/ex1/coding-standards.md` covers all 6 required sections (Naming, Error Handling, Logging, Security, Testing, Performance) | ✅ All 6 present |
| `gold/ex1/copilot-instructions.md` uses MUST/SHOULD language and ≤50 lines | ✅ Compliant |
| `gold/ex1/pull_request_template.md` covers all 6 required areas (security, tests, logging, error handling, performance, backward compatibility) | ✅ All 6 present |
| `gold/ex1/standards.schema.md` has all 6 sections with Rule/Rationale/Good/Bad/Verify structure | ✅ Correct |
| README step prompts are copy-pasteable and work against the source files | ✅ Verified |
| MCP setup blocks (Atlassian + GitHub) are correct per reference docs | ✅ Correct |
| Option A / Option B split is clear at every step | ✅ Clear |
| Repo structure diagram in README matches actual files (except `sample/` — see C1, and `output/`/`.github/` — see M1/M2) | ⚠️ 3 mismatches |

---

## Required Actions (Priority Order)

| # | Priority | Action | Files to Change |
|---|----------|--------|----------------|
| 1 | 🔴 Must fix | Create `standards-aggregator-lab/sample/bad_example.java` with all 10 violations | New file |
| 2 | 🟠 Should fix | Fix README Delivery Options table: "Step 5 validation" → "Step 6 validation" | `standards-aggregator-lab/README.md` |
| 3 | 🟠 Should fix | Fix CHEAT-SHEET Option B table: "Step 2" → "Step 3" for aggregation row | `CHEAT-SHEET.md` |
| 4 | 🟡 Nice to have | Add `standards-aggregator-lab/output/.gitkeep` | New file |
| 5 | 🟡 Nice to have | Add `standards-aggregator-lab/.github/.gitkeep` | New file |
