import dayjs from 'dayjs/esm';

import { IUIConfiguration, NewUIConfiguration } from './ui-configuration.model';

export const sampleWithRequiredData: IUIConfiguration = {
  id: 22285,
};

export const sampleWithPartialData: IUIConfiguration = {
  id: 10115,
  logo: 'airfreight',
  banner: 'joyfully',
  dateCreation: dayjs('2024-08-01T07:30'),
  dateModify: dayjs('2024-08-01T08:15'),
};

export const sampleWithFullData: IUIConfiguration = {
  id: 25132,
  colorSchema: 'alleviate lovely',
  logo: 'ha range sadly',
  banner: 'sympathetically',
  dateCreation: dayjs('2024-08-01T03:00'),
  dateModify: dayjs('2024-08-01T09:28'),
};

export const sampleWithNewData: NewUIConfiguration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
