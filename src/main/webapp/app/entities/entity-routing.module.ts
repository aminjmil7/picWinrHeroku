import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { WelcomeComponent } from './welcome/welcome.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        pathMatch: 'full',
        redirectTo: '/welcome',
      },
      {
        path: 'jhipster/opcode',
        data: { pageTitle: 'pickwinrApp.opcode.home.title' },
        loadChildren: () => import('./opcode/opcode.module').then(m => m.OpcodeModule),
      },
      {
        path: 'jhipster/post',
        data: { pageTitle: 'pickwinrApp.post.home.title' },
        loadChildren: () => import('./post/post.module').then(m => m.PostModule),
      },
      {
        path: 'jhipster/cycle',
        data: { pageTitle: 'pickwinrApp.cycle.home.title' },
        loadChildren: () => import('./cycle/cycle.module').then(m => m.CycleModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
