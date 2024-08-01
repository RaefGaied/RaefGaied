import dayjs from 'dayjs/esm';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { Act } from 'app/entities/enumerations/act.model';

export interface IEmailTemplateConfiguration {
  id: number;
  nomTemplate?: string | null;
  corps?: string | null;
  dateCreation?: dayjs.Dayjs | null;
  dateModify?: dayjs.Dayjs | null;
  activeStatus?: keyof typeof Act | null;
  hotel?: Pick<IHotel, 'id'> | null;
}

export type NewEmailTemplateConfiguration = Omit<IEmailTemplateConfiguration, 'id'> & { id: null };
