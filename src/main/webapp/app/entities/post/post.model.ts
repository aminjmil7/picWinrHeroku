import { ICycle } from 'app/entities/cycle/cycle.model';

export interface IPost {
  id?: number;
  link?: string;
  commentCount?: number;
  content?: string;
  cycle?: ICycle | null;
  comments?: Comment[];
  postType?: string;
}

export interface Comment {
  id: string;
  ownerName: string;
  message: string;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public link?: string,
    public commentCount?: number,
    public content?: string,
    public cycle?: ICycle | null,
    public comments?: Comment[],
    public postType?: string
  ) {}
}

export function getPostIdentifier(post: IPost): number | undefined {
  return post.id;
}
