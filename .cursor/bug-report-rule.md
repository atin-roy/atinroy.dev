# Bug Report Rule

Every time you uncover a bug inside a lab, run the helper script below so the reports stay sequential and consistent:

```bash
.cursor/new-bug-report.sh labs/<project-name> "Short descriptive title"
```

The script creates (or reuses) `labs/<project-name>/logs/report-N-title.md` with placeholders for:

- **What happened** – describe the observable failure/behavior.
- **Fix** – the concrete change you applied to resolve it.
- **What I learned** – quick insight to remember next time.

Adjust the severity, date, and details inside the generated file before committing. Keeping the logs folder per project gives you a clear timeline without rewriting the prompt.
