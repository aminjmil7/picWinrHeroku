import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

export type EntityResponseType = HttpResponse<any>;
export type EntityArrayResponseType = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class YoutubeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/youtube');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getVideoDetails(id: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/getVideoDetails/${id}`, { observe: 'response' });
  }
}
