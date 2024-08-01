import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAuthentificationConfiguration, NewAuthentificationConfiguration } from '../authentification-configuration.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAuthentificationConfiguration for edit and NewAuthentificationConfigurationFormGroupInput for create.
 */
type AuthentificationConfigurationFormGroupInput =
  | IAuthentificationConfiguration
  | PartialWithRequiredKeyOf<NewAuthentificationConfiguration>;

type AuthentificationConfigurationFormDefaults = Pick<NewAuthentificationConfiguration, 'id' | 'twoFactorEnabled'>;

type AuthentificationConfigurationFormGroupContent = {
  id: FormControl<IAuthentificationConfiguration['id'] | NewAuthentificationConfiguration['id']>;
  twoFactorEnabled: FormControl<IAuthentificationConfiguration['twoFactorEnabled']>;
  loginPageCustomization: FormControl<IAuthentificationConfiguration['loginPageCustomization']>;
  hotel: FormControl<IAuthentificationConfiguration['hotel']>;
};

export type AuthentificationConfigurationFormGroup = FormGroup<AuthentificationConfigurationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AuthentificationConfigurationFormService {
  createAuthentificationConfigurationFormGroup(
    authentificationConfiguration: AuthentificationConfigurationFormGroupInput = { id: null },
  ): AuthentificationConfigurationFormGroup {
    const authentificationConfigurationRawValue = {
      ...this.getFormDefaults(),
      ...authentificationConfiguration,
    };
    return new FormGroup<AuthentificationConfigurationFormGroupContent>({
      id: new FormControl(
        { value: authentificationConfigurationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      twoFactorEnabled: new FormControl(authentificationConfigurationRawValue.twoFactorEnabled),
      loginPageCustomization: new FormControl(authentificationConfigurationRawValue.loginPageCustomization),
      hotel: new FormControl(authentificationConfigurationRawValue.hotel),
    });
  }

  getAuthentificationConfiguration(
    form: AuthentificationConfigurationFormGroup,
  ): IAuthentificationConfiguration | NewAuthentificationConfiguration {
    return form.getRawValue() as IAuthentificationConfiguration | NewAuthentificationConfiguration;
  }

  resetForm(
    form: AuthentificationConfigurationFormGroup,
    authentificationConfiguration: AuthentificationConfigurationFormGroupInput,
  ): void {
    const authentificationConfigurationRawValue = { ...this.getFormDefaults(), ...authentificationConfiguration };
    form.reset(
      {
        ...authentificationConfigurationRawValue,
        id: { value: authentificationConfigurationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AuthentificationConfigurationFormDefaults {
    return {
      id: null,
      twoFactorEnabled: false,
    };
  }
}
