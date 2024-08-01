import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUIConfiguration, NewUIConfiguration } from '../ui-configuration.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUIConfiguration for edit and NewUIConfigurationFormGroupInput for create.
 */
type UIConfigurationFormGroupInput = IUIConfiguration | PartialWithRequiredKeyOf<NewUIConfiguration>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IUIConfiguration | NewUIConfiguration> = Omit<T, 'dateCreation' | 'dateModify'> & {
  dateCreation?: string | null;
  dateModify?: string | null;
};

type UIConfigurationFormRawValue = FormValueOf<IUIConfiguration>;

type NewUIConfigurationFormRawValue = FormValueOf<NewUIConfiguration>;

type UIConfigurationFormDefaults = Pick<NewUIConfiguration, 'id' | 'dateCreation' | 'dateModify'>;

type UIConfigurationFormGroupContent = {
  id: FormControl<UIConfigurationFormRawValue['id'] | NewUIConfiguration['id']>;
  colorSchema: FormControl<UIConfigurationFormRawValue['colorSchema']>;
  logo: FormControl<UIConfigurationFormRawValue['logo']>;
  banner: FormControl<UIConfigurationFormRawValue['banner']>;
  dateCreation: FormControl<UIConfigurationFormRawValue['dateCreation']>;
  dateModify: FormControl<UIConfigurationFormRawValue['dateModify']>;
  hotel: FormControl<UIConfigurationFormRawValue['hotel']>;
};

export type UIConfigurationFormGroup = FormGroup<UIConfigurationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UIConfigurationFormService {
  createUIConfigurationFormGroup(uIConfiguration: UIConfigurationFormGroupInput = { id: null }): UIConfigurationFormGroup {
    const uIConfigurationRawValue = this.convertUIConfigurationToUIConfigurationRawValue({
      ...this.getFormDefaults(),
      ...uIConfiguration,
    });
    return new FormGroup<UIConfigurationFormGroupContent>({
      id: new FormControl(
        { value: uIConfigurationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      colorSchema: new FormControl(uIConfigurationRawValue.colorSchema),
      logo: new FormControl(uIConfigurationRawValue.logo),
      banner: new FormControl(uIConfigurationRawValue.banner),
      dateCreation: new FormControl(uIConfigurationRawValue.dateCreation),
      dateModify: new FormControl(uIConfigurationRawValue.dateModify),
      hotel: new FormControl(uIConfigurationRawValue.hotel),
    });
  }

  getUIConfiguration(form: UIConfigurationFormGroup): IUIConfiguration | NewUIConfiguration {
    return this.convertUIConfigurationRawValueToUIConfiguration(
      form.getRawValue() as UIConfigurationFormRawValue | NewUIConfigurationFormRawValue,
    );
  }

  resetForm(form: UIConfigurationFormGroup, uIConfiguration: UIConfigurationFormGroupInput): void {
    const uIConfigurationRawValue = this.convertUIConfigurationToUIConfigurationRawValue({ ...this.getFormDefaults(), ...uIConfiguration });
    form.reset(
      {
        ...uIConfigurationRawValue,
        id: { value: uIConfigurationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UIConfigurationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateCreation: currentTime,
      dateModify: currentTime,
    };
  }

  private convertUIConfigurationRawValueToUIConfiguration(
    rawUIConfiguration: UIConfigurationFormRawValue | NewUIConfigurationFormRawValue,
  ): IUIConfiguration | NewUIConfiguration {
    return {
      ...rawUIConfiguration,
      dateCreation: dayjs(rawUIConfiguration.dateCreation, DATE_TIME_FORMAT),
      dateModify: dayjs(rawUIConfiguration.dateModify, DATE_TIME_FORMAT),
    };
  }

  private convertUIConfigurationToUIConfigurationRawValue(
    uIConfiguration: IUIConfiguration | (Partial<NewUIConfiguration> & UIConfigurationFormDefaults),
  ): UIConfigurationFormRawValue | PartialWithRequiredKeyOf<NewUIConfigurationFormRawValue> {
    return {
      ...uIConfiguration,
      dateCreation: uIConfiguration.dateCreation ? uIConfiguration.dateCreation.format(DATE_TIME_FORMAT) : undefined,
      dateModify: uIConfiguration.dateModify ? uIConfiguration.dateModify.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
