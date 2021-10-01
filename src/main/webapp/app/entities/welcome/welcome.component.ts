import { HttpResponse } from '@angular/common/http';
import { Component, TemplateRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPost } from '../post/post.model';
import { PostService } from '../post/service/post.service';
import { faHeart, faComments, faClock } from '@fortawesome/free-regular-svg-icons';
import { Router } from '@angular/router';
import { faLink } from '@fortawesome/free-solid-svg-icons';

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
  selectedLink = '';

  constructor(private modalService: NgbModal, protected postService: PostService, protected router: Router) {}

  submitPostLink(): any {
    this.router.navigate(['/filters']);
  }

  getFacebookPosts(postListContent: any) {
    this.postList.push(
      ...[
        {
          name: 'The Filthy Animals',
          nbHearts: Math.floor(Math.random() * 1000),
          nbComments: Math.floor(Math.random() * 1000),
          date: new Date().setHours(5),
          link: 'facebook.com/TunisoulTN/photos/a.1002605789840018/3149235591843683',
          isSelected: false,
        },
        {
          name: 'leading edge',
          nbHearts: Math.floor(Math.random() * 1000),
          nbComments: Math.floor(Math.random() * 1000),
          date: new Date().setMonth(3),
          link: 'facebook.com/TunisoulTN/photos/a.1002605789840018/3053356198098290',
          isSelected: false,
        },
        {
          name: 'magenta mission-critical',
          nbHearts: Math.floor(Math.random() * 1000),
          nbComments: Math.floor(Math.random() * 1000),
          date: new Date().setMonth(5),
          link: 'facebook.com/photo/?fbid=3779073372332631&set=a.1718656361707686',
          isSelected: false,
        },
        {
          name: 'Wedding picture',
          nbHearts: Math.floor(Math.random() * 1000),
          nbComments: Math.floor(Math.random() * 1000),
          date: new Date().setMonth(9),
          link: 'facebook.com/1718650065041649/photos/a.1718656361707686/3779151628991472/',
          isSelected: false,
        },
      ]
    );
    this.modalService.open(postListContent, { size: 'lg', centered: true });
  }
  postSelected() {
    console.log(this.postList);
    // this.selectedLink = this.postList.find(elem => elem.isSelected == true).link;
    this.modalService.dismissAll();
  }
}
