import { ICountry, NewCountry } from './country.model';

export const sampleWithRequiredData: ICountry = {
  id: 29400,
};

export const sampleWithPartialData: ICountry = {
  id: 25880,
};

export const sampleWithFullData: ICountry = {
  id: 7053,
  countryName: 'socialism quaintly',
};

export const sampleWithNewData: NewCountry = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
