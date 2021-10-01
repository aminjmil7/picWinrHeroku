import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OpcodeService } from '../opcode/service/opcode.service';
import { ValidationService } from './validation.service';

@Component({
  selector: 'jhi-validation',
  templateUrl: './validation.component.html',
  styleUrls: ['./validation.component.scss'],
})
export class validationComponent implements OnInit {
  selectedOPCode = '';
  nbWinners: number | undefined;
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
  constructor(public opcodeService: OpcodeService, protected router: Router) {}

  ngOnInit(): void {
    this.opcodeService.query().subscribe(res => {
      console.log(res.body);
    });
    // this.filterService.getAllfilters().subscribe(data => {
    //   data.map(element => this.filters.push({ filterName: element, selected: false }));
    // });
  }
  getWinners() {
    this.router.navigate(['/draw']);
  }
  navigate(steps: { title: string; description: string; path: string }[], i: number) {
    if (i <= 2) this.router.navigate([steps[i].path]);
  }
}
