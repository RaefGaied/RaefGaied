import { IAuthentificationConfiguration, NewAuthentificationConfiguration } from './authentification-configuration.model';

export const sampleWithRequiredData: IAuthentificationConfiguration = {
  id: 18439,
};

export const sampleWithPartialData: IAuthentificationConfiguration = {
  id: 8122,
  twoFactorEnabled: false,
};

export const sampleWithFullData: IAuthentificationConfiguration = {
  id: 12991,
  twoFactorEnabled: false,
  loginPageCustomization: 'in ah treasure',
};

export const sampleWithNewData: NewAuthentificationConfiguration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
