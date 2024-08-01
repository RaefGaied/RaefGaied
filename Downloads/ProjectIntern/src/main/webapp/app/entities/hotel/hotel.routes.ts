import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { HotelComponent } from './list/hotel.component';
import { HotelDetailComponent } from './detail/hotel-detail.component';
import { HotelUpdateComponent } from './update/hotel-update.component';
import HotelResolve from './route/hotel-routing-resolve.service';

const hotelRoute: Routes = [
  {
    path: '',
    component: HotelComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HotelDetailComponent,
    resolve: {
      hotel: HotelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HotelUpdateComponent,
    resolve: {
      hotel: HotelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HotelUpdateComponent,
    resolve: {
      hotel: HotelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default hotelRoute;
