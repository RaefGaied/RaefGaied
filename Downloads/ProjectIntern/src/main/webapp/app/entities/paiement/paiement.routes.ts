import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PaiementComponent } from './list/paiement.component';
import { PaiementDetailComponent } from './detail/paiement-detail.component';
import { PaiementUpdateComponent } from './update/paiement-update.component';
import PaiementResolve from './route/paiement-routing-resolve.service';

const paiementRoute: Routes = [
  {
    path: '',
    component: PaiementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaiementDetailComponent,
    resolve: {
      paiement: PaiementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaiementUpdateComponent,
    resolve: {
      paiement: PaiementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaiementUpdateComponent,
    resolve: {
      paiement: PaiementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default paiementRoute;
