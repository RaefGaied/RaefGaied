import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { HotelAdministrateurComponent } from './list/hotel-administrateur.component';
import { HotelAdministrateurDetailComponent } from './detail/hotel-administrateur-detail.component';
import { HotelAdministrateurUpdateComponent } from './update/hotel-administrateur-update.component';
import HotelAdministrateurResolve from './route/hotel-administrateur-routing-resolve.service';

const hotelAdministrateurRoute: Routes = [
  {
    path: '',
    component: HotelAdministrateurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HotelAdministrateurDetailComponent,
    resolve: {
      hotelAdministrateur: HotelAdministrateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HotelAdministrateurUpdateComponent,
    resolve: {
      hotelAdministrateur: HotelAdministrateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HotelAdministrateurUpdateComponent,
    resolve: {
      hotelAdministrateur: HotelAdministrateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default hotelAdministrateurRoute;
