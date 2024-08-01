import dayjs from 'dayjs/esm';
import { IHotel } from 'app/entities/hotel/hotel.model';

export interface IUIConfiguration {
  id: number;
  colorSchema?: string | null;
  logo?: string | null;
  banner?: string | null;
  dateCreation?: dayjs.Dayjs | null;
  dateModify?: dayjs.Dayjs | null;
  hotel?: Pick<IHotel, 'id'> | null;
}

export type NewUIConfiguration = Omit<IUIConfiguration, 'id'> & { id: null };
