# Facilitator Tips — GitHub Copilot Hackathon

> Load this reference when running or preparing either exercise.

---

## Before the Session — Preparation Checklist

### 1–2 Days Before
- [ ] Copy starter files from `assets/exercise1/` into `standards-aggregator-lab/sources/`
- [ ] Copy starter files from `assets/exercise2/` into `expert-mr-reviewer-lab/jira/`, `mr/`, `reports/`
- [ ] Run **every Copilot prompt** yourself end-to-end — save your outputs as gold standard answers
- [ ] Verify `diff.patch` is ≤150 lines; verify all class names in reports match the diff
- [ ] Enable **"Use Instruction Files"** in VS Code Copilot settings on your demo machine
- [ ] Print or prepare the participant cheat sheet (see below)

### Morning of the Session
- [ ] Check that every participant machine has GitHub Copilot extension installed and signed in
- [ ] Enable "Use Instruction Files" setting on participant machines (or send config instructions ahead of time)
- [ ] Share the starter repos with participants (zip or GitHub link)
- [ ] Confirm Copilot Chat is accessible from the IDE (panel opens, no auth errors)

---

## Participant Cheat Sheet — Copilot Chat File Context

> Print this or add as `CHEAT-SHEET.md` to the starter repos.

### How to reference a file in Copilot Chat

| Method | How to do it | Works when |
|--------|-------------|------------|
| `#file:` syntax | Type `#file:` then pick from dropdown | File is in the open workspace |
| Drag file tab | Drag the VS Code editor tab into the chat input | File is open in editor |
| Copy-paste | Copy file content, paste into chat before the prompt | Any situation |

### Template for multi-file prompts
```
Using #file:path/to/file1.md and #file:path/to/file2.md, [your instruction here].
```

### If Copilot says it can't see the file
→ Paste the file content directly into the chat before your prompt.

---

## Steps That Need a Live Demo

| Exercise | Step | What to Demo | Duration |
|----------|------|-------------|----------|
| Both | Start | File context injection using `#file:` and drag-drop | 5 min |
| Ex 1 | Step 2 | Full iteration cycle: prompt → output → refine prompt | 5 min |
| Ex 1 | Step 3 | Show MUST/SHOULD language examples (good vs bad) | 3 min |
| Ex 1 | Step 5 | Show a violation output — what "done" looks like | 3 min |
| Ex 2 | Intro | Read a `.patch` file — explain `---`, `+++`, `@@`, `+`, `-` | 5 min |
| Ex 2 | Step 4 | Show a good inline comment vs. a vague one | 3 min |
| Ex 2 | Step 5 | Show the gold standard MR summary structure | 3 min |

---

## Time Boxes

### Exercise 1 (~95 min)

| Step | Activity | Time | Notes |
|------|----------|------|-------|
| Step 0 | Setup + file context demo | 10 min | Most important demo block |
| Step 1 | Generate schema | 15 min | Usually fast — a good warm-up |
| Step 2 | Aggregate sources | **30 min** | Announce the time box — highest iteration risk |
| Step 3 | Generate instructions | 15 min | Remind MUST/SHOULD format |
| Step 4 | Generate PR template | 10 min | Usually fast |
| Step 5 | Validate bad example | 15 min | The payoff — protect this time |

### Exercise 2 (~100 min)

| Step | Activity | Time | Notes |
|------|----------|------|-------|
| Intro | Diff format primer | 5 min | Do before Step 1, not after |
| Step 1 | Extract AC checklist | 15 min | Usually fast |
| Step 2 | Map diff to AC | **25 min** | Hardest step — announce time box |
| Step 3 | Summarize quality signals | 20 min | Encourage grouping: Blockers first |
| Step 4 | Draft inline comments | 20 min | Teach "ADD CONSTRAINTS" refinement |
| Step 5 | Write MR summary | 15 min | Show structure first — Decision must be binary |

---

## Common Questions + Answers

| Question | Answer |
|----------|--------|
| "How do I give Copilot my file?" | Demo `#file:` syntax. If it doesn't work, paste the content directly. |
| "Copilot isn't reading the diff correctly" | Break the prompt: process one AC at a time, not all at once. |
| "My output is different from my neighbour's" | Normal — Copilot is non-deterministic. Quality of reasoning matters, not exact wording. |
| "Is my copilot-instructions.md actually working?" | Check VS Code Settings → search "copilot instructions" → toggle must be ON. |
| "The inline comments are too generic" | Teach: add `#file:` references + ask for specific method names and a code example fix. |
| "Should we Approve or Request Changes?" | In a real review: if any blocker exists → Request Changes. For this exercise, same rule applies. |

---

## Connecting Both Exercises (Bonus Step)

If teams finish early or you have time at the end, run the bonus step:

1. Take the `.github/copilot-instructions.md` produced in Exercise 1
2. Copy it into the Exercise 2 repo's `.github/` folder
3. Add to the Step 4 prompt:
   ```
   Also enforce the rules in #file:.github/copilot-instructions.md during your review. Flag any violations as an additional category.
   ```

This shows **team-aligned Copilot** — the standards from Exercise 1 feed the review in Exercise 2. It's the strongest demo moment of the whole session.

---

## Judging Guide

### Exercise 1 Rubric

| Criterion | 0 pts | 1 pt | 2 pts |
|-----------|-------|------|-------|
| **Clarity (30%)** | No examples, vague rules | Some examples, some measurable | All rules measurable + good/bad examples |
| **Completeness (30%)** | <3 sections covered | 4–5 sections covered | All 6 sections with content |
| **Copilot usability (40%)** | No MUST/SHOULD, paragraph format | Some directive language | Full MUST/SHOULD, ≤60 lines, scannable |

### Exercise 2 Rubric

| Criterion | 0 pts | 1 pt | 2 pts |
|-----------|-------|------|-------|
| **AC traceability (35%)** | No mapping or all blank | Some ACs mapped with file names | All 5 ACs with file/method + Missing/Partial/Complete |
| **Tool findings (35%)** | Missing or all lumped together | Identified blockers vs warnings | Blockers correctly prioritized + justified recommendation |
| **Comment usefulness (30%)** | Vague ("fix this") | Has file + issue | Has file + method + why it matters + suggested fix |
