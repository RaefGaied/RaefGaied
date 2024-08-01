import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ReservationComponent } from './list/reservation.component';
import { ReservationDetailComponent } from './detail/reservation-detail.component';
import { ReservationUpdateComponent } from './update/reservation-update.component';
import ReservationResolve from './route/reservation-routing-resolve.service';

const reservationRoute: Routes = [
  {
    path: '',
    component: ReservationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReservationDetailComponent,
    resolve: {
      reservation: ReservationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReservationUpdateComponent,
    resolve: {
      reservation: ReservationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReservationUpdateComponent,
    resolve: {
      reservation: ReservationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default reservationRoute;
