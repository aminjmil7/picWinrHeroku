import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WelcomeComponent } from './welcome.component';
import { SharedModule } from 'app/shared/shared.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RouterModule } from '@angular/router';
import { SocialFacebookComponent } from './social-facebook/social-facebook.component';
import { SocialYoutubeComponent } from './social-youtube/social-youtube.component';
import { SocialTwitterComponent } from './social-twitter/social-twitter.component';
import { SocialInstagramComponent } from './social-instagram/social-instagram.component';

@NgModule({
  declarations: [WelcomeComponent, SocialFacebookComponent, SocialYoutubeComponent, SocialTwitterComponent, SocialInstagramComponent],
  imports: [CommonModule, SharedModule, FontAwesomeModule, RouterModule],
  exports: [],
  providers: [],
})
export class WelcomeModule {}
