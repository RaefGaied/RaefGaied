import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  id: 4485,
};

export const sampleWithPartialData: ILocation = {
  id: 25256,
  postalCode: 'till blindly',
};

export const sampleWithFullData: ILocation = {
  id: 4240,
  streetAddress: 'hideous',
  postalCode: 'worth among indeed',
  city: 'Lake Maximillian',
  stateProvince: 'since abaft',
};

export const sampleWithNewData: NewLocation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
