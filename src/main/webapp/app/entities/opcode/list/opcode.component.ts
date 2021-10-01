import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOpcode } from '../opcode.model';
import { OpcodeService } from '../service/opcode.service';
import { OpcodeDeleteDialogComponent } from '../delete/opcode-delete-dialog.component';

@Component({
  selector: 'jhi-opcode',
  templateUrl: './opcode.component.html',
})
export class OpcodeComponent implements OnInit {
  opcodes?: IOpcode[];
  isLoading = false;

  constructor(protected opcodeService: OpcodeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.opcodeService.query().subscribe(
      (res: HttpResponse<IOpcode[]>) => {
        this.isLoading = false;
        this.opcodes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IOpcode): number {
    return item.id!;
  }

  delete(opcode: IOpcode): void {
    const modalRef = this.modalService.open(OpcodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.opcode = opcode;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
