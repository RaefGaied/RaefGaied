import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaiement } from '../paiement.model';
import { PaiementService } from '../service/paiement.service';

const paiementResolve = (route: ActivatedRouteSnapshot): Observable<null | IPaiement> => {
  const id = route.params['id'];
  if (id) {
    return inject(PaiementService)
      .find(id)
      .pipe(
        mergeMap((paiement: HttpResponse<IPaiement>) => {
          if (paiement.body) {
            return of(paiement.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default paiementResolve;
