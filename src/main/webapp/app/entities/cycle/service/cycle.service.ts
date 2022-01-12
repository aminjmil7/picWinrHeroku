import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICycle, getCycleIdentifier } from '../cycle.model';

export type EntityResponseType = HttpResponse<ICycle>;
export type EntityArrayResponseType = HttpResponse<ICycle[]>;

@Injectable({ providedIn: 'root' })
export class CycleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cycles');
  public currentCycle: ICycle = {};

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cycle: ICycle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cycle);
    return this.http
      .post<ICycle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cycle: ICycle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cycle);
    return this.http
      .put<ICycle>(`${this.resourceUrl}/${getCycleIdentifier(cycle) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(cycle: ICycle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cycle);
    return this.http
      .patch<ICycle>(`${this.resourceUrl}/${getCycleIdentifier(cycle) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICycle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICycle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCycleToCollectionIfMissing(cycleCollection: ICycle[], ...cyclesToCheck: (ICycle | null | undefined)[]): ICycle[] {
    const cycles: ICycle[] = cyclesToCheck.filter(isPresent);
    if (cycles.length > 0) {
      const cycleCollectionIdentifiers = cycleCollection.map(cycleItem => getCycleIdentifier(cycleItem)!);
      const cyclesToAdd = cycles.filter(cycleItem => {
        const cycleIdentifier = getCycleIdentifier(cycleItem);
        if (cycleIdentifier == null || cycleCollectionIdentifiers.includes(cycleIdentifier)) {
          return false;
        }
        cycleCollectionIdentifiers.push(cycleIdentifier);
        return true;
      });
      return [...cyclesToAdd, ...cycleCollection];
    }
    return cycleCollection;
  }

  protected convertDateFromClient(cycle: ICycle): ICycle {
    return Object.assign({}, cycle, {
      runDate: cycle.runDate?.isValid() ? cycle.runDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.runDate = res.body.runDate ? dayjs(res.body.runDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cycle: ICycle) => {
        cycle.runDate = cycle.runDate ? dayjs(cycle.runDate) : undefined;
      });
    }
    return res;
  }
}
