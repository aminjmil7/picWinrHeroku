import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

export type EntityResponseType = HttpResponse<any>;
export type EntityArrayResponseType = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class FacebookService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fb');
  public accessToken: any;

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getClientId(): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/facebookClientId`, { observe: 'response' });
  }
  getProfile(accessToken: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/${accessToken}/profile`, { observe: 'response' });
  }
  getUserPosts(accessToken: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/${accessToken}/myPosts`, { observe: 'response' });
  }
  getPagePosts(accessToken: string, pageId: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/${accessToken}/pagePosts/${pageId}`, { observe: 'response' });
  }
  getgetPostById(accessToken: string, postId: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/${accessToken}/getPostById/${postId}`, { observe: 'response' });
  }
  getPages(accessToken: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/${accessToken}/pages`, { observe: 'response' });
  }
  getInstagram(accessToken: string): Observable<EntityResponseType> {
    return this.http.get<any>(
      'https://graph.facebook.com/v12.0/17895695668004550?fields=id,media_type,media_url,owner,timestamp&access_token='+accessToken,
      { observe: 'response' }
    );
  }
}
