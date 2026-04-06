# Team Notes — Coding Standards
*Captured from Slack #engineering-standards channel and sprint retro notes*

---

hey everyone — dropping some notes from last sprint on stuff that keeps coming up in reviews:

**Naming**
- just use camelCase for variables, its what everyone does anyway
- classes = PascalCase obviously
- stop using names like `x`, `temp`, `data2` — be descriptive

**Error stuff**
- handle errors properly (this keeps getting flagged)
- don't just log and move on — actually do something
- if you're not sure what to do with an exception, at least log it with the full stacktrace

**Logs**
- pls stop logging passwords and user info — this is a compliance issue *(duplicate from confluence_export.md)*
- use the structured logger, not System.out.println or print()
- include the request ID in every log line so we can trace issues

**Tests**
- make sure tests pass before pushing — CI was breaking last week
- write clean tests, not just for coverage
- mock external services in unit tests

**Performance**
- avoid doing DB queries inside loops — we had a production incident because of this
- use pagination for large result sets

**Security** *(vague — missing key rules)*
- don't put secrets in the code
- security is everyone's responsibility

**General**
- write clean code
- keep functions short
- review your own PR before asking for review
- refactor when things get messy

---

*These are informal notes — for the official standards refer to Confluence. But honestly these are what the team actually follows day to day.*

<!-- FACILITATOR NOTE:
Contradictions planted in this file vs others:
1. NAMING: says "just use camelCase" (agrees with public_guidelines.md, conflicts with confluence_export.md snake_case) — this is the tiebreaker that resolves the contradiction in favour of camelCase
2. SECURITY: extremely vague ("security is everyone's responsibility") — conflicts with the detailed rules in public_guidelines.md and confluence_export.md; Copilot must notice the gap and fill from other sources
3. DUPLICATE: "pls stop logging passwords" mirrors the rule in confluence_export.md — Copilot should deduplicate into one clear rule
4. VAGUE RULES: "write clean code", "keep functions short", "handle errors properly" — unmeasurable rules Copilot should either make measurable or discard as tribal noise
-->
