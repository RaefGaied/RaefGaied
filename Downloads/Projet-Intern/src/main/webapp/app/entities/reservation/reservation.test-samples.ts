import dayjs from 'dayjs/esm';

import { IReservation, NewReservation } from './reservation.model';

export const sampleWithRequiredData: IReservation = {
  id: 18535,
};

export const sampleWithPartialData: IReservation = {
  id: 864,
  dateFin: dayjs('2024-07-31T19:28'),
  totalPrix: 12193.29,
};

export const sampleWithFullData: IReservation = {
  id: 26212,
  dateDebut: dayjs('2024-08-01T08:38'),
  dateFin: dayjs('2024-08-01T03:45'),
  totalPrix: 4120.98,
  statutPaiement: 'CONFIRME',
};

export const sampleWithNewData: NewReservation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
