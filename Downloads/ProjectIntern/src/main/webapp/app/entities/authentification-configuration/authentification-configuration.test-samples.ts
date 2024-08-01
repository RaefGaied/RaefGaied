import { IAuthentificationConfiguration, NewAuthentificationConfiguration } from './authentification-configuration.model';

export const sampleWithRequiredData: IAuthentificationConfiguration = {
  id: 24136,
};

export const sampleWithPartialData: IAuthentificationConfiguration = {
  id: 7334,
};

export const sampleWithFullData: IAuthentificationConfiguration = {
  id: 4737,
  twoFactorEnabled: false,
  loginPageCustomization: 'quirky neat before',
};

export const sampleWithNewData: NewAuthentificationConfiguration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
