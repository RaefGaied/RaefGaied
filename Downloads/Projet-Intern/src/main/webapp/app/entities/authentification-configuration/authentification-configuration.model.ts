import { IHotel } from 'app/entities/hotel/hotel.model';

export interface IAuthentificationConfiguration {
  id: number;
  twoFactorEnabled?: boolean | null;
  loginPageCustomization?: string | null;
  hotel?: Pick<IHotel, 'id'> | null;
}

export type NewAuthentificationConfiguration = Omit<IAuthentificationConfiguration, 'id'> & { id: null };
