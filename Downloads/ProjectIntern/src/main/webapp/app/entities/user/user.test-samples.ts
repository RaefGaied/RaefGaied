import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 12245,
  login: 'Ueq',
};

export const sampleWithPartialData: IUser = {
  id: 20042,
  login: '-U0Y_',
};

export const sampleWithFullData: IUser = {
  id: 9744,
  login: 'GI5Vrq',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
