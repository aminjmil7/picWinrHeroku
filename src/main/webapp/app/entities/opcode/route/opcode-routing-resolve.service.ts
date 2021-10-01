import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOpcode, Opcode } from '../opcode.model';
import { OpcodeService } from '../service/opcode.service';

@Injectable({ providedIn: 'root' })
export class OpcodeRoutingResolveService implements Resolve<IOpcode> {
  constructor(protected service: OpcodeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOpcode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((opcode: HttpResponse<Opcode>) => {
          if (opcode.body) {
            return of(opcode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Opcode());
  }
}
