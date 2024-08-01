import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '3c8dba2b-8508-4cb0-a2b2-04407166bfe9',
};

export const sampleWithPartialData: IAuthority = {
  name: '154dd8a5-edbc-4233-8be8-2a2ca1025b82',
};

export const sampleWithFullData: IAuthority = {
  name: '999a8838-050e-4ac8-863e-aa72893f8135',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
