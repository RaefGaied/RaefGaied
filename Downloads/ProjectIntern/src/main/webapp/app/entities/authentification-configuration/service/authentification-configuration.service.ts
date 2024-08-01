import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAuthentificationConfiguration, NewAuthentificationConfiguration } from '../authentification-configuration.model';

export type PartialUpdateAuthentificationConfiguration = Partial<IAuthentificationConfiguration> &
  Pick<IAuthentificationConfiguration, 'id'>;

export type EntityResponseType = HttpResponse<IAuthentificationConfiguration>;
export type EntityArrayResponseType = HttpResponse<IAuthentificationConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class AuthentificationConfigurationService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/authentification-configurations');

  create(authentificationConfiguration: NewAuthentificationConfiguration): Observable<EntityResponseType> {
    return this.http.post<IAuthentificationConfiguration>(this.resourceUrl, authentificationConfiguration, { observe: 'response' });
  }

  update(authentificationConfiguration: IAuthentificationConfiguration): Observable<EntityResponseType> {
    return this.http.put<IAuthentificationConfiguration>(
      `${this.resourceUrl}/${this.getAuthentificationConfigurationIdentifier(authentificationConfiguration)}`,
      authentificationConfiguration,
      { observe: 'response' },
    );
  }

  partialUpdate(authentificationConfiguration: PartialUpdateAuthentificationConfiguration): Observable<EntityResponseType> {
    return this.http.patch<IAuthentificationConfiguration>(
      `${this.resourceUrl}/${this.getAuthentificationConfigurationIdentifier(authentificationConfiguration)}`,
      authentificationConfiguration,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAuthentificationConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAuthentificationConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAuthentificationConfigurationIdentifier(authentificationConfiguration: Pick<IAuthentificationConfiguration, 'id'>): number {
    return authentificationConfiguration.id;
  }

  compareAuthentificationConfiguration(
    o1: Pick<IAuthentificationConfiguration, 'id'> | null,
    o2: Pick<IAuthentificationConfiguration, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAuthentificationConfigurationIdentifier(o1) === this.getAuthentificationConfigurationIdentifier(o2)
      : o1 === o2;
  }

  addAuthentificationConfigurationToCollectionIfMissing<Type extends Pick<IAuthentificationConfiguration, 'id'>>(
    authentificationConfigurationCollection: Type[],
    ...authentificationConfigurationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const authentificationConfigurations: Type[] = authentificationConfigurationsToCheck.filter(isPresent);
    if (authentificationConfigurations.length > 0) {
      const authentificationConfigurationCollectionIdentifiers = authentificationConfigurationCollection.map(
        authentificationConfigurationItem => this.getAuthentificationConfigurationIdentifier(authentificationConfigurationItem),
      );
      const authentificationConfigurationsToAdd = authentificationConfigurations.filter(authentificationConfigurationItem => {
        const authentificationConfigurationIdentifier = this.getAuthentificationConfigurationIdentifier(authentificationConfigurationItem);
        if (authentificationConfigurationCollectionIdentifiers.includes(authentificationConfigurationIdentifier)) {
          return false;
        }
        authentificationConfigurationCollectionIdentifiers.push(authentificationConfigurationIdentifier);
        return true;
      });
      return [...authentificationConfigurationsToAdd, ...authentificationConfigurationCollection];
    }
    return authentificationConfigurationCollection;
  }
}
