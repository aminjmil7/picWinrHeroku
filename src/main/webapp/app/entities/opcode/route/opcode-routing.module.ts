import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OpcodeComponent } from '../list/opcode.component';
import { OpcodeDetailComponent } from '../detail/opcode-detail.component';
import { OpcodeUpdateComponent } from '../update/opcode-update.component';
import { OpcodeRoutingResolveService } from './opcode-routing-resolve.service';

const opcodeRoute: Routes = [
  {
    path: '',
    component: OpcodeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OpcodeDetailComponent,
    resolve: {
      opcode: OpcodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OpcodeUpdateComponent,
    resolve: {
      opcode: OpcodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OpcodeUpdateComponent,
    resolve: {
      opcode: OpcodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(opcodeRoute)],
  exports: [RouterModule],
})
export class OpcodeRoutingModule {}
