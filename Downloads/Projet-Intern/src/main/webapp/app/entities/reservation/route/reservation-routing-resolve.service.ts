import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReservation } from '../reservation.model';
import { ReservationService } from '../service/reservation.service';

const reservationResolve = (route: ActivatedRouteSnapshot): Observable<null | IReservation> => {
  const id = route.params['id'];
  if (id) {
    return inject(ReservationService)
      .find(id)
      .pipe(
        mergeMap((reservation: HttpResponse<IReservation>) => {
          if (reservation.body) {
            return of(reservation.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default reservationResolve;
