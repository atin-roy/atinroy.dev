import BookTile from "@/components/BookTile";
import { getBooks } from "@/services/book-services";

export default async function CatalogPage() {
  const books = await getBooks();

  return (
    <div>
      <main className="relative bg-background px-6 py-16 text-foreground">
        <div className="mx-auto flex max-w-4xl flex-col gap-10">
          <section className="space-y-6">
            {books.map((book) => (
              <BookTile key={`${book.title}-${book.author}`} {...book} />
            ))}
          </section>
        </div>
      </main>
    </div>
  );
}
