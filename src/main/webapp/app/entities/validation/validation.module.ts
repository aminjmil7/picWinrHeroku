import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'app/shared/shared.module';
import { validationComponent } from './validation.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [validationComponent],
  imports: [CommonModule, SharedModule, RouterModule],
  exports: [],
  providers: [],
})
export class validationModule {}
