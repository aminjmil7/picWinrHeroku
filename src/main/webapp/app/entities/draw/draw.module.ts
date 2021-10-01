import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { DrawComponent } from './draw.component';

@NgModule({
  declarations: [DrawComponent],
  imports: [CommonModule, SharedModule, RouterModule],
  exports: [],
  providers: [],
})
export class DrawModule {}
