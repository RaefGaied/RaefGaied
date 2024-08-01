import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EmailTemplateConfigurationComponent } from './list/email-template-configuration.component';
import { EmailTemplateConfigurationDetailComponent } from './detail/email-template-configuration-detail.component';
import { EmailTemplateConfigurationUpdateComponent } from './update/email-template-configuration-update.component';
import EmailTemplateConfigurationResolve from './route/email-template-configuration-routing-resolve.service';

const emailTemplateConfigurationRoute: Routes = [
  {
    path: '',
    component: EmailTemplateConfigurationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmailTemplateConfigurationDetailComponent,
    resolve: {
      emailTemplateConfiguration: EmailTemplateConfigurationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmailTemplateConfigurationUpdateComponent,
    resolve: {
      emailTemplateConfiguration: EmailTemplateConfigurationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmailTemplateConfigurationUpdateComponent,
    resolve: {
      emailTemplateConfiguration: EmailTemplateConfigurationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default emailTemplateConfigurationRoute;
