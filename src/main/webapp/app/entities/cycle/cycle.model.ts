import * as dayjs from 'dayjs';
import { IPost } from 'app/entities/post/post.model';
import { IOpcode } from 'app/entities/opcode/opcode.model';

export interface ICycle {
  id?: number;
  runDate?: dayjs.Dayjs;
  email?: string;
  winners?: string | null;
  alternatives?: string | null;
  post?: IPost | null;
  opcode?: IOpcode | null;
}

export class Cycle implements ICycle {
  constructor(
    public id?: number,
    public runDate?: dayjs.Dayjs,
    public email?: string,
    public winners?: string | null,
    public alternatives?: string | null,
    public post?: IPost | null,
    public opcode?: IOpcode | null
  ) {}
}

export function getCycleIdentifier(cycle: ICycle): number | undefined {
  return cycle.id;
}
