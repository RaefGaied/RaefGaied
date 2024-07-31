import { IJob, NewJob } from './job.model';

export const sampleWithRequiredData: IJob = {
  id: 3370,
};

export const sampleWithPartialData: IJob = {
  id: 32378,
  jobTitle: 'Chief Assurance Manager',
  maxSalary: 14789,
};

export const sampleWithFullData: IJob = {
  id: 31613,
  jobTitle: 'Forward Security Representative',
  minSalary: 16359,
  maxSalary: 20065,
};

export const sampleWithNewData: NewJob = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
