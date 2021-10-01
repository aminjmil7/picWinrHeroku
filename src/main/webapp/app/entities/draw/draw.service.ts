import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class DrawService {
  getDrawResults(): Observable<string[]> {
    return of(['Amin_Jmil', 'yousef_saoud', 'hamza_mansouri', 'majid_tchala']);
  }
}
