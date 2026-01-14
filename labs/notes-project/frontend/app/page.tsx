export default function Home() {
  return (
    <main className="min-h-screen bg-background px-6 py-16 text-foreground">
      <section className="mx-auto max-w-3xl space-y-10">
        <header>
          <p className="text-sm uppercase tracking-[0.3em] text-muted-foreground">
            Notes project
          </p>
          <h1 className="mt-4 text-4xl font-semibold">Clean canvas</h1>
          <p className="mt-2 text-lg text-muted-foreground">
            Everything is stripped back so you can start building. Leave this
            page as a launch pad for components you want to explore.
          </p>
        </header>
        <article className="rounded-2xl border border-muted-foreground/50 bg-white/90 p-6 shadow-lg shadow-black/5 dark:bg-black/70 dark:shadow-white/5">
          <h2 className="text-2xl font-medium">What to try next</h2>
          <ul className="mt-4 list-disc space-y-2 pl-4 text-base leading-relaxed text-muted-foreground">
            <li>Set up your preferred layout system (flex, grid, etc.).</li>
            <li>Create a component per note and compose it here.</li>
            <li>Hook pnpm dev to hot reload while experimenting.</li>
          </ul>
        </article>
      </section>
    </main>
  );
}
