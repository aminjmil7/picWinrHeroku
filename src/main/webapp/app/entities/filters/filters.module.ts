import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { filtersComponent } from './filters.component';
import { SharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [filtersComponent],
  imports: [CommonModule, SharedModule, RouterModule],
  exports: [],
  providers: [],
})
export class filtersModule {}
