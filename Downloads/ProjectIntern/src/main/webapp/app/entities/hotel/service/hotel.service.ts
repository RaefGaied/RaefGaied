import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHotel, NewHotel } from '../hotel.model';

export type PartialUpdateHotel = Partial<IHotel> & Pick<IHotel, 'id'>;

export type EntityResponseType = HttpResponse<IHotel>;
export type EntityArrayResponseType = HttpResponse<IHotel[]>;

@Injectable({ providedIn: 'root' })
export class HotelService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hotels');

  create(hotel: NewHotel): Observable<EntityResponseType> {
    return this.http.post<IHotel>(this.resourceUrl, hotel, { observe: 'response' });
  }

  update(hotel: IHotel): Observable<EntityResponseType> {
    return this.http.put<IHotel>(`${this.resourceUrl}/${this.getHotelIdentifier(hotel)}`, hotel, { observe: 'response' });
  }

  partialUpdate(hotel: PartialUpdateHotel): Observable<EntityResponseType> {
    return this.http.patch<IHotel>(`${this.resourceUrl}/${this.getHotelIdentifier(hotel)}`, hotel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHotel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHotel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHotelIdentifier(hotel: Pick<IHotel, 'id'>): number {
    return hotel.id;
  }

  compareHotel(o1: Pick<IHotel, 'id'> | null, o2: Pick<IHotel, 'id'> | null): boolean {
    return o1 && o2 ? this.getHotelIdentifier(o1) === this.getHotelIdentifier(o2) : o1 === o2;
  }

  addHotelToCollectionIfMissing<Type extends Pick<IHotel, 'id'>>(
    hotelCollection: Type[],
    ...hotelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hotels: Type[] = hotelsToCheck.filter(isPresent);
    if (hotels.length > 0) {
      const hotelCollectionIdentifiers = hotelCollection.map(hotelItem => this.getHotelIdentifier(hotelItem));
      const hotelsToAdd = hotels.filter(hotelItem => {
        const hotelIdentifier = this.getHotelIdentifier(hotelItem);
        if (hotelCollectionIdentifiers.includes(hotelIdentifier)) {
          return false;
        }
        hotelCollectionIdentifiers.push(hotelIdentifier);
        return true;
      });
      return [...hotelsToAdd, ...hotelCollection];
    }
    return hotelCollection;
  }
}
