export type Book = {
  id: number;
  title: string;
  author: string;
  description: string;
  rating: number;
};

export async function getBooks(): Promise<Book[]> {
  const username = "dev";
  const password = "dev123";

  console.log(username, password);

  const authHeader = `Basic ${Buffer.from(`${username}:${password}`).toString("base64")}`;

  const response = await fetch("http://localhost:8080/api/books", {
    headers: {
      Authorization: authHeader,
    },
    cache: "no-store",
  });
  console.log(response);
  if (!response.ok) {
    throw new Error("Failed to fetch books");
  }
  return response.json();
}
