import dayjs from 'dayjs/esm';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { IUser } from 'app/entities/user/user.model';
import { Statut } from 'app/entities/enumerations/statut.model';

export interface IReservation {
  id: number;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  totalPrix?: number | null;
  statutPaiement?: keyof typeof Statut | null;
  hotel?: Pick<IHotel, 'id'> | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewReservation = Omit<IReservation, 'id'> & { id: null };
