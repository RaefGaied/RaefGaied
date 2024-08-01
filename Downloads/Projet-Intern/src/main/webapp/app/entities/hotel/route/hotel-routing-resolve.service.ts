import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHotel } from '../hotel.model';
import { HotelService } from '../service/hotel.service';

const hotelResolve = (route: ActivatedRouteSnapshot): Observable<null | IHotel> => {
  const id = route.params['id'];
  if (id) {
    return inject(HotelService)
      .find(id)
      .pipe(
        mergeMap((hotel: HttpResponse<IHotel>) => {
          if (hotel.body) {
            return of(hotel.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default hotelResolve;
