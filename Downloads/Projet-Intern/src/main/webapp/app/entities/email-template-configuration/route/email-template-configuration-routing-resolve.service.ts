import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmailTemplateConfiguration } from '../email-template-configuration.model';
import { EmailTemplateConfigurationService } from '../service/email-template-configuration.service';

const emailTemplateConfigurationResolve = (route: ActivatedRouteSnapshot): Observable<null | IEmailTemplateConfiguration> => {
  const id = route.params['id'];
  if (id) {
    return inject(EmailTemplateConfigurationService)
      .find(id)
      .pipe(
        mergeMap((emailTemplateConfiguration: HttpResponse<IEmailTemplateConfiguration>) => {
          if (emailTemplateConfiguration.body) {
            return of(emailTemplateConfiguration.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default emailTemplateConfigurationResolve;
