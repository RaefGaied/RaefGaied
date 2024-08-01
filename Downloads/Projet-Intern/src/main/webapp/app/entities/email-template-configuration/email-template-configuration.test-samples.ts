import dayjs from 'dayjs/esm';

import { IEmailTemplateConfiguration, NewEmailTemplateConfiguration } from './email-template-configuration.model';

export const sampleWithRequiredData: IEmailTemplateConfiguration = {
  id: 18349,
};

export const sampleWithPartialData: IEmailTemplateConfiguration = {
  id: 5251,
  nomTemplate: 'wince',
};

export const sampleWithFullData: IEmailTemplateConfiguration = {
  id: 1459,
  nomTemplate: 'harsh',
  corps: 'apology muscat clapboard',
  dateCreation: dayjs('2024-08-01T10:25'),
  dateModify: dayjs('2024-07-31T16:08'),
  activeStatus: 'OFFLINE',
};

export const sampleWithNewData: NewEmailTemplateConfiguration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
