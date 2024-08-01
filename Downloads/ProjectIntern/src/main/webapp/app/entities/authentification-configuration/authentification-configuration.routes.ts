import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AuthentificationConfigurationComponent } from './list/authentification-configuration.component';
import { AuthentificationConfigurationDetailComponent } from './detail/authentification-configuration-detail.component';
import { AuthentificationConfigurationUpdateComponent } from './update/authentification-configuration-update.component';
import AuthentificationConfigurationResolve from './route/authentification-configuration-routing-resolve.service';

const authentificationConfigurationRoute: Routes = [
  {
    path: '',
    component: AuthentificationConfigurationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuthentificationConfigurationDetailComponent,
    resolve: {
      authentificationConfiguration: AuthentificationConfigurationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuthentificationConfigurationUpdateComponent,
    resolve: {
      authentificationConfiguration: AuthentificationConfigurationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuthentificationConfigurationUpdateComponent,
    resolve: {
      authentificationConfiguration: AuthentificationConfigurationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default authentificationConfigurationRoute;
