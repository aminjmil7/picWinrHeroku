import { Route } from '@angular/router';

import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
  path: 'jhipster',
  component: HomeComponent,
  data: {
    pageTitle: 'home.title',
  },
};
