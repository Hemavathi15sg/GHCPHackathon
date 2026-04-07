# Exercise 1 — Option A: Real Source Fetching via MCP & Terminal

> Use this guide when participants fetch LIVE data instead of the provided seed MD files (Option B).

---

## Overview: Option A vs Option B

| | Option A (Real) | Option B (Mock) |
|---|---|---|
| Confluence | Fetch via Atlassian MCP | Use `sources/confluence_export.md` |
| Public guidelines | Fetch via Copilot web fetch / curl | Use `sources/public_guidelines.md` |
| Team notes | Fetch via Confluence MCP or GitHub Wiki API | Use `sources/team_notes.md` |
| Validation (Step 5) | Run against real Java repo PR branch | Single `bad_example.java` file |
| Setup effort | High (MCP config needed) | Zero — files provided |
| Realism | High | Medium |

Use **Option A** when participants have company Atlassian accounts and you want maximum real-world authenticity.
Use **Option B** for time-constrained sessions or when participants don't have Confluence access.

---

## Prerequisite: MCP Servers Required

### 1. Atlassian MCP (for Confluence + Jira)

Atlassian provides a Remote MCP Server for Confluence and Jira. Participants need:

- An Atlassian account with access to a Confluence space that contains coding standards pages
- The Atlassian MCP server configured in VS Code

**Setup in VS Code `settings.json`:**
```json
{
  "mcp.servers": {
    "atlassian": {
      "type": "http",
      "url": "https://mcp.atlassian.com/v1/mcp",
      "headers": {
        "Authorization": "Bearer <ATLASSIAN_API_TOKEN>"
      }
    }
  }
}
```

Generate an Atlassian API token at: https://id.atlassian.com/manage-profile/security/api-tokens

> Official Atlassian MCP docs: https://developer.atlassian.com/cloud/jira/platform/mcp/

### 2. GitHub MCP (for team notes in GitHub Wiki)

If team notes live in a GitHub Wiki, the GitHub MCP server is needed.

**Setup in VS Code `settings.json`:**
```json
{
  "mcp.servers": {
    "github": {
      "type": "http", 
      "url": "https://api.githubcopilot.com/mcp/",
      "headers": {
        "Authorization": "Bearer <GITHUB_TOKEN>"
      }
    }
  }
}
```

> Official GitHub MCP docs: https://github.com/github/github-mcp-server

---

## Confluence — Fetching Standards Pages

### Finding relevant pages

In Copilot Chat, once the Atlassian MCP is configured:

```
Search Confluence for pages in the "<your-space-key>" space that contain coding standards, development guidelines, or quality rules. List the page titles and IDs.
```

### Fetching page content

```
Fetch the full content of Confluence page "<page-title>" from space "<space-key>". Save the content as sources/confluence_export.md.
```

### Facilitator prep (before session)

1. Identify 1–3 real Confluence pages in your company space that have coding standards
2. Test that participants can fetch them with the above prompt
3. If pages are too long (>500 lines), ask Copilot to summarize into the key rules only
4. Confirm the pages have contradictions, duplicates, or vague rules — otherwise the Exercise 1 aggregation step is trivial

---

## Public Guidelines — Fetching via Web

Public coding guidelines can be fetched using Copilot's web fetch capability (if the `web` tool or `fetch_webpage` MCP is available) or via terminal.

### Via Copilot Chat (`#fetch` or web tool)

```
Fetch the content from https://google.github.io/styleguide/javaguide.html and extract the key naming, error handling, and security rules. Save as sources/public_guidelines.md.
```

### Via terminal (PowerShell/bash)

```powershell
# Fetch Google Java Style Guide excerpt
curl -s https://google.github.io/styleguide/javaguide.html | python -c "
import sys
from html.parser import HTMLParser

class TextExtractor(HTMLParser):
    def __init__(self):
        super().__init__()
        self.text = []
    def handle_data(self, data):
        self.text.append(data)

parser = TextExtractor()
parser.feed(sys.stdin.read())
print(' '.join(parser.text[:3000]))
" > sources/public_guidelines_raw.txt
```

Then ask Copilot to clean and structure it:
```
Read the raw text in sources/public_guidelines_raw.txt. Extract and structure the naming, error handling, and security rules into clean markdown. Save as sources/public_guidelines.md.
```

### Recommended public sources

| Standard | URL | Best for |
|----------|-----|---------|
| Google Java Style Guide | https://google.github.io/styleguide/javaguide.html | Naming, formatting |
| Oracle Java Code Conventions | https://www.oracle.com/java/technologies/javase/codeconventions-introduction.html | Structural rules |
| OWASP Top 10 (for security rules) | https://owasp.org/www-project-top-ten/ | Security section |

---

## Team Notes — Fetching from Confluence or GitHub Wiki

### From Confluence (team notes page)

```
Fetch the content of the Confluence page "<Team Notes / Engineering Standards>" in space "<space-key>". Save as sources/team_notes.md.
```

### From GitHub Wiki

```
Using the GitHub MCP, fetch the wiki page "Coding-Standards" from repository "<org>/<repo>". Save the content as sources/team_notes.md.
```

---

## Validation Step (Step 5) — Using the Real Java Repo

Instead of a single `bad_example.java`, participants validate their generated standards against the **real PR branch** of the `user-management-service` Java repo.

**Copilot Chat prompt:**
```
I have a PR branch checked out locally. The changed files are in #file:src/main/java/com/example/service/UserService.java and #file:src/main/java/com/example/controller/UserController.java.

Review these files against the rules in #file:.github/copilot-instructions.md and #file:output/coding-standards.md. List every violation with: rule violated, file, method, and proposed fix.
```

The PR branch code (`user-management-service` feature branch) contains:
- A plain-text password being logged (Security rule violation)
- Missing email format validation (Testing/Validation rule violation)
- Cognitive complexity above threshold (Code Quality violation)
- A hardcoded BCrypt encoder instance (Design rule violation)

This gives participants real, non-trivial violations to find and fix — far more valuable than a single synthetic bad_example file.

---

## Pre-session Checklist (Option A)

- [ ] Atlassian MCP configured in VS Code and tested on facilitator machine
- [ ] Confirmed 2–3 Confluence pages exist with real standards content
- [ ] Confirmed GitHub MCP works if using GitHub Wiki for team notes
- [ ] Tested all fetch prompts end-to-end (sources saved as expected)
- [ ] Verified the `user-management-service` PR branch is pushed to GitHub
- [ ] Participants have Atlassian API tokens generated before the session
- [ ] Have Option B seed files as backup in case MCP access fails
