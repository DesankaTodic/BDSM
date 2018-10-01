export class Book {
  id: number;
  title: string;
  author: string | null;
  keywords: string | null;
  publicationYear: string | null;
  filename: string;
  mime: string | null;
  categoryId: number | null;
  languageId: number | null;
}