import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from 'app/admin/configuration/configuration.service';
import { InstagramService } from './social-instagram.service';

@Component({
  selector: 'app-social-instagram',
  templateUrl: './social-instagram.component.html',
  styleUrls: ['./social-instagram.scss'],
})
export class SocialInstagramComponent implements OnInit {
  instagramCode: string | undefined;
  instagramClientId: string | undefined;
  instagramClientSecret: string | undefined;
  instagramRedirectUri: string | undefined;
  constructor(private instagramService: InstagramService, private configurationService: ConfigurationService) {}

  ngOnInit(): void {
    console.log(this.instagramCode);
    this.configurationService.getPropertySources().subscribe(propertySources => {
      this.instagramClientId = propertySources[4].properties.instagramClientId.value;
      this.instagramClientSecret = propertySources[4].properties.instagramClientSecret.value;
      this.instagramRedirectUri = propertySources[4].properties.instagramRedirectUri.value;
      console.log(this.instagramClientId, this.instagramClientSecret, this.instagramRedirectUri, this.instagramCode);
      console.log('get code');
      if (this.instagramCode) {
        this.instagramService
          .getAccessToken(this.instagramClientId, this.instagramClientSecret, this.instagramRedirectUri, this.instagramCode)
          .subscribe(res => {
            console.log('insta result');
            console.log(res);
          });
      }
    });
  }
}
