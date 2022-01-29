import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class InstagramService {
  constructor(protected http: HttpClient) {}

  getAccessToken(client_id: string, client_secret: string, redirect_uri: string, code: string): Observable<any> {
    return this.http.post<any>('https://api.instagram.com/oauth/access_token', {
      client_id: client_id,
      client_secret: 'ea04816ebaa1690167fdb3d9c39580c5',
      code: code,
      grant_type: 'authorization_code',
      redirect_uri: 'redirect_uri',
    });
  }
}
