import dayjs from 'dayjs/esm';

import { IUIConfiguration, NewUIConfiguration } from './ui-configuration.model';

export const sampleWithRequiredData: IUIConfiguration = {
  id: 26381,
};

export const sampleWithPartialData: IUIConfiguration = {
  id: 27073,
  dateCreation: dayjs('2024-08-01T07:15'),
};

export const sampleWithFullData: IUIConfiguration = {
  id: 19899,
  colorSchema: 'contextualise rewarding',
  logo: 'anti notwithstanding adored',
  banner: 'pfft that',
  dateCreation: dayjs('2024-08-01T03:21'),
  dateModify: dayjs('2024-08-01T07:22'),
};

export const sampleWithNewData: NewUIConfiguration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
