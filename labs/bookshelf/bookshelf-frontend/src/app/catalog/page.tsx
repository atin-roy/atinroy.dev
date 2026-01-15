import BookTile from "@/components/BookTile";

const dummyBooks = [
  {
    title: "Refactoring",
    author: "Martin Fowler",
    description:
      "A practical guide to improving existing codebases without rewriting them.",
  },
  {
    title: "Domain-Driven Design",
    author: "Eric Evans",
    description:
      "A blueprint for tackling complex domains with rich models and clear boundaries.",
  },
  {
    title: "Clean Architecture",
    author: "Robert C. Martin",
    description:
      "Principles for keeping systems flexible and maintainable as they grow.",
  },
  {
    title: "Design Patterns",
    author: "Erich Gamma et al.",
    description:
      "Proven structural, creational, and behavioral blueprints for solving common problems.",
  },
];

export default function CatalogPage() {
  return (
    <div>
      <main className="relative bg-background px-6 py-16 text-foreground">
        <div className="mx-auto flex max-w-4xl flex-col gap-10">
          <section className="space-y-6">
            {dummyBooks.map((book) => (
              <BookTile key={`${book.title}-${book.author}`} {...book} />
            ))}
          </section>
        </div>
      </main>
    </div>
  );
}
