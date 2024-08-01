import { IService, NewService } from './service.model';

export const sampleWithRequiredData: IService = {
  id: 21963,
};

export const sampleWithPartialData: IService = {
  id: 10102,
};

export const sampleWithFullData: IService = {
  id: 30482,
  nom: 'whoa surprisingly',
  description: 'ah',
  prix: 31738.34,
  disposability: 'prickly intelligent far',
  capacite: 13781,
  typeService: 'pro raisin',
};

export const sampleWithNewData: NewService = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
