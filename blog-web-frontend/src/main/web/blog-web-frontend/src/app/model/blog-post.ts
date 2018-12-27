export class BlogPost {
  id: number;
  title: string;
  content: string;

  constructor(
    title: string,
    content: string,
    id: number
  ) {
    this.title = title;
    this.content = content;
    this.id = id;
  }
}
