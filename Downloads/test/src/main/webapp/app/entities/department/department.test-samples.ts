import { IDepartment, NewDepartment } from './department.model';

export const sampleWithRequiredData: IDepartment = {
  id: 24851,
  departmentName: 'gadzooks even',
};

export const sampleWithPartialData: IDepartment = {
  id: 26821,
  departmentName: 'prefix yahoo',
};

export const sampleWithFullData: IDepartment = {
  id: 17997,
  departmentName: 'diffuse huzzah asparagus',
};

export const sampleWithNewData: NewDepartment = {
  departmentName: 'exeunt early',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
