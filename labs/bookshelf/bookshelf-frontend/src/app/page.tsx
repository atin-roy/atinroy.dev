import { Button } from "@/components/ui/button";
import Link from "next/link";

export default function Home() {
  return (
    <main className="relative min-h-screen bg-background px-6 py-16 text-foreground">
      <div className="mx-auto flex max-w-4xl flex-col gap-10">
        <section className="space-y-6">
          <p className="text-xs uppercase tracking-[0.6em] text-muted-foreground">
            Bookshelf · UI system ready
          </p>
          <h1 className="text-4xl font-semibold leading-tight sm:text-5xl">
            Shadcn UI gives this layout responsive, accessible primitives
          </h1>
          <p className="text-lg text-muted-foreground">
            Tailwind CSS 4 plus the Shadcn theme deliver color tokens,
            animations, and utility-first defaults so we can prototype the
            actual bookshelf experience fast.
          </p>
        </section>

        <section className="grid gap-4 sm:flex">
          <Link href="/catalog">
            <Button className="w-full sm:w-auto">Browse catalog</Button>
          </Link>
          <Button variant="outline" className="w-full sm:w-auto">
            Add a book
          </Button>
        </section>
      </div>
    </main>
  );
}
