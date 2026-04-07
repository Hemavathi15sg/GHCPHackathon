# Exercise 1 — Risks, Solutions & Mitigation Guide

> For use by the hackathon facilitator. Review before the session and prepare mitigations in advance.

---

## Technical Risks

### T1 — Copilot Chat does not automatically read files by path

**Risk:** Participants paste a prompt like "Read the files in /sources" but Copilot has no access to their filesystem. This is the #1 blocker.

**Solution:**
- Add a **"How to inject file context"** section to the repo README before the session.
- Demo this explicitly at the start of Step 1: in Copilot Chat, type `#file:` and pick the file from the dropdown, or drag the file tab into the chat window.
- Provide the participant cheat sheet (see `facilitator-tips.md`) — print it or link from README.

**Fallback:** Ask participants to open the file and paste its content directly into the chat prompt. This always works.

---

### T2 — `copilot-instructions.md` silently does nothing

**Risk:** Participants create `.github/copilot-instructions.md` but the VS Code setting "Use Instructions" is not enabled, so the file has no effect.

**Solution:**
- Before the session, on every participant machine: open VS Code Settings → search "copilot instructions" → enable **GitHub Copilot: Use Instruction Files**.
- Show participants the ✅ indicator in Copilot Chat that confirms instructions are loaded.
- If the setting was off during review, the participant can re-run the Step 5 prompt after enabling it.

---

### T3 — Step 5 cross-file review produces vague output

**Risk:** Copilot may only flag cosmetic issues in `bad_example.*` and miss the structural violations if workspace indexing is incomplete.

**Solution:**
- Add all referenced files (instructions + bad example) as explicit `#file:` references in the Step 5 prompt rather than relying on implicit workspace context.
- Suggested improved prompt:
  ```
  Review #file:sample/bad_example.java against the rules in #file:.github/copilot-instructions.md and #file:output/coding-standards.md. List each violation with: rule violated, line number, why it matters, and corrected code.
  ```

---

## Content Risks

### C1 — `sources/*` files are not provided — facilitator must create them

**Risk:** If source files are too clean, Step 2 aggregation is trivial. If too complex, Copilot loses the thread.

**Solution:**
- Use the pre-built templates in [`../assets/exercise1/`](../assets/exercise1/) — `confluence_export.md`, `public_guidelines.md`, `team_notes.md` are ready to use.
- Each file has intentional contradictions and duplicates labeled in comments so the facilitator understands what Copilot should resolve.
- Do NOT simplify the files — the messiness is the exercise.

---

### C2 — No reference example of a good `copilot-instructions.md`

**Risk:** Participants don't know if their output is any good; judging becomes subjective.

**Solution:**
- Use [`../assets/exercise1/copilot-instructions-seed.md`](../assets/exercise1/copilot-instructions-seed.md) as the gold standard during judging.
- Share it with participants only **after** they've produced their own version — not before.
- Scoring rubric checklist: MUST/SHOULD language used? Rules are ≤2 sentences each? Covers all 6 sections? Under 60 lines?

---

## Time Risks

### TM1 — Step 2 (aggregation) runs long

**Risk:** Copilot's first output almost always needs 2–3 iterations. Participants spend 30+ minutes here.

**Solution:**
- Time-box Step 2 to 30 minutes explicitly. Announce this aloud.
- Teach the iteration loop early: "If Copilot's output is not good enough, refine your prompt — don't start over."
- Suggested refinement prompt:
  ```
  The output is missing measurable rules for Security. Also the Error Handling section still has a vague rule ("handle errors properly"). Fix both issues.
  ```
- If a team is still stuck at 25 min, give them the seed file for the relevant section only.

---

### TM2 — Step 5 (validation) is skipped

**Risk:** The most engaging step gets cut because participants run out of time.

**Solution:**
- Announce Step 5 as time-boxed (15 min) from the start — frame it as the "payoff" step.
- Have a pre-built `sample/bad_example.java` ready to hand to teams that are running late so they can still experience the validation step.
- Pre-built bad example path: [`../assets/exercise1/bad_example_seed.java`](../assets/exercise1/bad_example_seed.java) *(create this as a helper file)*.

---

## Skill Risks

### S1 — Participants unfamiliar with `#file:` context injection

**Solution:** Demo at Step 0 (10 min block). See cheat sheet in `facilitator-tips.md`.

---

### S2 — MUST/SHOULD instruction format is unfamiliar

**Risk:** Participants write instructions that are vague ("be consistent") rather than directive ("MUST use camelCase").

**Solution:**
- Show one before/after example during the Step 3 demo:
  - Bad: *"Try to write consistent naming"*
  - Good: *"MUST use camelCase for variables; MUST NOT use generic names like `data` or `temp`"*
- Point participants to the gold standard seed file after they've drafted their own.
