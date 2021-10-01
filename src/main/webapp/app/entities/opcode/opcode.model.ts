import * as dayjs from 'dayjs';
import { ICycle } from 'app/entities/cycle/cycle.model';
import { IUser } from 'app/entities/user/user.model';

export interface IOpcode {
  id?: number;
  count?: number;
  ceationDated?: dayjs.Dayjs;
  expirationDate?: dayjs.Dayjs | null;
  cycles?: ICycle[] | null;
  user?: IUser | null;
}

export class Opcode implements IOpcode {
  constructor(
    public id?: number,
    public count?: number,
    public ceationDated?: dayjs.Dayjs,
    public expirationDate?: dayjs.Dayjs | null,
    public cycles?: ICycle[] | null,
    public user?: IUser | null
  ) {}
}

export function getOpcodeIdentifier(opcode: IOpcode): number | undefined {
  return opcode.id;
}
