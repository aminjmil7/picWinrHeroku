import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DrawService } from './draw.service';
import { faTrophy } from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'jhi-draw',
  templateUrl: './draw.component.html',
  styleUrls: ['./draw.component.scss'],
})
export class DrawComponent implements OnInit {
  drawResults: { winnerName: string; Position: number }[] = [];
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
  faTrophy = faTrophy;

  constructor(protected drawService: DrawService, protected ngbmOdel: NgbModal, protected router: Router) {}

  ngOnInit(): void {
    this.drawService.getDrawResults().subscribe(data => {
      data.map(element => this.drawResults.push({ winnerName: element, Position: this.drawResults.length + 1 }));
    });
  }

  navigate(steps: { title: string; description: string; path: string }[], i: number) {
    if (i <= 3) this.router.navigate([steps[i].path]);
  }
}
