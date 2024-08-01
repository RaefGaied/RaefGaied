import dayjs from 'dayjs/esm';

import { IPaiement, NewPaiement } from './paiement.model';

export const sampleWithRequiredData: IPaiement = {
  id: 14704,
};

export const sampleWithPartialData: IPaiement = {
  id: 13410,
  montant: 7123.61,
  methodePaiement: 'stealthily concerning',
  description: 'scallop',
};

export const sampleWithFullData: IPaiement = {
  id: 2187,
  montant: 27296.87,
  datePaiement: dayjs('2024-07-31T15:33'),
  methodePaiement: 'unless',
  token: 'clot corps ultimately',
  description: 'inasmuch',
};

export const sampleWithNewData: NewPaiement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
