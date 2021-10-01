import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { validationComponent } from '../validation/validation.component';
import { FilterService } from './filters.service';

@Component({
  selector: 'jhi-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss'],
})
export class filtersComponent implements OnInit {
  filters: { filterName: string; selected: boolean }[] = [];
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

  constructor(protected filterService: FilterService, protected ngbmOdel: NgbModal, protected router: Router) {}

  ngOnInit(): void {
    this.filterService.getAllfilters().subscribe(data => {
      data.map(element => this.filters.push({ filterName: element, selected: false }));
    });
  }
  submitFilters() {
    console.log(this.filters);

    this.router.navigate(['/validation']);
  }
  navigate(steps: { title: string; description: string; path: string }[], i: number) {
    if (i <= 1) this.router.navigate([steps[i].path]);
  }
}
