import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUIConfiguration } from '../ui-configuration.model';
import { UIConfigurationService } from '../service/ui-configuration.service';

const uIConfigurationResolve = (route: ActivatedRouteSnapshot): Observable<null | IUIConfiguration> => {
  const id = route.params['id'];
  if (id) {
    return inject(UIConfigurationService)
      .find(id)
      .pipe(
        mergeMap((uIConfiguration: HttpResponse<IUIConfiguration>) => {
          if (uIConfiguration.body) {
            return of(uIConfiguration.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default uIConfigurationResolve;
