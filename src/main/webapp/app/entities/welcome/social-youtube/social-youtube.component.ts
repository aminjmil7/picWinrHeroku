import { Component, OnInit } from '@angular/core';
import { faClock, faComments, faHeart } from '@fortawesome/free-regular-svg-icons';
import { faLink } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CycleService } from 'app/entities/cycle/service/cycle.service';
import { Post } from 'app/entities/post/post.model';
import { YoutubeService } from 'app/home/youtube-service';

@Component({
  selector: 'app-social-youtube',
  templateUrl: './social-youtube.component.html',
  styleUrls: ['./social-youtube.scss'],
})
export class SocialYoutubeComponent implements OnInit {
  closeResult: string | undefined;
  postList: any[] = [];
  fa_Heart = faHeart;
  fa_Comments = faComments;
  fa_Clock = faClock;
  fa_Link = faLink;
  selectedPost: Post = {};
  Pages: any[] = [];
  accessToken = '';
  selectedPage: any;
  profile: any;
  twitterAuth_token: string | undefined;
  twitterAuth_verifier: string | undefined;
  parentComponent: any;
  constructor(public modalService: NgbModal, protected youtubeService: YoutubeService, public cycleService: CycleService) {}

  ngOnInit(): void {}

  youtubeGetVideoDetails() {
    this.modalService.dismissAll();
    this.parentComponent.selectedPost = this.selectedPost;
    const from = Number(this.selectedPost?.link?.indexOf('=')) + 1;
    const to = Number(this.selectedPost.link?.indexOf('&'));
    const postId = this.selectedPost.link?.substring(from, to);

    if (postId) {
      this.youtubeService.getVideoDetails(postId).subscribe(res => {
        this.selectedPost.content = '';
        this.selectedPost.comments = [];
        res.body.items.map((element: any) => {
          this.selectedPost.comments?.push({
            id: String(element.id),
            ownerName: String(element.snippet.topLevelComment.snippet.authorDisplayName),
            message: String(element.snippet.topLevelComment.snippet.textDisplay),
          });
          this.selectedPost.content +=
            String(element.snippet.topLevelComment.snippet.authorDisplayName) +
            ',' +
            String(element.snippet.topLevelComment.snippet.textDisplay);
        });
        this.selectedPost.id = this.selectedPost.content.length;
        this.selectedPost.commentCount = res.body.items.length;
        this.cycleService.currentCycle.post = this.selectedPost;
      });
    }
  }
}
