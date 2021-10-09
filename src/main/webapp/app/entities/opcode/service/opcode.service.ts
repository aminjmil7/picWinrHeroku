import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOpcode, getOpcodeIdentifier } from '../opcode.model';

export type EntityResponseType = HttpResponse<IOpcode>;
export type EntityArrayResponseType = HttpResponse<IOpcode[]>;

@Injectable({ providedIn: 'root' })
export class OpcodeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/opcodes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(opcode: IOpcode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(opcode);
    return this.http
      .post<IOpcode>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(opcode: IOpcode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(opcode);
    return this.http
      .put<IOpcode>(`${this.resourceUrl}/${getOpcodeIdentifier(opcode) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(opcode: IOpcode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(opcode);
    return this.http
      .patch<IOpcode>(`${this.resourceUrl}/${getOpcodeIdentifier(opcode) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOpcode>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOpcode[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOpcodeToCollectionIfMissing(opcodeCollection: IOpcode[], ...opcodesToCheck: (IOpcode | null | undefined)[]): IOpcode[] {
    const opcodes: IOpcode[] = opcodesToCheck.filter(isPresent);
    if (opcodes.length > 0) {
      const opcodeCollectionIdentifiers = opcodeCollection.map(opcodeItem => getOpcodeIdentifier(opcodeItem)!);
      const opcodesToAdd = opcodes.filter(opcodeItem => {
        const opcodeIdentifier = getOpcodeIdentifier(opcodeItem);
        if (opcodeIdentifier == null || opcodeCollectionIdentifiers.includes(opcodeIdentifier)) {
          return false;
        }
        opcodeCollectionIdentifiers.push(opcodeIdentifier);
        return true;
      });
      return [...opcodesToAdd, ...opcodeCollection];
    }
    return opcodeCollection;
  }

  protected convertDateFromClient(opcode: IOpcode): IOpcode {
    return Object.assign({}, opcode, {
      ceationDated: opcode.ceationDated?.isValid() ? opcode.ceationDated.toJSON() : undefined,
      expirationDate: opcode.expirationDate?.isValid() ? opcode.expirationDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ceationDated = res.body.ceationDated ? dayjs(res.body.ceationDated) : undefined;
      res.body.expirationDate = res.body.expirationDate ? dayjs(res.body.expirationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((opcode: IOpcode) => {
        opcode.ceationDated = opcode.ceationDated ? dayjs(opcode.ceationDated) : undefined;
        opcode.expirationDate = opcode.expirationDate ? dayjs(opcode.expirationDate) : undefined;
      });
    }
    return res;
  }
}
