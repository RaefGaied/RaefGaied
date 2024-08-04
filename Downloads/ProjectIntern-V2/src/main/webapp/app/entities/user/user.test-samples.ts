import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 9636,
  login: 'wDU@vS-7zh\\XM\\gPPVJE\\Mt\\8xBRKPd\\"bB2',
};

export const sampleWithPartialData: IUser = {
  id: 11454,
  login: '&@3nrFT\\,Dp\\?E\\@zkR',
};

export const sampleWithFullData: IUser = {
  id: 28496,
  login: 'H@IhXi\\al\\[awx\\:Nk\\!H',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
