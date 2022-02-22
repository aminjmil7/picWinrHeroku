import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as dayjs from 'dayjs';
import { CycleService } from '../cycle/service/cycle.service';
import { OpcodeService } from '../opcode/service/opcode.service';
import { Comment } from '../post/post.model';
import { ValidationService } from './validation.service';

@Component({
  selector: 'jhi-validation',
  templateUrl: './validation.component.html',
  styleUrls: ['./validation.component.scss'],
})
export class validationComponent implements OnInit {
  OPCode = '';
  nbWinners = 0;
  steps: { title: string; description: string; path: string }[] = [
    {
      title: 'Step 1',
      description: 'Select post',
      path: '/welcome',
    },
    {
      title: 'Step 2',
      description: 'Filtering options',
      path: '/filters',
    },
    {
      title: 'Step 3',
      description: 'Validation',
      path: '/validation',
    },
    {
      title: 'Step 4',
      description: 'Draw result',
      path: '/draw',
    },
  ];
  updated = true;
  codeError = '';
  maxWinners = 0;
  constructor(public opcodeService: OpcodeService, protected router: Router, public cycleService: CycleService) {}

  ngOnInit(): void {
    if (this.cycleService.currentCycle.opcode) this.OPCode = this.cycleService.currentCycle.opcode.opirationCode!;
    this.maxWinners = this.getMaxPossibleWinners();
  }

  getWinners() {
    this.opcodeService
      .query({
        'opirationCode.equals': this.OPCode,
      })
      .subscribe(res => {
        if (res.body && res.body.length > 0) {
          const OpCode = res.body[0];
          if (OpCode.count == 0) {
            this.codeError = 'Number of uses is done';
            this.updated = false;
            return;
          }
          const now = dayjs();
          if (OpCode.expirationDate && OpCode.expirationDate < now) {
            this.codeError = 'Code Date expired';
            this.updated = false;
            return;
          }
          if (OpCode.count) OpCode.count--;
          this.cycleService.currentCycle.opcode = OpCode;
          this.selectWinners();

          this.opcodeService.update(OpCode).subscribe(abc => {
            this.router.navigate(['/draw']);
          });
        } else {
          this.codeError = "Opiration code doesn't exist";
          this.updated = false;
        }
      });
  }
  navigate(steps: { title: string; description: string; path: string }[], i: number) {
    if (i <= 2) this.router.navigate([steps[i].path]);
  }

  updateOPCode() {
    this.updated = true;
  }

  selectWinners() {
    const commentList = this.cycleService.currentCycle.post?.comments;
    this.maxWinners = this.getMaxPossibleWinners();
    if (commentList && commentList?.length > 0) {
      let winners = '';
      let index = 0;
      while (index < this.nbWinners) {
        const winnerIndex = this.getRandomInt(commentList.length);
        if (winners.indexOf(commentList[winnerIndex].ownerName) == -1) {
          winners += '@' + String(commentList[winnerIndex].ownerName);
          index++;
        } else {
          const otherWinners = commentList.filter(comment => comment.ownerName != commentList[winnerIndex].ownerName);
          if (otherWinners.length === 0) {
            index = this.nbWinners + 1;
          }
        }
      }

      this.cycleService.currentCycle.winners = winners;
      this.cycleService.update(this.cycleService.currentCycle).subscribe();
    }
  }

  getMaxPossibleWinners(): number {
    const CommentList = this.cycleService.currentCycle.post?.comments;
    const usersList: string[] = [];
    if (CommentList) {
      CommentList.forEach(comment => {
        if (comment.ownerName && !usersList.includes(comment.ownerName)) {
          usersList.push(comment.ownerName);
        }
      });
    }
    return usersList.length;
  }

  getRandomInt(max: number) {
    return Math.floor(Math.random() * max);
  }

  previousState(): void {
    window.history.back();
  }
}
