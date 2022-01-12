import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { faClock, faComments, faHeart } from '@fortawesome/free-regular-svg-icons';
import { faLink } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FacebookService } from 'app/home/facebook.service';
import { TwitterService } from 'app/home/twitter.service';
import { YoutubeService } from 'app/home/youtube-service';
import { CycleService } from '../cycle/service/cycle.service';
import { Comment, Post } from '../post/post.model';
import { SocialFacebookComponent } from './social-facebook/social-facebook.component';
import { SocialTwitterComponent } from './social-twitter/social-twitter.component';
import { SocialYoutubeComponent } from './social-youtube/social-youtube.component';
declare const FB: any;

@Component({
  selector: 'jhi-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss'],
})
export class WelcomeComponent implements OnInit {
  closeResult: string | undefined;
  postList: any[] = [];
  fa_Heart = faHeart;
  fa_Comments = faComments;
  fa_Clock = faClock;
  fa_Link = faLink;
  selectedPost: Post = {};
  Pages: any[] = [];
  selectedPage: any;
  profile: any;
  twitterAuth_token: string | undefined;
  twitterAuth_verifier: string | undefined;

  constructor(
    public modalService: NgbModal,
    public router: Router,
    public cycleService: CycleService,
    public facebookService: FacebookService,
    public twitterService: TwitterService,
    public youtubeService: YoutubeService,
    private activeRoute: ActivatedRoute
  ) {
    FB.init({
      appId: '559836958693019',
      cookie: true,
      xfbml: true,
      version: 'v12.0',
    });
  }

  ngOnInit(): void {
    this.activeRoute.queryParams.subscribe(res => {
      this.twitterAuth_token = res.oauth_token;
      this.twitterAuth_verifier = res.oauth_verifier;
      if (this.twitterAuth_verifier) {
        this.postList = [];
        const modalRef = this.modalService.open(SocialTwitterComponent, { size: 'lg', centered: true });
        modalRef.componentInstance.twitterAuth_verifier = this.twitterAuth_verifier;
        modalRef.componentInstance.parentComponent = this;
      }
    });
  }

  signInWithFacebook(): void {
    if (this.facebookService.accessToken) {
      this.initiateFacebook();
    } else {
      FB.login(
        (response: any) => {
          if (response.status === 'connected') {
            this.facebookService.accessToken = response.authResponse.accessToken;
            this.initiateFacebook();
            // Logged into your webpage and Facebook.
          } else {
            // The person is not logged into your webpage or we are unable to tell.
          }
        },
        {
          scope: `public_profile,user_birthday,user_hometown,user_location,user_likes,user_events,user_payment_tokens,user_photos
                  ,user_videos,user_friends,user_posts,user_gender,user_link,user_age_range,email,read_insights
                  ,publish_video,catalog_management,gaming_user_locale,user_managed_groups,groups_show_list,pages_manage_cta
                  ,pages_manage_instant_articles,pages_show_list,read_page_mailboxes,ads_management,ads_read,business_management
                  ,pages_messaging,pages_messaging_phone_number,pages_messaging_subscriptions,instagram_basic
                  ,instagram_manage_comments,instagram_manage_insights,instagram_content_publish,publish_to_groups
                  ,groups_access_member_info,leads_retrieval,whatsapp_business_management,instagram_manage_messages
                  ,attribution_read,page_events,pages_read_engagement,pages_manage_metadata,pages_read_user_content
                  ,pages_manage_ads,pages_manage_posts,pages_manage_engagement`,
        }
      );
    }
  }

  initiateFacebook() {
    this.facebookService.getPages(this.facebookService.accessToken).subscribe(res1 => {
      if (res1.body) {
        this.Pages = res1.body.data;
        this.facebookService.getProfile(this.facebookService.accessToken).subscribe(
          result => {
            this.profile = result.body;
          },
          err => console.error(err),
          () => {
            const modalRef = this.modalService.open(SocialFacebookComponent);
            modalRef.componentInstance.Pages = res1.body.data;
            modalRef.componentInstance.profile = this.profile;
            modalRef.componentInstance.parentComponent = this;
          }
        );
      }
    });
  }

  signOutfromFacebook() {
    this.selectedPost = {};
    this.facebookService.accessToken = null;
    FB.logout((res: any) => {
      // Person is now logged out
      console.log(res);
    });
  }

  twitterLogin() {
    this.twitterService.getTwitterToken().subscribe(res => {
      window.location.href = res.body.twitterAuthKey;
    });
  }

  openYoutubeModal() {
    const refModal = this.modalService.open(SocialYoutubeComponent, { size: 'lg', centered: true });
    refModal.componentInstance.parentComponent = this;
  }

  submitPostLink(): any {
    if (this.selectedPost.link?.indexOf('facebook') != -1) {
      this.loadFacebookPost();
    } else if (this.selectedPost.link?.indexOf('youtube')) {
      this.router.navigate(['/filters']);
    }
  }

  loadFacebookPost() {
    if (this.selectedPost.link) {
      const start = String(this.selectedPost.link).indexOf('com/') + 4;
      const postId = this.selectedPost.link.substr(start, start + 33);
      this.facebookService.getgetPostById(this.facebookService.accessToken, postId).subscribe(res => {
        const commentList: Comment[] = [];
        res.body.data.forEach((comment: any) => {
          commentList.push({
            id: comment.id,
            message: comment.message,
            ownerName: comment.from.name,
          });
        });
        this.selectedPost = {
          comments: commentList,
          commentCount: commentList.length,
          postType: 'facebook',
        };
        this.cycleService.currentCycle = {
          post: this.selectedPost,
        };
        this.router.navigate(['/filters']);
      });
    }
  }

  instagram() {
    window.location.href =
      'https://api.instagram.com/oauth/authorize?client_id=1087161935189461&redirect_uri=https://socialsizzle.heroku.com/auth/&scope=user_profile,user_media&response_type=code';

    // this.facebookService.getInstagram(this.facebookService.accessToken).subscribe(res => {
    //   console.log(res);
    // });
  }
}
