import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ServiceComponent } from './list/service.component';
import { ServiceDetailComponent } from './detail/service-detail.component';
import { ServiceUpdateComponent } from './update/service-update.component';
import ServiceResolve from './route/service-routing-resolve.service';

const serviceRoute: Routes = [
  {
    path: '',
    component: ServiceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceDetailComponent,
    resolve: {
      service: ServiceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceUpdateComponent,
    resolve: {
      service: ServiceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceUpdateComponent,
    resolve: {
      service: ServiceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default serviceRoute;
