# Exercise 2 — Risks, Solutions & Mitigation Guide

> For use by the hackathon facilitator. Review before the session and prepare mitigations in advance.

---

## Technical Risks

### T1 — Large diff + 4 report files exceed Copilot context window

**Risk:** In Step 4, participants reference `diff.patch` + `ac-traceability.md` + `quality-gates.md` in one prompt. If these are large, Copilot's output is truncated or degrades.

**Solution:**
- Use the pre-built `diff.patch` from [`../assets/exercise2/diff.patch`](../assets/exercise2/diff.patch) — kept at ~120 lines deliberately.
- If you create your own diff, enforce the **150-line maximum**.
- For Step 4, teach participants to break the prompt into one file at a time if they get poor output:
  ```
  Step 4a: Focus only on mr/diff.patch. List code quality issues you see.
  Step 4b: Using output/ac-traceability.md, which of those issues relate to missing AC coverage?
  Step 4c: Using output/quality-gates.md, which issues were also flagged by tools? Combine into final inline comments.
  ```

---

### T2 — Copilot line-number reasoning in diff is fragile

**Risk:** Step 2 asks Copilot to reference specific lines in the diff. This is unreliable — Copilot may hallucinate line numbers or lose them across prompt iterations.

**Solution:**
- Lower expectations: tell participants that **approximate location** (file + method name) is acceptable for AC mapping — exact line numbers are a bonus.
- Clarify the Step 2 prompt:
  ```
  Using #file:mr/diff.patch and #file:output/ac-checklist.md, map each AC to the file and method in the diff that implements it (or note Missing if not found). Exact line numbers are optional — file and method name is sufficient.
  ```
- In the judging rubric, award full marks for correct file+method mapping even without line numbers.

---

### T3 — 6 starter files must be coherent — high prep risk

**Risk:** If `ticket.md` describes a feature that the `diff.patch` doesn't implement, Step 2 traceability produces nonsense. This is the highest prep risk of either exercise.

**Solution:**
- Use the complete pre-built asset set in [`../assets/exercise2/`](../assets/exercise2/):
  - `ticket.md` — 5 ACs for user registration endpoint
  - `diff.patch` — implements AC-1, AC-2, AC-3 fully; AC-4 and AC-5 are intentionally Missing
  - `sonarqube.md` — flags issues in the same files/methods as the diff
  - `jacoco.md` — coverage gaps in the same service class as the diff
  - `pitest.md` — surviving mutants in the same service class
  - `trivy.md` — CVE in the dependency added in the diff
- If you create custom files: fill in the coherence checklist in [`../assets/exercise2/README.md`](../assets/exercise2/README.md) before the session.

---

## Content Risks

### C1 — `ticket.md` and `diff.patch` are narratively incoherent

**Risk:** See T3 above. This breaks Step 2.

**Solution:** Use provided assets. If customizing, verify: every file name and class name in the diff must appear at least once in the report files (sonarqube/jacoco/pitest).

---

### C2 — Report files are too generic / placeholder-like

**Risk:** If reports say things like "Coverage: low" without specifics, Step 3 quality gate output will be flat and not useful for judging.

**Solution:**
- Use the pre-built reports — each has specific class names, line numbers, CVE IDs, and mutation details.
- Test quality: run the Step 3 prompt yourself before the session. If Copilot's output has fewer than 5 distinct findings across the 4 reports, your reports are too thin.

---

### C3 — Participants unfamiliar with what SonarQube/JaCoCo/PITest/Trivy look like

**Risk:** Participants spend time understanding the report format instead of using it.

**Solution:**
- Add a **2-minute tool intro** at the start of Exercise 2 (before Step 1):
  - SonarQube: code quality + bugs. Look for Blocker/Critical/Major.
  - JaCoCo: which lines/branches are tested. Look for % below threshold.
  - PITest: which logic mutations could survive — gaps in test assertions. Look for Survived mutants.
  - Trivy: dependency CVEs. Look for HIGH/CRITICAL

---

## Time Risks

### TM1 — Step 2 (traceability) runs long

**Risk:** Mapping each AC to diff locations is the intellectually hardest step. Participants iterate many times.

**Solution:**
- Hard time-box at 25 minutes. Announce this at the start of Step 2.
- Teach a shortcut: have participants process one AC at a time, not all 5 in one prompt:
  ```
  Using #file:mr/diff.patch, does this diff implement AC-1: [paste AC-1 text]? Which file and method? Is it Complete, Partial, or Missing?
  ```
- If a team is stuck at 20 min, give them the answer for AC-4 and AC-5 (both Missing) so they can move forward.

---

### TM2 — Step 5 (MR summary) is rushed

**Risk:** By Step 5, participants are tired. The summary is vague and doesn't include a clear Approve/Request Changes decision.

**Solution:**
- Show a gold standard MR summary structure during the Step 5 demo (before participants start):
  ```
  ## AC Status: 3/5 Complete, 2 Missing (AC-4, AC-5)
  ## Quality Gates: FAILED — 1 SonarQube blocker, coverage gap in UserService
  ## Security: HIGH CVE must be resolved before merge
  ## Testing: Mutation score 72%, 3 surviving mutants need test fixes
  ## Risk: Medium-High
  ## Decision: REQUEST CHANGES
  ## Next Actions: (list 3–5 specific items)
  ```
- Participants who template their prompt against this structure produce much better output.

---

## Skill Risks

### S1 — Participants have never read a `.patch` file

**Risk:** They lose 10 minutes trying to understand the format before they can even start Step 2.

**Solution:**
- Add a **5-minute diff primer** at the start of Exercise 2:
  - `---` / `+++` = old/new file
  - `@@` = hunk header with line numbers
  - `-` = removed line, `+` = added line
  - Show one hunk from the pre-built diff as the example
- Include this primer in the Exercise 2 repo README.

---

### S2 — "Act as a senior reviewer" prompts produce low-quality output

**Risk:** Participants who get a vague response from the Step 4 prompt don't know how to iterate.

**Solution:**
- Teach the "ADD CONSTRAINTS" refinement technique:
  ```
  The comments are too generic. For each comment: add the specific line or method name from the diff, explain WHY it matters (security/correctness/maintainability), and suggest a concrete fix with a code example.
  ```
- Cap each inline comment at 4 lines so participants stay focused.

---

### S3 — Participants don't know what a good final decision looks like

**Risk:** Step 5 summaries say "maybe approve" or have no Decision line.

**Solution:** Show the gold standard structure from TM2 above before Step 5 starts. The Decision must be binary: **Approve** or **Request Changes** — no hedging.
