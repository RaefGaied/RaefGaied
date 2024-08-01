import { IPartenaire, NewPartenaire } from './partenaire.model';

export const sampleWithRequiredData: IPartenaire = {
  id: 13581,
};

export const sampleWithPartialData: IPartenaire = {
  id: 19119,
  description: 'defiantly',
};

export const sampleWithFullData: IPartenaire = {
  id: 2191,
  description: 'palpitate who soliloquy',
  nom: 'bestseller',
  contact: 'boldly craft till',
  adresse: 'uh-huh scrummage',
  typePartenaire: 'and tame',
};

export const sampleWithNewData: NewPartenaire = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
