import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHotelAdministrateur } from '../hotel-administrateur.model';
import { HotelAdministrateurService } from '../service/hotel-administrateur.service';

const hotelAdministrateurResolve = (route: ActivatedRouteSnapshot): Observable<null | IHotelAdministrateur> => {
  const id = route.params['id'];
  if (id) {
    return inject(HotelAdministrateurService)
      .find(id)
      .pipe(
        mergeMap((hotelAdministrateur: HttpResponse<IHotelAdministrateur>) => {
          if (hotelAdministrateur.body) {
            return of(hotelAdministrateur.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default hotelAdministrateurResolve;
