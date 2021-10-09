import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOpcode } from '../opcode.model';
import { OpcodeService } from '../service/opcode.service';

@Component({
  templateUrl: './opcode-delete-dialog.component.html',
})
export class OpcodeDeleteDialogComponent {
  opcode?: IOpcode;

  constructor(protected opcodeService: OpcodeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.opcodeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
