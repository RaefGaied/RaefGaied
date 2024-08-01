import { IHotelAdministrateur } from 'app/entities/hotel-administrateur/hotel-administrateur.model';

export interface IHotel {
  id: number;
  nom?: string | null;
  address?: string | null;
  numTel?: string | null;
  pays?: string | null;
  ville?: string | null;
  vueS?: string | null;
  capacity?: number | null;
  notation?: string | null;
  lienUnique?: string | null;
  hotelAdministrateur?: Pick<IHotelAdministrateur, 'id'> | null;
}

export type NewHotel = Omit<IHotel, 'id'> & { id: null };
