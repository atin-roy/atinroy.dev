# Report 1 – Missing Node Runtime for pnpm DLX

**Date:** 2026-01-14  
**Severity:** medium  

## What happened
Running `pnpm create next-app@latest .` inside `labs/notes-project/frontend` failed before showing the prompts because the pnpm “dlx” script executed `node` but the pnpm-managed runtime hadn’t been provisioned yet. The error shown was `node: not found`, even though `node -v` works in the shell.

## Fix
Installed and activated the Node runtime that pnpm expects by running `pnpm env use --global 24`. After that, rerunning the creation command reached the default prompt and the project scaffold could be generated normally.

## What I learned
pnpm’s dlx/ create scripts rely on the runtime added through `pnpm env use`. The shell’s PATH alone isn’t enough for those cached scripts, so proactively activating the desired Node version for pnpm avoids “node: not found” errors when the CLI spins up temporary binaries.
