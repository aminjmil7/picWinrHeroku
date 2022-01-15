import { Component, OnInit } from '@angular/core';
import { faClock, faComments, faHeart } from '@fortawesome/free-regular-svg-icons';
import { faLink } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CycleService } from 'app/entities/cycle/service/cycle.service';
import { Post } from 'app/entities/post/post.model';
import { TwitterService } from 'app/home/twitter.service';

@Component({
  selector: 'app-social-twitter',
  templateUrl: './social-twitter.component.html',
  styleUrls: ['./social-twitter.scss'],
})
export class SocialTwitterComponent implements OnInit {
  postList: any[] = [];
  fa_Heart = faHeart;
  fa_Comments = faComments;
  fa_Clock = faClock;
  fa_Link = faLink;
  selectedPost: any = {};
  tweetsData: any;
  twitterAuth_verifier: any;
  parentComponent: any;

  constructor(public modalService: NgbModal, public twitterService: TwitterService, public cycleService: CycleService) {}

  ngOnInit(): void {
    this.postList = [];
    this.twitterService.getMyTweets(this.twitterAuth_verifier).subscribe(tweetsData => {
      console.clear();
      console.log(tweetsData);
      this.twitterService.getProfileInfo(tweetsData.body[0].user.screenName).subscribe(res => {
        console.log(res.body.data[0].id);
        this.twitterService.getTimelineTweets(res.body.data[0].id).subscribe(result => {
          console.log(result);
          result.body.data.forEach((tweet: any) => {
            this.postList.push({
              id: tweet.id,
              title: this.findTweet(tweet.created_at, tweetsData.body)?.user?.screenName,
              nbLikes: tweet.favoriteCount,
              commentCount: 0,
              date: new Date(tweet.created_at),
              link: 'https://twitter.com/aminjmil4/status/' + String(tweet.id),
              picture: this.findTweet(tweet.created_at, tweetsData.body)?.mediaEntities[0]?.mediaURLHttps,
              content: tweet.text,
              isSelected: false,
              conversation_id: tweet?.conversation_id,
            });
          });
        });
      });
    });
  }
  findTweet(created_at: string, tweetsData: any[]): any {
    return tweetsData.find(tweet => tweet.createdAt == created_at);
  }
  selectPost(event: any, post: any) {
    post.isSelected = true;
  }
  postSelected() {
    this.selectedPost = this.postList.find(elem => elem.isSelected == true);
    this.selectedPost.comments = [];

    this.postList.forEach(post => {
      if (this.selectedPost.conversation_id == post.conversation_id) {
        this.selectedPost.comments?.push({
          id: String(post.id),
          ownerName: String(post.title),
          message: String(post.content),
        });
      }
    });
    this.parentComponent.selectedPost = this.selectedPost;
    this.cycleService.currentCycle.post = this.selectedPost;
    this.modalService.dismissAll();
    // this.twitterService.getTweetReplies(String(this.selectedPost.id)).subscribe(replies => {
    //   console.log(replies);
    // });
  }
}
