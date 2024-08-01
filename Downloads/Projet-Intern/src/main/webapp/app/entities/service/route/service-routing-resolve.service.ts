import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IService } from '../service.model';
import { ServiceService } from '../service/service.service';

const serviceResolve = (route: ActivatedRouteSnapshot): Observable<null | IService> => {
  const id = route.params['id'];
  if (id) {
    return inject(ServiceService)
      .find(id)
      .pipe(
        mergeMap((service: HttpResponse<IService>) => {
          if (service.body) {
            return of(service.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default serviceResolve;
