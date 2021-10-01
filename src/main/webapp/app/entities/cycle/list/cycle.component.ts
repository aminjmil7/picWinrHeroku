import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICycle } from '../cycle.model';
import { CycleService } from '../service/cycle.service';
import { CycleDeleteDialogComponent } from '../delete/cycle-delete-dialog.component';

@Component({
  selector: 'jhi-cycle',
  templateUrl: './cycle.component.html',
})
export class CycleComponent implements OnInit {
  cycles?: ICycle[];
  isLoading = false;

  constructor(protected cycleService: CycleService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.cycleService.query().subscribe(
      (res: HttpResponse<ICycle[]>) => {
        this.isLoading = false;
        this.cycles = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICycle): number {
    return item.id!;
  }

  delete(cycle: ICycle): void {
    const modalRef = this.modalService.open(CycleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cycle = cycle;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
