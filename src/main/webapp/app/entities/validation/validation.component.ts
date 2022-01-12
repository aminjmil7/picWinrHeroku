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
  constructor(public opcodeService: OpcodeService, protected router: Router, public cycleService: CycleService) {}

  ngOnInit(): void {
    if (this.cycleService.currentCycle.opcode) this.OPCode = this.cycleService.currentCycle.opcode.opirationCode!;
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
          console.log(this.cycleService.currentCycle);
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
    if (this.cycleService.currentCycle.post?.comments && this.cycleService.currentCycle.post?.comments?.length > 0) {
      let winners = '';
      let index = 0;
      while (index < this.nbWinners) {
        const winnerIndex = this.getRandomInt(this.cycleService.currentCycle.post?.comments.length);
        if (winners.indexOf(this.cycleService.currentCycle.post?.comments[winnerIndex].ownerName) == -1) {
          winners += '@' + String(this.cycleService.currentCycle.post?.comments[winnerIndex].ownerName);
          index++;
        } else {
          console.log('winner exist');
        }
      }

      this.cycleService.currentCycle.winners = winners;
      this.cycleService.update(this.cycleService.currentCycle).subscribe();
    }
  }

  getRandomInt(max: number) {
    return Math.floor(Math.random() * max);
  }

  previousState(): void {
    window.history.back();
  }
}
