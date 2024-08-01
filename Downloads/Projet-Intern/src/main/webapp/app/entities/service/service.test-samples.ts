import { IService, NewService } from './service.model';

export const sampleWithRequiredData: IService = {
  id: 18366,
};

export const sampleWithPartialData: IService = {
  id: 31525,
  nom: 'where',
  prix: 20308.02,
  capacite: 18880,
};

export const sampleWithFullData: IService = {
  id: 14642,
  nom: 'naturally',
  description: 'out',
  prix: 15892.27,
  disposability: 'place through yuck',
  capacite: 9974,
  typeService: 'incidentally standard joyfully',
};

export const sampleWithNewData: NewService = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
