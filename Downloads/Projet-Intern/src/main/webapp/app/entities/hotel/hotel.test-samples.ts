import { IHotel, NewHotel } from './hotel.model';

export const sampleWithRequiredData: IHotel = {
  id: 18754,
};

export const sampleWithPartialData: IHotel = {
  id: 6416,
  address: 'freckle even loyal',
  ville: 'stravage sailing',
  vueS: 'nor',
};

export const sampleWithFullData: IHotel = {
  id: 4609,
  nom: 'vengeful greatly',
  address: 'um phooey',
  numTel: 'through inflect refuge',
  pays: 'tenderly',
  ville: 'eek lone although',
  vueS: 'pronounce',
  capacity: 10668,
  notation: 'till yuck accounting',
  lienUnique: 'efficacy',
};

export const sampleWithNewData: NewHotel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
