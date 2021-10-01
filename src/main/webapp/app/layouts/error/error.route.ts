import { Routes } from '@angular/router';

import { ErrorComponent } from './error.component';

export const errorRoute: Routes = [
  {
    path: 'jhipster/error',
    component: ErrorComponent,
    data: {
      pageTitle: 'error.title',
    },
  },
  {
    path: 'jhipster/accessdenied',
    component: ErrorComponent,
    data: {
      pageTitle: 'error.title',
      errorMessage: 'error.http.403',
    },
  },
  {
    path: 'jhipster/404',
    component: ErrorComponent,
    data: {
      pageTitle: 'error.title',
      errorMessage: 'error.http.404',
    },
  },
  {
    path: 'jhipster/**',
    redirectTo: '/404',
  },
];
