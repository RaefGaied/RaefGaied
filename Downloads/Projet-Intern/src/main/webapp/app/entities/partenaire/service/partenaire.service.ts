import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartenaire, NewPartenaire } from '../partenaire.model';

export type PartialUpdatePartenaire = Partial<IPartenaire> & Pick<IPartenaire, 'id'>;

export type EntityResponseType = HttpResponse<IPartenaire>;
export type EntityArrayResponseType = HttpResponse<IPartenaire[]>;

@Injectable({ providedIn: 'root' })
export class PartenaireService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/partenaires');

  create(partenaire: NewPartenaire): Observable<EntityResponseType> {
    return this.http.post<IPartenaire>(this.resourceUrl, partenaire, { observe: 'response' });
  }

  update(partenaire: IPartenaire): Observable<EntityResponseType> {
    return this.http.put<IPartenaire>(`${this.resourceUrl}/${this.getPartenaireIdentifier(partenaire)}`, partenaire, {
      observe: 'response',
    });
  }

  partialUpdate(partenaire: PartialUpdatePartenaire): Observable<EntityResponseType> {
    return this.http.patch<IPartenaire>(`${this.resourceUrl}/${this.getPartenaireIdentifier(partenaire)}`, partenaire, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartenaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartenaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPartenaireIdentifier(partenaire: Pick<IPartenaire, 'id'>): number {
    return partenaire.id;
  }

  comparePartenaire(o1: Pick<IPartenaire, 'id'> | null, o2: Pick<IPartenaire, 'id'> | null): boolean {
    return o1 && o2 ? this.getPartenaireIdentifier(o1) === this.getPartenaireIdentifier(o2) : o1 === o2;
  }

  addPartenaireToCollectionIfMissing<Type extends Pick<IPartenaire, 'id'>>(
    partenaireCollection: Type[],
    ...partenairesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const partenaires: Type[] = partenairesToCheck.filter(isPresent);
    if (partenaires.length > 0) {
      const partenaireCollectionIdentifiers = partenaireCollection.map(partenaireItem => this.getPartenaireIdentifier(partenaireItem));
      const partenairesToAdd = partenaires.filter(partenaireItem => {
        const partenaireIdentifier = this.getPartenaireIdentifier(partenaireItem);
        if (partenaireCollectionIdentifiers.includes(partenaireIdentifier)) {
          return false;
        }
        partenaireCollectionIdentifiers.push(partenaireIdentifier);
        return true;
      });
      return [...partenairesToAdd, ...partenaireCollection];
    }
    return partenaireCollection;
  }
}
