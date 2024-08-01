import { IPartenaire, NewPartenaire } from './partenaire.model';

export const sampleWithRequiredData: IPartenaire = {
  id: 32021,
};

export const sampleWithPartialData: IPartenaire = {
  id: 30266,
};

export const sampleWithFullData: IPartenaire = {
  id: 26365,
  description: 'minority lest',
  nom: 'er although',
  contact: 'uselessly',
  adresse: 'throughout worth',
  typePartenaire: 'wonderfully drat',
};

export const sampleWithNewData: NewPartenaire = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
