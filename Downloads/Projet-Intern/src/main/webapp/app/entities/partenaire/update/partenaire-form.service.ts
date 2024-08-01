import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPartenaire, NewPartenaire } from '../partenaire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPartenaire for edit and NewPartenaireFormGroupInput for create.
 */
type PartenaireFormGroupInput = IPartenaire | PartialWithRequiredKeyOf<NewPartenaire>;

type PartenaireFormDefaults = Pick<NewPartenaire, 'id'>;

type PartenaireFormGroupContent = {
  id: FormControl<IPartenaire['id'] | NewPartenaire['id']>;
  description: FormControl<IPartenaire['description']>;
  nom: FormControl<IPartenaire['nom']>;
  contact: FormControl<IPartenaire['contact']>;
  adresse: FormControl<IPartenaire['adresse']>;
  typePartenaire: FormControl<IPartenaire['typePartenaire']>;
  service: FormControl<IPartenaire['service']>;
};

export type PartenaireFormGroup = FormGroup<PartenaireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PartenaireFormService {
  createPartenaireFormGroup(partenaire: PartenaireFormGroupInput = { id: null }): PartenaireFormGroup {
    const partenaireRawValue = {
      ...this.getFormDefaults(),
      ...partenaire,
    };
    return new FormGroup<PartenaireFormGroupContent>({
      id: new FormControl(
        { value: partenaireRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      description: new FormControl(partenaireRawValue.description),
      nom: new FormControl(partenaireRawValue.nom),
      contact: new FormControl(partenaireRawValue.contact),
      adresse: new FormControl(partenaireRawValue.adresse),
      typePartenaire: new FormControl(partenaireRawValue.typePartenaire),
      service: new FormControl(partenaireRawValue.service),
    });
  }

  getPartenaire(form: PartenaireFormGroup): IPartenaire | NewPartenaire {
    return form.getRawValue() as IPartenaire | NewPartenaire;
  }

  resetForm(form: PartenaireFormGroup, partenaire: PartenaireFormGroupInput): void {
    const partenaireRawValue = { ...this.getFormDefaults(), ...partenaire };
    form.reset(
      {
        ...partenaireRawValue,
        id: { value: partenaireRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PartenaireFormDefaults {
    return {
      id: null,
    };
  }
}
