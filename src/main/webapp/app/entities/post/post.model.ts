import { ICycle } from 'app/entities/cycle/cycle.model';

export interface IPost {
  id?: number;
  link?: string;
  commentCount?: number;
  content?: string;
  cycle?: ICycle | null;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public link?: string,
    public commentCount?: number,
    public content?: string,
    public cycle?: ICycle | null
  ) {}
}

export function getPostIdentifier(post: IPost): number | undefined {
  return post.id;
}
