import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WelcomeComponent } from './entities/welcome/welcome.component';
import { filtersComponent } from './entities/filters/filters.component';
import { validationComponent } from './entities/validation/validation.component';
import { DrawComponent } from './entities/draw/draw.component';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'welcome',
          component: WelcomeComponent,
          loadChildren: () => import('./entities/welcome/welcome.module').then(m => m.WelcomeModule),
        },
        {
          path: 'filters',
          component: filtersComponent,
          loadChildren: () => import('./entities/filters/filters.module').then(m => m.filtersModule),
        },
        {
          path: 'validation',
          component: validationComponent,
          loadChildren: () => import('./entities/validation/validation.module').then(m => m.validationModule),
        },
        {
          path: 'draw',
          component: DrawComponent,
          loadChildren: () => import('./entities/draw/draw.module').then(m => m.DrawModule),
        },
        {
          path: 'jhipster/admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'jhipster/account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'jhipster/login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        ...LAYOUT_ROUTES,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
