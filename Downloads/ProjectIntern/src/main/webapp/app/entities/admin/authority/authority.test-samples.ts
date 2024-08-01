import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '42ba77c0-c551-4685-bab1-04a33ac4e502',
};

export const sampleWithPartialData: IAuthority = {
  name: '3929208f-a80e-4cd9-9d3b-428246ddd32f',
};

export const sampleWithFullData: IAuthority = {
  name: '46a18797-61d2-4ffd-93d5-58051fe62f9e',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
