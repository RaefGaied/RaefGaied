import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IService, NewService } from '../service.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IService for edit and NewServiceFormGroupInput for create.
 */
type ServiceFormGroupInput = IService | PartialWithRequiredKeyOf<NewService>;

type ServiceFormDefaults = Pick<NewService, 'id'>;

type ServiceFormGroupContent = {
  id: FormControl<IService['id'] | NewService['id']>;
  nom: FormControl<IService['nom']>;
  description: FormControl<IService['description']>;
  prix: FormControl<IService['prix']>;
  disposability: FormControl<IService['disposability']>;
  capacite: FormControl<IService['capacite']>;
  typeService: FormControl<IService['typeService']>;
  hotel: FormControl<IService['hotel']>;
  partenaire: FormControl<IService['partenaire']>;
  reservation: FormControl<IService['reservation']>;
};

export type ServiceFormGroup = FormGroup<ServiceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServiceFormService {
  createServiceFormGroup(service: ServiceFormGroupInput = { id: null }): ServiceFormGroup {
    const serviceRawValue = {
      ...this.getFormDefaults(),
      ...service,
    };
    return new FormGroup<ServiceFormGroupContent>({
      id: new FormControl(
        { value: serviceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(serviceRawValue.nom),
      description: new FormControl(serviceRawValue.description),
      prix: new FormControl(serviceRawValue.prix),
      disposability: new FormControl(serviceRawValue.disposability),
      capacite: new FormControl(serviceRawValue.capacite),
      typeService: new FormControl(serviceRawValue.typeService),
      hotel: new FormControl(serviceRawValue.hotel),
      partenaire: new FormControl(serviceRawValue.partenaire),
      reservation: new FormControl(serviceRawValue.reservation),
    });
  }

  getService(form: ServiceFormGroup): IService | NewService {
    return form.getRawValue() as IService | NewService;
  }

  resetForm(form: ServiceFormGroup, service: ServiceFormGroupInput): void {
    const serviceRawValue = { ...this.getFormDefaults(), ...service };
    form.reset(
      {
        ...serviceRawValue,
        id: { value: serviceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServiceFormDefaults {
    return {
      id: null,
    };
  }
}
