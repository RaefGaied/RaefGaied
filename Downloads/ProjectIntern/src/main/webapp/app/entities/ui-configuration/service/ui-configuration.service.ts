import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUIConfiguration, NewUIConfiguration } from '../ui-configuration.model';

export type PartialUpdateUIConfiguration = Partial<IUIConfiguration> & Pick<IUIConfiguration, 'id'>;

type RestOf<T extends IUIConfiguration | NewUIConfiguration> = Omit<T, 'dateCreation' | 'dateModify'> & {
  dateCreation?: string | null;
  dateModify?: string | null;
};

export type RestUIConfiguration = RestOf<IUIConfiguration>;

export type NewRestUIConfiguration = RestOf<NewUIConfiguration>;

export type PartialUpdateRestUIConfiguration = RestOf<PartialUpdateUIConfiguration>;

export type EntityResponseType = HttpResponse<IUIConfiguration>;
export type EntityArrayResponseType = HttpResponse<IUIConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class UIConfigurationService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ui-configurations');

  create(uIConfiguration: NewUIConfiguration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(uIConfiguration);
    return this.http
      .post<RestUIConfiguration>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(uIConfiguration: IUIConfiguration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(uIConfiguration);
    return this.http
      .put<RestUIConfiguration>(`${this.resourceUrl}/${this.getUIConfigurationIdentifier(uIConfiguration)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(uIConfiguration: PartialUpdateUIConfiguration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(uIConfiguration);
    return this.http
      .patch<RestUIConfiguration>(`${this.resourceUrl}/${this.getUIConfigurationIdentifier(uIConfiguration)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestUIConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestUIConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUIConfigurationIdentifier(uIConfiguration: Pick<IUIConfiguration, 'id'>): number {
    return uIConfiguration.id;
  }

  compareUIConfiguration(o1: Pick<IUIConfiguration, 'id'> | null, o2: Pick<IUIConfiguration, 'id'> | null): boolean {
    return o1 && o2 ? this.getUIConfigurationIdentifier(o1) === this.getUIConfigurationIdentifier(o2) : o1 === o2;
  }

  addUIConfigurationToCollectionIfMissing<Type extends Pick<IUIConfiguration, 'id'>>(
    uIConfigurationCollection: Type[],
    ...uIConfigurationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const uIConfigurations: Type[] = uIConfigurationsToCheck.filter(isPresent);
    if (uIConfigurations.length > 0) {
      const uIConfigurationCollectionIdentifiers = uIConfigurationCollection.map(uIConfigurationItem =>
        this.getUIConfigurationIdentifier(uIConfigurationItem),
      );
      const uIConfigurationsToAdd = uIConfigurations.filter(uIConfigurationItem => {
        const uIConfigurationIdentifier = this.getUIConfigurationIdentifier(uIConfigurationItem);
        if (uIConfigurationCollectionIdentifiers.includes(uIConfigurationIdentifier)) {
          return false;
        }
        uIConfigurationCollectionIdentifiers.push(uIConfigurationIdentifier);
        return true;
      });
      return [...uIConfigurationsToAdd, ...uIConfigurationCollection];
    }
    return uIConfigurationCollection;
  }

  protected convertDateFromClient<T extends IUIConfiguration | NewUIConfiguration | PartialUpdateUIConfiguration>(
    uIConfiguration: T,
  ): RestOf<T> {
    return {
      ...uIConfiguration,
      dateCreation: uIConfiguration.dateCreation?.toJSON() ?? null,
      dateModify: uIConfiguration.dateModify?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restUIConfiguration: RestUIConfiguration): IUIConfiguration {
    return {
      ...restUIConfiguration,
      dateCreation: restUIConfiguration.dateCreation ? dayjs(restUIConfiguration.dateCreation) : undefined,
      dateModify: restUIConfiguration.dateModify ? dayjs(restUIConfiguration.dateModify) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestUIConfiguration>): HttpResponse<IUIConfiguration> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestUIConfiguration[]>): HttpResponse<IUIConfiguration[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
