# Git Workflow & Standards

This document defines how we manage version control in this monorepo.

**Core Philosophy:**

- **Honesty > Perfection:** Commit history should reflect what actually happened.
- **Clarity > Brevity:** Explain _why_ a change was made, not just _what_.
- **Atomic Commits:** One logical change per commit (when possible).

---

## 1. Branching Strategy

We use a simplified **Trunk-Based Development** model.

- **`main`**: The source of truth. Always deployable (or at least buildable).
- **Feature Branches**: `feat/my-feature` or `fix/issue-description`.
- **Labs**: `lab/experiment-name` (for messy, experimental branches).

### The Flow

1.  Checkout `main` and pull latest.
2.  Create branch: `git checkout -b feat/add-weather-db`.
3.  Work, commit, push.
4.  Merge back to `main` (Squash or Merge Commit are both fine, just be consistent per feature).

---

## 2. Commit Message Convention

We use **Conventional Commits**. This creates a readable history and makes it easy to scan what touched the "infra" vs "apps".

**Format:**
`type(scope): description`

**Types:**

- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation only changes
- `style`: Formatting, missing semi-colons, etc. (no code change)
- `refactor`: A code change that neither fixes a bug nor adds a feature
- `chore`: Updating build tasks, package manager configs, etc.

**Scopes (Examples):**

- `(root)`: Repo-wide changes
- `(web)`: The Next.js app
- `(api)`: The Spring Boot app
- `(infra)`: Docker, database configs
- `(lab)`: Specific lab changes

**Examples:**

- `feat(web): add dark mode toggle`
- `fix(api): handle null pointer in user service`
- `chore(infra): add mongodb to docker-compose`
- `docs(root): update git workflow guide`

---

## 3. The "Honest History" Rule

Do not obsess over rebasing every single typo fix into a perfect commit.

- If you made a mistake and fixed it in the next commit, that's fine.
- **Progress beats polish.**
- Exception: If you committed a secret/password, you MUST rewrite history to remove it.

---

## 4. Tagging

- Use tags for major milestones or completed projects.
- Format: `v1.0.0-projectname` or just `v1.0.0` for the whole platform.
