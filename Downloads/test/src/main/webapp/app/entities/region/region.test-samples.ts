import { IRegion, NewRegion } from './region.model';

export const sampleWithRequiredData: IRegion = {
  id: 1751,
};

export const sampleWithPartialData: IRegion = {
  id: 17840,
  regionName: 'till wherever',
};

export const sampleWithFullData: IRegion = {
  id: 31472,
  regionName: 'now',
};

export const sampleWithNewData: NewRegion = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
