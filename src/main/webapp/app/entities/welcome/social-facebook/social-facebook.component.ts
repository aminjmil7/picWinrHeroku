import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CycleService } from 'app/entities/cycle/service/cycle.service';
import { PostService } from 'app/entities/post/service/post.service';
import { FacebookService } from 'app/home/facebook.service';
import { TwitterService } from 'app/home/twitter.service';
import { YoutubeService } from 'app/home/youtube-service';
import { faHeart, faComments, faClock } from '@fortawesome/free-regular-svg-icons';
import { faLink } from '@fortawesome/free-solid-svg-icons';
import { Post } from 'app/entities/post/post.model';

@Component({
  selector: 'app-social-facebook',
  templateUrl: './social-facebook.component.html',
  styleUrls: ['./social-facebook.scss'],
})
export class SocialFacebookComponent implements OnInit {
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

  parentComponent: any;

  constructor(public modalService: NgbModal, protected facebookService: FacebookService) {}

  ngOnInit(): void {}

  selectPage(event: any, page: any) {
    this.selectedPage = page;
  }

  pageSelected(postListContent: any) {
    if (this.selectedPage.id) {
      this.facebookService.getPagePosts(this.facebookService.accessToken, this.selectedPage.id).subscribe(res => {
        this.postList = [];
        this.modalService.dismissAll();
        res.body?.data?.map((element: any, i: number) => {
          const index = String(element.id).lastIndexOf('_');
          const ID = +String(element.id).substring(index);

          if (i < 5)
            this.postList.push({
              id: ID,
              title: element.message,
              nbLikes: element.likesCount,
              commentCount: element.commentsCount,
              date: new Date(element.createdTime),
              link: 'https://www.facebook.com/' + String(element.id),
              picture: element.fullPicture,
              content: JSON.stringify(element.comments.data),
              isSelected: false,
            });
        });
        this.modalService.open(postListContent, { size: 'lg', centered: true });
      });
    } else {
      this.facebookService.getUserPosts(this.facebookService.accessToken).subscribe(res => {
        this.postList = [];
        this.modalService.dismissAll();

        res.body?.data?.map((element: any, i: number) => {
          const index = String(element.id).lastIndexOf('_');
          const ID = +String(element.ID).substring(index);

          if (i < 5)
            this.postList.push({
              id: ID,
              title: element.message,
              nbLikes: element.reactionsCount,
              commentCount: element.commentsCount,
              date: new Date(element.createdTime),
              link: 'https://www.facebook.com/' + String(element.id),
              picture: element.fullPicture,
              content: JSON.stringify(element.comments.data),
              isSelected: false,
            });
        });
        this.modalService.open(postListContent, { size: 'lg', centered: true });
      });
    }
  }

  selectPost(event: any, post: any) {
    post.isSelected = true;
  }

  postSelected() {
    this.selectedPost = this.postList.find(elem => elem.isSelected == true);
    this.parentComponent.selectedPost = this.selectedPost;
    this.modalService.dismissAll();
  }
}
