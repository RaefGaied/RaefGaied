import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PartenaireComponent } from './list/partenaire.component';
import { PartenaireDetailComponent } from './detail/partenaire-detail.component';
import { PartenaireUpdateComponent } from './update/partenaire-update.component';
import PartenaireResolve from './route/partenaire-routing-resolve.service';

const partenaireRoute: Routes = [
  {
    path: '',
    component: PartenaireComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartenaireDetailComponent,
    resolve: {
      partenaire: PartenaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartenaireUpdateComponent,
    resolve: {
      partenaire: PartenaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartenaireUpdateComponent,
    resolve: {
      partenaire: PartenaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default partenaireRoute;
