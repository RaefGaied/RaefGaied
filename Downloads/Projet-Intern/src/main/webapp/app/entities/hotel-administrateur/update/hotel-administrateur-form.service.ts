import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IHotelAdministrateur, NewHotelAdministrateur } from '../hotel-administrateur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHotelAdministrateur for edit and NewHotelAdministrateurFormGroupInput for create.
 */
type HotelAdministrateurFormGroupInput = IHotelAdministrateur | PartialWithRequiredKeyOf<NewHotelAdministrateur>;

type HotelAdministrateurFormDefaults = Pick<NewHotelAdministrateur, 'id'>;

type HotelAdministrateurFormGroupContent = {
  id: FormControl<IHotelAdministrateur['id'] | NewHotelAdministrateur['id']>;
  nom: FormControl<IHotelAdministrateur['nom']>;
  email: FormControl<IHotelAdministrateur['email']>;
  motDePasse: FormControl<IHotelAdministrateur['motDePasse']>;
};

export type HotelAdministrateurFormGroup = FormGroup<HotelAdministrateurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HotelAdministrateurFormService {
  createHotelAdministrateurFormGroup(hotelAdministrateur: HotelAdministrateurFormGroupInput = { id: null }): HotelAdministrateurFormGroup {
    const hotelAdministrateurRawValue = {
      ...this.getFormDefaults(),
      ...hotelAdministrateur,
    };
    return new FormGroup<HotelAdministrateurFormGroupContent>({
      id: new FormControl(
        { value: hotelAdministrateurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(hotelAdministrateurRawValue.nom),
      email: new FormControl(hotelAdministrateurRawValue.email),
      motDePasse: new FormControl(hotelAdministrateurRawValue.motDePasse),
    });
  }

  getHotelAdministrateur(form: HotelAdministrateurFormGroup): IHotelAdministrateur | NewHotelAdministrateur {
    return form.getRawValue() as IHotelAdministrateur | NewHotelAdministrateur;
  }

  resetForm(form: HotelAdministrateurFormGroup, hotelAdministrateur: HotelAdministrateurFormGroupInput): void {
    const hotelAdministrateurRawValue = { ...this.getFormDefaults(), ...hotelAdministrateur };
    form.reset(
      {
        ...hotelAdministrateurRawValue,
        id: { value: hotelAdministrateurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): HotelAdministrateurFormDefaults {
    return {
      id: null,
    };
  }
}
