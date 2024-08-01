import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmailTemplateConfiguration, NewEmailTemplateConfiguration } from '../email-template-configuration.model';

export type PartialUpdateEmailTemplateConfiguration = Partial<IEmailTemplateConfiguration> & Pick<IEmailTemplateConfiguration, 'id'>;

type RestOf<T extends IEmailTemplateConfiguration | NewEmailTemplateConfiguration> = Omit<T, 'dateCreation' | 'dateModify'> & {
  dateCreation?: string | null;
  dateModify?: string | null;
};

export type RestEmailTemplateConfiguration = RestOf<IEmailTemplateConfiguration>;

export type NewRestEmailTemplateConfiguration = RestOf<NewEmailTemplateConfiguration>;

export type PartialUpdateRestEmailTemplateConfiguration = RestOf<PartialUpdateEmailTemplateConfiguration>;

export type EntityResponseType = HttpResponse<IEmailTemplateConfiguration>;
export type EntityArrayResponseType = HttpResponse<IEmailTemplateConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class EmailTemplateConfigurationService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/email-template-configurations');

  create(emailTemplateConfiguration: NewEmailTemplateConfiguration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emailTemplateConfiguration);
    return this.http
      .post<RestEmailTemplateConfiguration>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(emailTemplateConfiguration: IEmailTemplateConfiguration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emailTemplateConfiguration);
    return this.http
      .put<RestEmailTemplateConfiguration>(
        `${this.resourceUrl}/${this.getEmailTemplateConfigurationIdentifier(emailTemplateConfiguration)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(emailTemplateConfiguration: PartialUpdateEmailTemplateConfiguration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emailTemplateConfiguration);
    return this.http
      .patch<RestEmailTemplateConfiguration>(
        `${this.resourceUrl}/${this.getEmailTemplateConfigurationIdentifier(emailTemplateConfiguration)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEmailTemplateConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEmailTemplateConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmailTemplateConfigurationIdentifier(emailTemplateConfiguration: Pick<IEmailTemplateConfiguration, 'id'>): number {
    return emailTemplateConfiguration.id;
  }

  compareEmailTemplateConfiguration(
    o1: Pick<IEmailTemplateConfiguration, 'id'> | null,
    o2: Pick<IEmailTemplateConfiguration, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getEmailTemplateConfigurationIdentifier(o1) === this.getEmailTemplateConfigurationIdentifier(o2) : o1 === o2;
  }

  addEmailTemplateConfigurationToCollectionIfMissing<Type extends Pick<IEmailTemplateConfiguration, 'id'>>(
    emailTemplateConfigurationCollection: Type[],
    ...emailTemplateConfigurationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const emailTemplateConfigurations: Type[] = emailTemplateConfigurationsToCheck.filter(isPresent);
    if (emailTemplateConfigurations.length > 0) {
      const emailTemplateConfigurationCollectionIdentifiers = emailTemplateConfigurationCollection.map(emailTemplateConfigurationItem =>
        this.getEmailTemplateConfigurationIdentifier(emailTemplateConfigurationItem),
      );
      const emailTemplateConfigurationsToAdd = emailTemplateConfigurations.filter(emailTemplateConfigurationItem => {
        const emailTemplateConfigurationIdentifier = this.getEmailTemplateConfigurationIdentifier(emailTemplateConfigurationItem);
        if (emailTemplateConfigurationCollectionIdentifiers.includes(emailTemplateConfigurationIdentifier)) {
          return false;
        }
        emailTemplateConfigurationCollectionIdentifiers.push(emailTemplateConfigurationIdentifier);
        return true;
      });
      return [...emailTemplateConfigurationsToAdd, ...emailTemplateConfigurationCollection];
    }
    return emailTemplateConfigurationCollection;
  }

  protected convertDateFromClient<
    T extends IEmailTemplateConfiguration | NewEmailTemplateConfiguration | PartialUpdateEmailTemplateConfiguration,
  >(emailTemplateConfiguration: T): RestOf<T> {
    return {
      ...emailTemplateConfiguration,
      dateCreation: emailTemplateConfiguration.dateCreation?.toJSON() ?? null,
      dateModify: emailTemplateConfiguration.dateModify?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEmailTemplateConfiguration: RestEmailTemplateConfiguration): IEmailTemplateConfiguration {
    return {
      ...restEmailTemplateConfiguration,
      dateCreation: restEmailTemplateConfiguration.dateCreation ? dayjs(restEmailTemplateConfiguration.dateCreation) : undefined,
      dateModify: restEmailTemplateConfiguration.dateModify ? dayjs(restEmailTemplateConfiguration.dateModify) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEmailTemplateConfiguration>): HttpResponse<IEmailTemplateConfiguration> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestEmailTemplateConfiguration[]>,
  ): HttpResponse<IEmailTemplateConfiguration[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
