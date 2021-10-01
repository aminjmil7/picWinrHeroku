import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { OpcodeComponent } from './list/opcode.component';
import { OpcodeDetailComponent } from './detail/opcode-detail.component';
import { OpcodeUpdateComponent } from './update/opcode-update.component';
import { OpcodeDeleteDialogComponent } from './delete/opcode-delete-dialog.component';
import { OpcodeRoutingModule } from './route/opcode-routing.module';

@NgModule({
  imports: [SharedModule, OpcodeRoutingModule],
  declarations: [OpcodeComponent, OpcodeDetailComponent, OpcodeUpdateComponent, OpcodeDeleteDialogComponent],
  entryComponents: [OpcodeDeleteDialogComponent],
})
export class OpcodeModule {}
