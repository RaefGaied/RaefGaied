import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaiement, NewPaiement } from '../paiement.model';

export type PartialUpdatePaiement = Partial<IPaiement> & Pick<IPaiement, 'id'>;

type RestOf<T extends IPaiement | NewPaiement> = Omit<T, 'datePaiement'> & {
  datePaiement?: string | null;
};

export type RestPaiement = RestOf<IPaiement>;

export type NewRestPaiement = RestOf<NewPaiement>;

export type PartialUpdateRestPaiement = RestOf<PartialUpdatePaiement>;

export type EntityResponseType = HttpResponse<IPaiement>;
export type EntityArrayResponseType = HttpResponse<IPaiement[]>;

@Injectable({ providedIn: 'root' })
export class PaiementService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/paiements');

  create(paiement: NewPaiement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paiement);
    return this.http
      .post<RestPaiement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(paiement: IPaiement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paiement);
    return this.http
      .put<RestPaiement>(`${this.resourceUrl}/${this.getPaiementIdentifier(paiement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(paiement: PartialUpdatePaiement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paiement);
    return this.http
      .patch<RestPaiement>(`${this.resourceUrl}/${this.getPaiementIdentifier(paiement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPaiement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPaiement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaiementIdentifier(paiement: Pick<IPaiement, 'id'>): number {
    return paiement.id;
  }

  comparePaiement(o1: Pick<IPaiement, 'id'> | null, o2: Pick<IPaiement, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaiementIdentifier(o1) === this.getPaiementIdentifier(o2) : o1 === o2;
  }

  addPaiementToCollectionIfMissing<Type extends Pick<IPaiement, 'id'>>(
    paiementCollection: Type[],
    ...paiementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paiements: Type[] = paiementsToCheck.filter(isPresent);
    if (paiements.length > 0) {
      const paiementCollectionIdentifiers = paiementCollection.map(paiementItem => this.getPaiementIdentifier(paiementItem));
      const paiementsToAdd = paiements.filter(paiementItem => {
        const paiementIdentifier = this.getPaiementIdentifier(paiementItem);
        if (paiementCollectionIdentifiers.includes(paiementIdentifier)) {
          return false;
        }
        paiementCollectionIdentifiers.push(paiementIdentifier);
        return true;
      });
      return [...paiementsToAdd, ...paiementCollection];
    }
    return paiementCollection;
  }

  protected convertDateFromClient<T extends IPaiement | NewPaiement | PartialUpdatePaiement>(paiement: T): RestOf<T> {
    return {
      ...paiement,
      datePaiement: paiement.datePaiement?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPaiement: RestPaiement): IPaiement {
    return {
      ...restPaiement,
      datePaiement: restPaiement.datePaiement ? dayjs(restPaiement.datePaiement) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPaiement>): HttpResponse<IPaiement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPaiement[]>): HttpResponse<IPaiement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
