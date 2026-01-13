# Git Workflow Reference

Common Git commands with safe practices.

## Daily Workflow
```bash
# Check status
git status

# Create feature branch
git checkout -b feature/my-feature

# Stage changes
git add .                    # All changes
git add -p                   # Interactive (review each hunk)

# Commit with message
git commit -m "feat: add user authentication"

# Push to remote
git push origin feature/my-feature

# Push and set upstream
git push -u origin feature/my-feature
```

## Conventional Commits
```bash
# Format: <type>(<scope>): <description>

git commit -m "feat: add login button"
git commit -m "fix: resolve navigation bug"
git commit -m "docs: update API documentation"
git commit -m "refactor: simplify auth logic"
git commit -m "test: add user service tests"
git commit -m "chore: update dependencies"

# Types:
# feat: New feature
# fix: Bug fix
# docs: Documentation only
# style: Formatting (no code change)
# refactor: Code change (no fix/feature)
# test: Adding tests
# chore: Maintenance
```

**Why?** Auto-generate changelogs, clear history, better PR reviews.

## Syncing with Main
```bash
# Update main branch
git checkout main
git pull origin main

# Rebase feature branch (cleaner history)
git checkout feature/my-feature
git rebase main

# If conflicts, resolve and continue
git rebase --continue

# Force push (safe after rebase)
git push --force-with-lease  # Safer than --force (checks remote)
```

**Why rebase over merge?** Linear history, easier to bisect, cleaner git log. **Trade-off:** Rewrites history (don't rebase shared branches).

## Undo Changes
```bash
# Unstage file (keep changes)
git restore --staged file.txt

# Discard local changes
git restore file.txt

# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes) ⚠️ DESTRUCTIVE
git reset --hard HEAD~1

# Revert commit (creates new commit)
git revert <commit-hash>  # Safer for shared branches
```

## Stashing
```bash
# Save work in progress
git stash

# Save with message
git stash push -m "WIP: login form"

# List stashes
git stash list

# Apply latest stash (keep in stash)
git stash apply

# Apply and remove from stash
git stash pop

# Apply specific stash
git stash apply stash@{1}

# Delete stash
git stash drop stash@{0}
```

## Viewing History
```bash
# Compact log
git log --oneline

# Graph view
git log --oneline --graph --all

# Search commits
git log --grep="authentication"

# Show changes in commit
git show <commit-hash>

# Blame (who changed each line)
git blame file.txt
```

## Branches
```bash
# List branches
git branch              # Local
git branch -r           # Remote
git branch -a           # All

# Delete branch
git branch -d feature/old      # Safe (checks if merged)
git branch -D feature/old      # Force delete

# Delete remote branch
git push origin --delete feature/old

# Rename current branch
git branch -m new-name
```

## Danger Zone ⚠️
```bash
# Force push (overwrites remote)
git push --force
# PREFER: git push --force-with-lease (checks remote first)

# Hard reset (deletes commits)
git reset --hard HEAD~1
# PREFER: git revert (creates new commit)

# Clean untracked files
git clean -n   # Dry run (preview)
git clean -f   # Delete untracked files
git clean -fd  # Delete files and directories
```

**Never** force push to `main`/`master` or shared branches!

## Useful Aliases
```bash
# Add to ~/.gitconfig
[alias]
    st = status
    co = checkout
    br = branch
    ci = commit
    last = log -1 HEAD
    unstage = restore --staged
    lg = log --oneline --graph --decorate --all
```

## Collaboration
```bash
# Fetch changes (don't merge)
git fetch origin

# Pull with rebase
git pull --rebase origin main

# Interactive rebase (clean history before PR)
git rebase -i HEAD~3  # Last 3 commits

# Cherry-pick (apply specific commit)
git cherry-pick <commit-hash>
```
