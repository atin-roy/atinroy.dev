## 🛠️ Incident Report: Native Binding & Resolution Failure

### 1. The Core Issue: The "Phantom" Binary

The initial error—`Could not locate the bindings file`—happened because **`better-sqlite3`** is a **Native C++ Addon**, not a standard JavaScript library.

-   **The Logic:** JavaScript cannot talk to a SQLite database file directly. It needs a "bridge" (the `.node` binary) compiled specifically for your CPU and OS.
-   **The Failure:** Because you are using `pnpm`, which has strict security defaults, it blocked the installation script that compiles this bridge. You had the source code for the bridge, but no "built" bridge to walk across.

### 2. The Turbopack Conflict

You then encountered `Module not found: Can't resolve 'next/dist/client/next-dev-turbopack.js'`.

-   **The Logic:** Turbopack is Next.js's new Rust-based bundler. It is faster than Webpack but significantly stricter about how it resolves file paths.
-   **The Failure:** When the initial `pnpm install` failed or was interrupted, the symbolic links (symlinks) between your project and the global `pnpm` store were corrupted. Turbopack looked for its own internal development tools and found a broken link.

---

### 3. How We Fixed It (Step-by-Step)

| Step                    | Action Taken                                              | Why it worked                                                                                                                                                                           |
| ----------------------- | --------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **I. Whitelisting**     | Added `onlyBuiltDependencies` to `package.json`.          | Told `pnpm` that `better-sqlite3` is "trusted" and allowed to run its C++ compiler during installation.                                                                                 |
| **II. Externalizing**   | Added `serverExternalPackages` to `next.config.ts`.       | Instructed Turbopack **not** to bundle `better-sqlite3`. This forces Next.js to load the binary from the file system at runtime instead of trying to "shrink-wrap" it into a JS bundle. |
| **III. The Force Sync** | Ran `rm -rf .next node_modules` & `pnpm install --force`. | Destroyed the corrupted symlinks and forced a fresh, clean rebuild of the native bindings and the Turbopack cache.                                                                      |

---

### 4. Prevention for Future "Labs"

Since you are working in a `/labs/` directory, keep these rules in mind to avoid this again:

-   **Always use `serverExternalPackages**` for any library that interacts with the OS (Databases, File System watchers, Image processors like Sharp).
-   **Check the `build` folder:** If a native library crashes, check `node_modules/[package]/build`. If that folder is missing, the installation didn't finish the job.
-   **Node Version Stability:** Stick to an LTS version of Node (20.x or 22.x) to ensure the C++ headers match what the libraries expect.
