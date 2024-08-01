import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuthentificationConfiguration } from '../authentification-configuration.model';
import { AuthentificationConfigurationService } from '../service/authentification-configuration.service';

const authentificationConfigurationResolve = (route: ActivatedRouteSnapshot): Observable<null | IAuthentificationConfiguration> => {
  const id = route.params['id'];
  if (id) {
    return inject(AuthentificationConfigurationService)
      .find(id)
      .pipe(
        mergeMap((authentificationConfiguration: HttpResponse<IAuthentificationConfiguration>) => {
          if (authentificationConfiguration.body) {
            return of(authentificationConfiguration.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default authentificationConfigurationResolve;
