import { IHotel, NewHotel } from './hotel.model';

export const sampleWithRequiredData: IHotel = {
  id: 5713,
};

export const sampleWithPartialData: IHotel = {
  id: 12744,
  nom: 'worriedly improbable death',
  numTel: 'hopelessly vice till',
  vueS: 'juicy pfft',
  capacity: 7259,
};

export const sampleWithFullData: IHotel = {
  id: 14829,
  nom: 'although ex-wife longingly',
  address: 'brr',
  numTel: 'rapidly',
  pays: 'though',
  ville: 'into given',
  vueS: 'testy beneath',
  capacity: 27493,
  notation: 'up yuck',
  lienUnique: 'what that bold',
};

export const sampleWithNewData: NewHotel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
