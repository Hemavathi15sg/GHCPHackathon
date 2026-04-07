# Standards Aggregator Lab

> **Exercise 1 — GitHub Copilot Hackathon**

Use GitHub Copilot Chat to consolidate messy, multi-source coding standards into a single structured document, then generate a Copilot instructions file and a PR checklist template.

---

## Delivery Options

| | Option A — Real Sources (MCP) | Option B — Mock Files |
|---|---|---|
| Confluence | Fetch live via Atlassian MCP | Use `sample sources/confluence_export.md` |
| Public guidelines | Fetch via terminal or Copilot web fetch | Use `sample sources/public_guidelines.md` |
| Team notes | Fetch via Atlassian MCP or GitHub MCP | Use `sample sources/team_notes.md` |
| Step 6 validation | PR branch files via GitHub MCP | PR branch files via local `git checkout` |
| Setup effort | High — MCP config required | Zero — files provided |

Use **Option A** when participants have Atlassian accounts and you want maximum real-world authenticity.
Use **Option B** for time-constrained sessions or when Confluence access is unavailable. *(If Option A sources couldn't be fetched, use the files in `sample sources/` as fallback)*

---

## Repo Structure

```
standards-aggregator-lab/
  sample sources/
    confluence_export.md     ← old Confluence wiki export  (Option B)
    public_guidelines.md     ← public style guide excerpt  (Option B)
    team_notes.md            ← informal Slack / retro notes (Option B)
  output/
    (empty — you'll fill this)
  .github/
    (empty — you'll fill this)
  README.md

Validation (Step 6) uses files from the shared `user-management-service/` repo (PR branch).
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

## MCP Setup (Option A only)

Skip this section if you are using Option B (mock files).

### Atlassian MCP — for Confluence pages and Jira

Add to VS Code `settings.json` (`Ctrl+Shift+P` → _Open User Settings JSON_):

```json
{
  "servers": {
    "atlassian": {
      "type": "http",
      "url": "https://mcp.atlassian.com/v1/mcp",
      "headers": {
        "Authorization": "Bearer <YOUR_ATLASSIAN_API_TOKEN>"
      }
    }
  }
}
```

Generate an API token at: https://id.atlassian.com/manage-profile/security/api-tokens

### Update GitHub MCP within servers— for team notes in a GitHub Wiki

```json

    "github": {
      "type": "http",
      "url": "https://api.githubcopilot.com/mcp/",
      "headers": {
        "Authorization": "Bearer <YOUR_GITHUB_TOKEN>"
      }
    }

Generate an API token from github dev settings → Developer settings → Personal access tokens → Generate new token (classic) → select scopes (repo, read:org, read:user) → Generate token.
  
```

After adding either server, restart VS Code and verify the MCP server appears in the Copilot Chat **Tools** panel.

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

### Step 2 — Fetch / Gather Source Files

#### Option A — Fetch from Confluence and GitHub (MCP required)

**Fetch Confluence coding standards pages:**



```
Fetch the full content of Confluence page link "<page-link>".
Save the content as sources/confluence_export.md.
```

**Fetch public guidelines via terminal - make sure be path of source (PowerShell):**

```powershell
# Fetch Google Java Style Guide and extract text
Invoke-WebRequest -Uri "https://google.github.io/styleguide/javaguide.html" -OutFile sources/public_guidelines_raw.html
```

Then ask Copilot to clean it:

```
Read the raw HTML in #file:sources/public_guidelines_raw.html.
Extract and structure the naming, error handling, and security rules into clean markdown.
Save as sources/public_guidelines.md.
```

**Fetch team notes from Confluence:**

```
Fetch the content of the Confluence page "<Team Notes / Engineering Standards>"
in space "<space-key>". Save as sources/team_notes.md.
```


#### Option B — Use Mock Seed Files (no setup required use only if Option A isn't feasible)

All three source files are already in `sample sources/`. Rename it as `sources/` and proceed directly to Step 3.

---

### Step 3 — Aggregate Sources into a Single Standards Doc

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

### Step 4 — Generate a Copilot Instructions File

**Create:** `.github/copilot-instructions.md`

```
Convert #file:output/coding-standards.md into a concise instruction file for GitHub Copilot in .github/copilot-instructions.md.
Make it directive, short, and optimized for AI coding help.
Use MUST/SHOULD language. Include repo-specific conventions.
Keep it under 50 lines.
```

Check the output is saved to `.github/copilot-instructions.md`.

---

### Step 5 — Generate a PR Checklist Template

**Create:** `.github/pull_request_template.md`

```
Create a PR template checklist derived from #file:output/coding-standards.md.
Make it quick to complete. Include: security, tests, logging, error handling, performance, backward compatibility.
```

Save to `.github/pull_request_template.md`.

---

### Step 6 — Validate Against the Real PR Branch ⭐ WOW MOMENT

Validation always uses the `user-management-service` PR branch — **no synthetic file needed**.

#### Option A — Fetch files via GitHub MCP (no local clone needed)

```
Get the contents of src/main/java/com/example/service/UserService.java from the
feature/USER-142-user-registration branch of <org>/user-management-service.
```

```
Get the contents of src/main/java/com/example/controller/UserController.java from the
feature/USER-142-user-registration branch of <org>/user-management-service.
```

Then:

```
Review the two files above against the rules in
#file:.github/copilot-instructions.md and #file:output/coding-standards.md.
List every violation as: Rule Violated | File | Method | Why it matters | Corrected code.
```

#### Option B — Local checkout

```powershell
cd ../user-management-service
git checkout feature/USER-142-user-registration
```

Then in Copilot Chat:

```
Review #file:src/main/java/com/example/service/UserService.java and
#file:src/main/java/com/example/controller/UserController.java against the rules in
#file:.github/copilot-instructions.md and #file:output/coding-standards.md.
List every violation as: Rule Violated | File | Method | Why it matters | Corrected code.
```

Watch Copilot catch real violations in real production-destined code — against standards **you** wrote. That's the full loop.

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
