import { IHotelAdministrateur, NewHotelAdministrateur } from './hotel-administrateur.model';

export const sampleWithRequiredData: IHotelAdministrateur = {
  id: 19154,
};

export const sampleWithPartialData: IHotelAdministrateur = {
  id: 18825,
};

export const sampleWithFullData: IHotelAdministrateur = {
  id: 18034,
  nom: 'worth',
  email: 'Manuel68@hotmail.com',
  motDePasse: 'ha',
};

export const sampleWithNewData: NewHotelAdministrateur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
