import { IHotelAdministrateur, NewHotelAdministrateur } from './hotel-administrateur.model';

export const sampleWithRequiredData: IHotelAdministrateur = {
  id: 25794,
};

export const sampleWithPartialData: IHotelAdministrateur = {
  id: 13674,
  nom: 'gorilla acclaimed bouncy',
  email: 'Merritt32@gmail.com',
  motDePasse: 'quizzically bruised',
};

export const sampleWithFullData: IHotelAdministrateur = {
  id: 24906,
  nom: 'neighboring disincentivize methinks',
  email: 'Norbert.Cronin@gmail.com',
  motDePasse: 'tensely athwart',
};

export const sampleWithNewData: NewHotelAdministrateur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
