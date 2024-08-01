import dayjs from 'dayjs/esm';

import { IReservation, NewReservation } from './reservation.model';

export const sampleWithRequiredData: IReservation = {
  id: 4807,
};

export const sampleWithPartialData: IReservation = {
  id: 16115,
  dateDebut: dayjs('2024-08-01T07:04'),
  totalPrix: 1561.55,
};

export const sampleWithFullData: IReservation = {
  id: 8942,
  dateDebut: dayjs('2024-07-31T23:18'),
  dateFin: dayjs('2024-07-31T18:47'),
  totalPrix: 11704.4,
  statutPaiement: 'ANNULE',
};

export const sampleWithNewData: NewReservation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
