import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmailTemplateConfiguration, NewEmailTemplateConfiguration } from '../email-template-configuration.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmailTemplateConfiguration for edit and NewEmailTemplateConfigurationFormGroupInput for create.
 */
type EmailTemplateConfigurationFormGroupInput = IEmailTemplateConfiguration | PartialWithRequiredKeyOf<NewEmailTemplateConfiguration>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmailTemplateConfiguration | NewEmailTemplateConfiguration> = Omit<T, 'dateCreation' | 'dateModify'> & {
  dateCreation?: string | null;
  dateModify?: string | null;
};

type EmailTemplateConfigurationFormRawValue = FormValueOf<IEmailTemplateConfiguration>;

type NewEmailTemplateConfigurationFormRawValue = FormValueOf<NewEmailTemplateConfiguration>;

type EmailTemplateConfigurationFormDefaults = Pick<NewEmailTemplateConfiguration, 'id' | 'dateCreation' | 'dateModify'>;

type EmailTemplateConfigurationFormGroupContent = {
  id: FormControl<EmailTemplateConfigurationFormRawValue['id'] | NewEmailTemplateConfiguration['id']>;
  nomTemplate: FormControl<EmailTemplateConfigurationFormRawValue['nomTemplate']>;
  corps: FormControl<EmailTemplateConfigurationFormRawValue['corps']>;
  dateCreation: FormControl<EmailTemplateConfigurationFormRawValue['dateCreation']>;
  dateModify: FormControl<EmailTemplateConfigurationFormRawValue['dateModify']>;
  activeStatus: FormControl<EmailTemplateConfigurationFormRawValue['activeStatus']>;
  hotel: FormControl<EmailTemplateConfigurationFormRawValue['hotel']>;
};

export type EmailTemplateConfigurationFormGroup = FormGroup<EmailTemplateConfigurationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmailTemplateConfigurationFormService {
  createEmailTemplateConfigurationFormGroup(
    emailTemplateConfiguration: EmailTemplateConfigurationFormGroupInput = { id: null },
  ): EmailTemplateConfigurationFormGroup {
    const emailTemplateConfigurationRawValue = this.convertEmailTemplateConfigurationToEmailTemplateConfigurationRawValue({
      ...this.getFormDefaults(),
      ...emailTemplateConfiguration,
    });
    return new FormGroup<EmailTemplateConfigurationFormGroupContent>({
      id: new FormControl(
        { value: emailTemplateConfigurationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomTemplate: new FormControl(emailTemplateConfigurationRawValue.nomTemplate),
      corps: new FormControl(emailTemplateConfigurationRawValue.corps),
      dateCreation: new FormControl(emailTemplateConfigurationRawValue.dateCreation),
      dateModify: new FormControl(emailTemplateConfigurationRawValue.dateModify),
      activeStatus: new FormControl(emailTemplateConfigurationRawValue.activeStatus),
      hotel: new FormControl(emailTemplateConfigurationRawValue.hotel),
    });
  }

  getEmailTemplateConfiguration(form: EmailTemplateConfigurationFormGroup): IEmailTemplateConfiguration | NewEmailTemplateConfiguration {
    return this.convertEmailTemplateConfigurationRawValueToEmailTemplateConfiguration(
      form.getRawValue() as EmailTemplateConfigurationFormRawValue | NewEmailTemplateConfigurationFormRawValue,
    );
  }

  resetForm(form: EmailTemplateConfigurationFormGroup, emailTemplateConfiguration: EmailTemplateConfigurationFormGroupInput): void {
    const emailTemplateConfigurationRawValue = this.convertEmailTemplateConfigurationToEmailTemplateConfigurationRawValue({
      ...this.getFormDefaults(),
      ...emailTemplateConfiguration,
    });
    form.reset(
      {
        ...emailTemplateConfigurationRawValue,
        id: { value: emailTemplateConfigurationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmailTemplateConfigurationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateCreation: currentTime,
      dateModify: currentTime,
    };
  }

  private convertEmailTemplateConfigurationRawValueToEmailTemplateConfiguration(
    rawEmailTemplateConfiguration: EmailTemplateConfigurationFormRawValue | NewEmailTemplateConfigurationFormRawValue,
  ): IEmailTemplateConfiguration | NewEmailTemplateConfiguration {
    return {
      ...rawEmailTemplateConfiguration,
      dateCreation: dayjs(rawEmailTemplateConfiguration.dateCreation, DATE_TIME_FORMAT),
      dateModify: dayjs(rawEmailTemplateConfiguration.dateModify, DATE_TIME_FORMAT),
    };
  }

  private convertEmailTemplateConfigurationToEmailTemplateConfigurationRawValue(
    emailTemplateConfiguration:
      | IEmailTemplateConfiguration
      | (Partial<NewEmailTemplateConfiguration> & EmailTemplateConfigurationFormDefaults),
  ): EmailTemplateConfigurationFormRawValue | PartialWithRequiredKeyOf<NewEmailTemplateConfigurationFormRawValue> {
    return {
      ...emailTemplateConfiguration,
      dateCreation: emailTemplateConfiguration.dateCreation ? emailTemplateConfiguration.dateCreation.format(DATE_TIME_FORMAT) : undefined,
      dateModify: emailTemplateConfiguration.dateModify ? emailTemplateConfiguration.dateModify.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
