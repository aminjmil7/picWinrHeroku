import { HttpResponse } from '@angular/common/http';
import { Component, TemplateRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPost, Post } from '../post/post.model';
import { PostService } from '../post/service/post.service';
import { faHeart, faComments, faClock } from '@fortawesome/free-regular-svg-icons';
import { Router } from '@angular/router';
import { faLink } from '@fortawesome/free-solid-svg-icons';
import { CycleService } from '../cycle/service/cycle.service';
import { Dayjs } from 'dayjs';
import * as dayjs from 'dayjs';

@Component({
  selector: 'jhi-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss'],
})
export class WelcomeComponent {
  closeResult: string | undefined;
  postList: any[] = [];
  fa_Heart = faHeart;
  fa_Comments = faComments;
  fa_Clock = faClock;
  fa_Link = faLink;
  selectedPost: Post = {};

  constructor(
    private modalService: NgbModal,
    protected postService: PostService,
    protected router: Router,
    protected cycleService: CycleService
  ) {}

  submitPostLink(): any {
    this.cycleService.create({ post: this.selectedPost, runDate: dayjs(), email: '' }).subscribe(res => {
      if (res.body) {
        this.cycleService.currentCycle = res.body;
        this.router.navigate(['/filters']);
      }
    });
  }

  getFacebookPosts(postListContent: any) {
    this.postService.query().subscribe(res => {
      res.body?.map(element => {
        if (element.content) {
          const obj = JSON.parse(element.content);
          this.postList.push({
            id: element.id,
            title: obj.title,
            nbLikes: obj.nbLikes,
            commentCount: obj?.comments?.length,
            date: new Date(obj.date),
            link: element.link,
            content: element.content,
            isSelected: false,
          });
        }
      });
    });

    this.modalService.open(postListContent, { size: 'lg', centered: true });
  }
  postSelected() {
    this.selectedPost = this.postList.find(elem => elem.isSelected == true);
    console.log(this.selectedPost);
    this.modalService.dismissAll();
  }
  choose(event: any, post: any) {
    post.isSelected = true;
  }
}
