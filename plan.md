# Hackathon Trainer Plan — GitHub Copilot Exercises

> Source: `GHCP - Hackathon.md`
> Prepared: April 6, 2026

---

## 📋 Exercise Overview

### Exercise 1 — "Coding Standards Aggregator & Enhancer"

Participants use GitHub Copilot Chat to consolidate messy, multi-source coding standards into a single structured document, then generate a Copilot instructions file and PR checklist from it. A final validation step asks Copilot to review a deliberately bad code sample against the generated standards. The exercise teaches Copilot as a **document synthesizer**, not just a code autocomplete tool.

- **Target audience:** Mid-level developers, tech leads, DevOps engineers
- **Assumed skill level:** Comfortable with VS Code and Git; Copilot beginner to intermediate
- **Realistic time estimate:** 90–120 min *(document implies ~45–60 min)*

### Exercise 2 — "Expert MR Code Reviewer (GitHub flow simulation)"

Participants build a Copilot-powered merge request review workflow using a simulated Jira ticket, a code diff patch, and quality tool reports (SonarQube, JaCoCo, PITest, Trivy). Step by step they produce: an AC checklist, a traceability matrix, a quality gates summary, inline review comments, and a final MR decision summary. The exercise teaches Copilot as a **multi-source analyst and reviewer**.

- **Target audience:** Senior developers, QA engineers, engineering leads
- **Assumed skill level:** Familiar with code review practices and quality tooling concepts
- **Realistic time estimate:** 90–110 min *(document implies ~60 min)*

---

## 🗂️ Structural Map

### Exercise 1

| Step | Goal | Input | Output | Copilot Role |
|------|------|-------|--------|--------------|
| 0 | Enable Copilot in IDE | Local environment | Copilot Chat open | None — prerequisite |
| 1 | Create standards schema | Prompt (paste as-is) | `output/standards.schema.md` | Generator |
| 2 | Aggregate & normalize sources | `sources/*` + schema | `output/coding-standards.md` | Synthesizer |
| 3 | Generate Copilot instructions file | `coding-standards.md` | `.github/copilot-instructions.md` | Transformer |
| 4 | Generate PR checklist template | `coding-standards.md` | `.github/pull_request_template.md` | Formatter |
| 5 | Validate with bad example | Bad sample file + instructions | Violation list + corrected code | Reviewer |

### Exercise 2

| Step | Goal | Input | Output | Copilot Role |
|------|------|-------|--------|--------------|
| 1 | Extract AC checklist from ticket | `jira/ticket.md` | `output/ac-checklist.md` | Extractor |
| 2 | Map diff to acceptance criteria | `mr/diff.patch` + checklist | `output/ac-traceability.md` | Analyst |
| 3 | Summarize quality tool signals | `reports/*.md` (4 files) | `output/quality-gates.md` | Aggregator |
| 4 | Draft inline review comments | diff + traceability + gates | `output/inline-comments.md` | Senior Reviewer |
| 5 | Write final MR review summary | All prior outputs | `output/mr-review-summary.md` | Decision Maker |

---

## ✅ Positives

### Exercise 1
- **"Tribal knowledge → enforceable standard"** is a real pain point — participants immediately see the value
- **Progressive deliverables** — each step builds on the previous; teams can demo partial work if time runs short
- **Copilot as synthesizer** is a powerful mental model shift; most attendees only know Copilot as autocomplete
- **Step 5 (validation)** is the most memorable moment — writing bad code then watching Copilot catch it is satisfying and concrete
- **Clear scoring rubric** (Clarity/Completeness/Usability) is objective and fast to apply during judging
- **Starter repo scaffolding** eliminates blank-page paralysis for participants

### Exercise 2
- **End-to-end MR simulation** mirrors a real senior engineer's review workflow — immediately applicable after the hackathon
- **Traceability step (Step 2)** teaches a discipline most developers skip: verifying that every AC is covered in the diff
- **Multi-tool integration** demonstrates that Copilot can synthesize outputs from SonarQube, JaCoCo, PITest, and Trivy in one pass
- **Prompt chaining across steps** is a transferable technique participants can use on real PRs
- **Five clear output files** make judging fast, fair, and consistent across teams

---

## ⚠️ Difficulties & Risks

### Exercise 1

**Technical Risks**
- Copilot Chat does **not** automatically read files by path — participants must manually attach or paste file contents; this is the #1 blocker for first-timers
- `.github/copilot-instructions.md` requires the "Use Instructions" setting to be enabled in VS Code; if it's off, the file silently does nothing
- Step 5 (cross-file review) depends on workspace indexing quality; results will vary between machines and Copilot versions

**Content Risks**
- `sources/*` starter files are described but **not provided** — facilitator must create convincingly messy content; too clean = trivial output, too complex = confuses Copilot
- No reference example of a "good" `copilot-instructions.md` — participants have nothing to benchmark their output against
- "Populate with messy standards" is vague prep guidance for first-time facilitators

**Time Risks**
- Step 2 (aggregation) will consume the most time — Copilot output almost always needs 2–3 iterations; budget 25–30 min here alone
- Step 5 (validation) is most often skipped under time pressure despite being the most engaging

**Skill Risks**
- Participants unfamiliar with Copilot Chat's file context features will lose 10–15 min just figuring out how to reference a file
- Writing and evaluating `.github/copilot-instructions.md` in MUST/SHOULD format is unfamiliar to many developers

---

### Exercise 2

**Technical Risks**
- `mr/diff.patch` must be realistic but size-controlled — a large patch + 4 report files may exceed Copilot's context window, producing truncated output in Step 4
- Step 2 asks Copilot to reason about specific line numbers in a diff — this is fragile; accuracy varies significantly run-to-run
- All 6 starter files must be hand-crafted with coherent, cross-referencing content — this is the highest prep burden of either exercise

**Content Risks**
- `ticket.md` and `diff.patch` must be **narratively coherent** — the diff must actually implement (some of) the ACs, or Step 2 traceability breaks
- Report files need real-looking, distinctive issues per tool — generic placeholder text produces flat, undifferentiated output
- Participants may not know what SonarQube / JaCoCo / PITest / Trivy output looks like in practice

**Time Risks**
- Step 2 (traceability) is the intellectually hardest step — participants iterate repeatedly; budget 25–30 min
- Step 5 (MR summary) is often rushed because participants are mentally tired after Steps 1–4, yet it's the most visible deliverable for judges

**Skill Risks**
- Most developers have never read a raw `.patch` file — a 5-minute diff format primer is essential before Step 2
- Quality tool outputs are simulated as Markdown (not real JSON/XML) — facilitator should acknowledge this simplification upfront
- "Act as a senior reviewer" prompt style is new to many; participants who get low-quality output won't know how to iterate

---

## 🛠️ Facilitator Tips

### Prepare in Advance
- Create all starter file content (`sources/*`, `jira/ticket.md`, `diff.patch`, `reports/*.md`) with realistic, internally consistent data — allow **2–3 hours** of prep
- Build a "gold standard" answer set for every key output file so you can quickly unblock stuck participants
- Run every Copilot prompt yourself the day before — model behaviour varies and prompts may need small adjustments
- Prepare a **1-page cheat sheet** explaining how to inject file context into Copilot Chat (`#file:`, drag-and-drop, copy-paste patterns)
- Verify that all participant machines have the "Use Instructions" Copilot setting enabled before the session starts

### Steps That Need a Live Demo
| Exercise | Step | What to Demo |
|---|---|---|
| Ex 1 | Step 0 | How to attach a file in Copilot Chat using `#file:` |
| Ex 1 | Step 2 | One full iteration cycle: paste prompt → review output → refine prompt |
| Ex 1 | Step 5 | Show the violation output so participants know what "done" looks like |
| Ex 2 | Step 1 | How to read a raw `.patch` file; what a line number reference means |
| Ex 2 | Step 4 | Show a good inline comment vs a vague one — quality bar setting |
| Ex 2 | Step 5 | Show a complete MR summary so participants calibrate length and tone |

### Where to Expect the Most Questions
- **"How do I give Copilot the file?"** — have the cheat sheet ready and demo this before Step 1 of each exercise
- **"Copilot isn't reading the diff correctly"** — suggest breaking the prompt into smaller chunks (one AC at a time)
- **"My output looks different from my neighbour's"** — remind participants Copilot is non-deterministic; quality of reasoning matters more than exact wording
- **"Is my copilot-instructions.md actually working?"** — walk them through the VS Code setting and the green indicator

### Suggested Time Boxes

#### Exercise 1
| Step | Activity | Time |
|---|---|---|
| Step 0 | Setup check + file context demo | 10 min |
| Step 1 | Generate schema | 15 min |
| Step 2 | Aggregate sources | 30 min |
| Step 3 | Generate Copilot instructions | 15 min |
| Step 4 | Generate PR template | 10 min |
| Step 5 | Validate with bad example | 15 min |
| **Total** | | **95 min** |

#### Exercise 2
| Step | Activity | Time |
|---|---|---|
| Intro | Diff format primer | 5 min |
| Step 1 | Extract AC checklist | 15 min |
| Step 2 | Map diff to AC | 25 min |
| Step 3 | Summarise quality signals | 20 min |
| Step 4 | Draft inline comments | 20 min |
| Step 5 | Write MR summary | 15 min |
| **Total** | | **100 min** |

---

## 🔁 Suggested Improvements

- **Add a context injection cheat sheet** as a repo file — a single reference explaining `#file:`, drag-drop, and copy-paste patterns would eliminate ~40% of Step 0/1 blockers
- **Include one worked example** per exercise (e.g., a pre-filled `standards.schema.md` or a sample `ac-checklist.md`) so participants have a quality reference to aim for
- **Cap `diff.patch` at 100–150 lines** — anything larger degrades Copilot reasoning quality in Steps 2 and 4 of Exercise 2
- **Promote the Bonus section to Step 6** — linking both exercises is the most impressive demo moment of the whole session; it should be a built-in step, not an afterthought
- **Add a 5-min "what is a .patch file" primer** to the Exercise 2 README to remove that prerequisite gap before participants reach Step 2
