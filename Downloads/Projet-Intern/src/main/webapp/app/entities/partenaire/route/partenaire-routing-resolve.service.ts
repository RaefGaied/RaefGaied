import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartenaire } from '../partenaire.model';
import { PartenaireService } from '../service/partenaire.service';

const partenaireResolve = (route: ActivatedRouteSnapshot): Observable<null | IPartenaire> => {
  const id = route.params['id'];
  if (id) {
    return inject(PartenaireService)
      .find(id)
      .pipe(
        mergeMap((partenaire: HttpResponse<IPartenaire>) => {
          if (partenaire.body) {
            return of(partenaire.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default partenaireResolve;
