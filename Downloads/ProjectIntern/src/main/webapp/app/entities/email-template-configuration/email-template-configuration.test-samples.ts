import dayjs from 'dayjs/esm';

import { IEmailTemplateConfiguration, NewEmailTemplateConfiguration } from './email-template-configuration.model';

export const sampleWithRequiredData: IEmailTemplateConfiguration = {
  id: 23853,
};

export const sampleWithPartialData: IEmailTemplateConfiguration = {
  id: 17049,
  nomTemplate: 'promptly mmm',
  dateCreation: dayjs('2024-07-31T21:51'),
};

export const sampleWithFullData: IEmailTemplateConfiguration = {
  id: 21331,
  nomTemplate: 'inasmuch once satisfied',
  corps: 'yahoo complete kick',
  dateCreation: dayjs('2024-07-31T16:58'),
  dateModify: dayjs('2024-08-01T06:23'),
  activeStatus: 'OFFLINE',
};

export const sampleWithNewData: NewEmailTemplateConfiguration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
