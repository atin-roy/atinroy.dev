# Monorepo Tasks (pnpm workspace)

Common commands for your monorepo setup.

## Install Dependencies
```bash
# Install all packages (at root)
pnpm install

# Install for specific workspace
pnpm install --filter @workspace/package-name

# Add dependency to specific package
pnpm add <package> --filter <workspace-name>
```

**Why pnpm?** 
- **Faster**: Content-addressable storage (symlinks shared packages)
- **Efficient**: Uses 30-50% less disk space than npm/yarn
- **Strict**: Prevents phantom dependencies (only declared deps accessible)

## Build Commands
```bash
# Build all packages
pnpm build

# Build specific package
pnpm --filter <package-name> build

# Build with dependencies
pnpm --filter <package-name>... build  # Triple dot includes dependencies
```

## Run Scripts
```bash
# Run dev server for web app
pnpm --filter web dev

# Run tests for all packages
pnpm -r test  # -r = recursive (all workspaces)

# Run specific script in all packages
pnpm -r lint
```

## Workspace Structure
```
atinroy.dev/
├── apps/
│   └── web/          → Next.js app
├── packages/
│   ├── ui/           → Shared components
│   └── config/       → Shared configs
└── pnpm-workspace.yaml
```

## Add New Package to Workspace
```bash
# 1. Create directory
mkdir -p packages/new-package

# 2. Initialize package
cd packages/new-package
pnpm init

# 3. Update pnpm-workspace.yaml (if not using wildcards)
# Add: - "packages/new-package"

# 4. Link workspace dependencies
# In package.json:
{
  "dependencies": {
    "@workspace/ui": "workspace:*"
  }
}

# 5. Install
pnpm install
```

## Troubleshooting
```bash
# Clear all node_modules and reinstall
pnpm -r exec rm -rf node_modules
pnpm install

# Check which packages are in workspace
pnpm list -r --depth 0

# Verify dependency graph
pnpm why <package-name>
```

**Trade-off:** Monorepos add complexity but enable code sharing and atomic commits across projects.
