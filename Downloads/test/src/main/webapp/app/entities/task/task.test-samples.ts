import { ITask, NewTask } from './task.model';

export const sampleWithRequiredData: ITask = {
  id: 15956,
};

export const sampleWithPartialData: ITask = {
  id: 9279,
  description: 'whereas consequently overcharge',
};

export const sampleWithFullData: ITask = {
  id: 25743,
  title: 'probability',
  description: 'as blah marketing',
};

export const sampleWithNewData: NewTask = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
