import dayjs from 'dayjs/esm';

import { IPaiement, NewPaiement } from './paiement.model';

export const sampleWithRequiredData: IPaiement = {
  id: 22946,
};

export const sampleWithPartialData: IPaiement = {
  id: 17777,
  montant: 15450.03,
  datePaiement: dayjs('2024-07-31T22:53'),
  methodePaiement: 'boudoir as lane',
  description: 'overawe prestigious down',
};

export const sampleWithFullData: IPaiement = {
  id: 24227,
  montant: 31223.49,
  datePaiement: dayjs('2024-07-31T17:19'),
  methodePaiement: 'truthfully barring',
  token: 'than',
  description: 'knuckle',
};

export const sampleWithNewData: NewPaiement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
