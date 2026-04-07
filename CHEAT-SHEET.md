# Copilot Chat — Participant Cheat Sheet

> Keep this open during the session. Reference it whenever Copilot says it can't see a file.

---

## How to Give Copilot a File (3 Methods)

### Method 1 — `#file:` syntax *(fastest)*
1. Click in the Copilot Chat input box
2. Type `#file:`
3. Pick the file from the dropdown that appears
4. Continue typing your prompt after the file reference

**Example:**
```
Using #file:sources/confluence_export.md and #file:sources/team_notes.md,
merge these into a single standards document.
```

### Method 2 — Drag the editor tab *(visual)*
1. Open the file in VS Code so it has an editor tab
2. Drag the tab directly into the Copilot Chat input
3. The file reference appears automatically

### Method 3 — Copy and paste *(always works)*
1. Open the file (`Ctrl+A` → `Ctrl+C`)
2. Paste before your prompt in the chat
3. Add your instruction below the pasted content

**Example:**
```
[paste file content here]

---
Summarize the above into structured rules. Group by: Naming, Security, Testing.
```

---

## Multi-File Prompt Template

```
Using #file:path/to/file1.md, #file:path/to/file2.md, and #file:path/to/file3.md:

[Your instruction here. Be specific about what you want Copilot to do with these files.]

Format the output as [table / markdown / checklist / numbered list].
```

---

## If Copilot Says "I Can't See the File"

> This is the #1 blocker. Here's the fix:

**Step 1:** Confirm the file is in your open VS Code workspace (not just on disk — the workspace folder must be open).

**Step 2:** Try `#file:` again and pick from the dropdown.

**Step 3:** If dropdown is empty or file is not listed → paste the file content directly into the chat before your prompt. **This always works.**

---

## MCP Setup — One-Time Configuration

If you are on **Option A (real sources / real tools)**, add the relevant blocks to VS Code `settings.json` (`Ctrl+Shift+P` → _Open User Settings JSON_).

### GitHub MCP — PR diff + GitHub Wiki team notes
```json
{
  "mcp.servers": {
    "github": {
      "type": "http",
      "url": "https://api.githubcopilot.com/mcp/",
      "headers": {
        "Authorization": "Bearer <YOUR_GITHUB_TOKEN>"
      }
    }
  }
}
```

### Atlassian MCP — Confluence pages + Jira ticket
```json
{
  "mcp.servers": {
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

Generate an Atlassian API token at: https://id.atlassian.com/manage-profile/security/api-tokens

After saving, restart VS Code. Verify the server appears in the Copilot Chat **Tools** panel (⚙️ icon).

> **Option B users:** Skip MCP setup entirely — all source files are in the repo.

---

## Enable the "Use Instruction Files" Setting

Your `.github/copilot-instructions.md` only works if this VS Code setting is ON:

1. Open VS Code Settings (`Ctrl+,`)
2. Search: `copilot instructions`
3. Enable: **GitHub Copilot: Use Instruction Files** ✅
4. Restart Copilot Chat
5. Open Copilot Chat — look for a ✅ indicator confirming instructions are loaded

> Without this setting, `.github/copilot-instructions.md` silently does nothing.

---

## Prompt Quality Booster

If Copilot output is generic or vague, add this structure to your prompt:

```
You are a [role — e.g., senior engineer / code reviewer / tech lead].
[Task sentence — exactly what you want produced.]
Format the output as [table / checklist / inline comments / markdown section].
Be specific: [add any constraints — e.g., "resolve ALL contradictions", "use MUST/SHOULD language", "include a corrected code example for each violation"].
```

---

## Exercise 1 — Quick Reference Prompts

### Option B — Mock Files (no MCP needed)

| Step | Prompt Starter |
|------|---------------|
| Step 1 | `Create a markdown schema for coding standards... Include sections: Naming, Error Handling, Logging, Security, Testing, Performance.` |
| Step 3 | `Using #file:sources/confluence_export.md, #file:sources/public_guidelines.md, and #file:sources/team_notes.md: Merge into one standards doc...` |
| Step 4 | `Convert #file:output/coding-standards.md into a concise Copilot instruction file. Use MUST/SHOULD language. Keep under 50 lines.` |
| Step 5 | `Create a PR template checklist from #file:output/coding-standards.md. Cover: security, tests, logging, error handling, performance.` |
| Step 6 (WOW) | `Review #file:src/main/java/com/example/service/UserService.java and #file:src/main/java/com/example/controller/UserController.java against #file:.github/copilot-instructions.md and #file:output/coding-standards.md. List every violation as: Rule Violated \| File \| Method \| Why it matters \| Corrected code.` |

> Step 6 uses `user-management-service` PR branch files. Run `cd ../user-management-service && git checkout feature/USER-142-user-registration` first (Option B), or fetch via GitHub MCP (Option A).

### Option A — MCP Prompts (Confluence + GitHub)

| Step | Copilot Chat Prompt |
|------|--------------------|
| Fetch Confluence | `Search Confluence for pages in the "<space-key>" space that contain coding standards or quality rules. List page titles and IDs.` |
| Fetch page | `Fetch the full content of Confluence page "<page-title>" from space "<space-key>". Save as sources/confluence_export.md.` |
| Fetch team notes | `Using the GitHub MCP, fetch the wiki page "Coding-Standards" from repository "<org>/<repo>". Save as sources/team_notes.md.` |
| Step 6 (WOW) | `Get the contents of src/main/java/com/example/service/UserService.java from the feature/USER-142-user-registration branch of <org>/user-management-service.` then `Review the file above against #file:.github/copilot-instructions.md and #file:output/coding-standards.md. List every violation as: Rule Violated \| File \| Method \| Why it matters \| Corrected code.` |

---

## Exercise 2 — Quick Reference Prompts

### Option B — Mock Files (no MCP or tool runs needed)

| Step | Prompt Starter |
|------|---------------|
| Step 1 | `Read #file:jira/ticket.md. Extract acceptance criteria as a checkbox checklist — each item must be objectively verifiable from code or tests.` |
| Step 2 | `Using #file:mr/diff.patch and #file:output/ac-checklist.md, map each AC to the file and method in the diff. Mark as Complete / Partial / Missing. Exact line numbers optional.` |
| Step 3 | `Summarize #file:reports/sonarqube.md, #file:reports/jacoco.md, #file:reports/pitest.md, #file:reports/trivy.md. Identify: (1) Blockers (2) Warnings (3) Informational. Recommend approve or request changes.` |
| Step 4 | `Act as a senior reviewer. Using #file:mr/diff.patch, #file:output/ac-traceability.md, and #file:output/quality-gates.md, draft inline review comments with: file, method, issue, why it matters, suggested fix.` |
| Step 5 | `You are a senior engineer. Using #file:output/ac-traceability.md, #file:output/quality-gates.md, #file:output/inline-comments.md: write a 300-word MR review summary. Include: overall verdict (APPROVE / REQUEST CHANGES / REJECT), top 3 strengths, top 3 concerns, next steps.` |

### Option A — MCP + Real Tool Prompts

| Step | Action |
|------|-------|
| Fetch Jira ticket | `Fetch the details of Jira ticket USER-142 including description, acceptance criteria, and definition of done.` |
| Fetch PR diff | `List open pull requests on repository <org>/user-management-service.` then `Get the full diff for pull request #<number> on <org>/user-management-service.` |
| Get file content | `Get the contents of src/main/java/com/example/service/UserService.java from the feature/USER-142-user-registration branch of <org>/user-management-service.` |

**Terminal commands — run from `user-management-service/` on the PR branch:**

```powershell
git checkout feature/USER-142-user-registration

# JaCoCo coverage report
mvn clean test jacoco:report
# → open target/site/jacoco/index.html

# PITest mutation testing (takes 3–5 min)
mvn pitest:mutationCoverage
# → open target/pit-reports/<timestamp>/index.html

# Trivy security scan
trivy fs --scanners vuln .

# Install SonarLint (once per machine)
code --install-extension SonarSource.sonarlint-vscode
```

**After running tools, paste all findings into Copilot Chat with this prompt:**
```
I ran all quality tools on the PR branch. Here are the findings:
SonarLint: [paste Problems panel]
JaCoCo: UserService 45% line coverage (threshold 80%), UserController 85%
PITest: 72% mutation score, 3 surviving mutants
Trivy: 1 HIGH CVE — CVE-2022-22978 in spring-security-crypto:5.7.3

Summarize as quality-gates.md: Blockers | Warnings | Informational.
Recommend: Approve or Request Changes, with justification.
```
