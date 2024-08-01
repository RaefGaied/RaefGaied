import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 3412,
  login: 'K@IgaM\\kvw',
};

export const sampleWithPartialData: IUser = {
  id: 6130,
  login: '-8@elw\\G--4M\\8fGzl\\Umi36Mf\\6B6Fg\\[J',
};

export const sampleWithFullData: IUser = {
  id: 27486,
  login: 'Gm',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
