import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class FilterService {
  getAllfilters(): Observable<string[]> {
    return of([
      'Include comment replies',
      'Add extra entries',
      'User should have liked the post',
      'Exclude / blacklist users',
      'Filter comments based on a specific text',
      'Filter duplicate users',
      'Minimum amount of friends tagged in one comment',
    ]);
  }
}
