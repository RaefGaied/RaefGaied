import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHotelAdministrateur, NewHotelAdministrateur } from '../hotel-administrateur.model';

export type PartialUpdateHotelAdministrateur = Partial<IHotelAdministrateur> & Pick<IHotelAdministrateur, 'id'>;

export type EntityResponseType = HttpResponse<IHotelAdministrateur>;
export type EntityArrayResponseType = HttpResponse<IHotelAdministrateur[]>;

@Injectable({ providedIn: 'root' })
export class HotelAdministrateurService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hotel-administrateurs');

  create(hotelAdministrateur: NewHotelAdministrateur): Observable<EntityResponseType> {
    return this.http.post<IHotelAdministrateur>(this.resourceUrl, hotelAdministrateur, { observe: 'response' });
  }

  update(hotelAdministrateur: IHotelAdministrateur): Observable<EntityResponseType> {
    return this.http.put<IHotelAdministrateur>(
      `${this.resourceUrl}/${this.getHotelAdministrateurIdentifier(hotelAdministrateur)}`,
      hotelAdministrateur,
      { observe: 'response' },
    );
  }

  partialUpdate(hotelAdministrateur: PartialUpdateHotelAdministrateur): Observable<EntityResponseType> {
    return this.http.patch<IHotelAdministrateur>(
      `${this.resourceUrl}/${this.getHotelAdministrateurIdentifier(hotelAdministrateur)}`,
      hotelAdministrateur,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHotelAdministrateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHotelAdministrateur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHotelAdministrateurIdentifier(hotelAdministrateur: Pick<IHotelAdministrateur, 'id'>): number {
    return hotelAdministrateur.id;
  }

  compareHotelAdministrateur(o1: Pick<IHotelAdministrateur, 'id'> | null, o2: Pick<IHotelAdministrateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getHotelAdministrateurIdentifier(o1) === this.getHotelAdministrateurIdentifier(o2) : o1 === o2;
  }

  addHotelAdministrateurToCollectionIfMissing<Type extends Pick<IHotelAdministrateur, 'id'>>(
    hotelAdministrateurCollection: Type[],
    ...hotelAdministrateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hotelAdministrateurs: Type[] = hotelAdministrateursToCheck.filter(isPresent);
    if (hotelAdministrateurs.length > 0) {
      const hotelAdministrateurCollectionIdentifiers = hotelAdministrateurCollection.map(hotelAdministrateurItem =>
        this.getHotelAdministrateurIdentifier(hotelAdministrateurItem),
      );
      const hotelAdministrateursToAdd = hotelAdministrateurs.filter(hotelAdministrateurItem => {
        const hotelAdministrateurIdentifier = this.getHotelAdministrateurIdentifier(hotelAdministrateurItem);
        if (hotelAdministrateurCollectionIdentifiers.includes(hotelAdministrateurIdentifier)) {
          return false;
        }
        hotelAdministrateurCollectionIdentifiers.push(hotelAdministrateurIdentifier);
        return true;
      });
      return [...hotelAdministrateursToAdd, ...hotelAdministrateurCollection];
    }
    return hotelAdministrateurCollection;
  }
}
