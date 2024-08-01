import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { UIConfigurationComponent } from './list/ui-configuration.component';
import { UIConfigurationDetailComponent } from './detail/ui-configuration-detail.component';
import { UIConfigurationUpdateComponent } from './update/ui-configuration-update.component';
import UIConfigurationResolve from './route/ui-configuration-routing-resolve.service';

const uIConfigurationRoute: Routes = [
  {
    path: '',
    component: UIConfigurationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UIConfigurationDetailComponent,
    resolve: {
      uIConfiguration: UIConfigurationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UIConfigurationUpdateComponent,
    resolve: {
      uIConfiguration: UIConfigurationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UIConfigurationUpdateComponent,
    resolve: {
      uIConfiguration: UIConfigurationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default uIConfigurationRoute;
