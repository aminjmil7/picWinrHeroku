import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

export type EntityResponseType = HttpResponse<any>;
export type EntityArrayResponseType = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class TwitterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/twitter');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getTwitterToken(): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/getToken`, { observe: 'response' });
  }
  getMyTweets(oauthVerifier: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/getUserTimeline/${oauthVerifier}`, { observe: 'response' });
  }
  getProfileInfo(usernames: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/v2/getProfileInfo/${usernames}`, { observe: 'response' });
  }
  getTimelineTweets(userId: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/v2/getTimelineTweets/${userId}`, { observe: 'response' });
  }
  getTweetReplies(conversationId: string): Observable<EntityResponseType> {
    return this.http.get<any>(`${this.resourceUrl}/v2/getTweetReplies/${conversationId}`, { observe: 'response' });
  }  
}
