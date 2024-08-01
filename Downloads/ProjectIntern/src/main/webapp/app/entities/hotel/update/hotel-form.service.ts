import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IHotel, NewHotel } from '../hotel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHotel for edit and NewHotelFormGroupInput for create.
 */
type HotelFormGroupInput = IHotel | PartialWithRequiredKeyOf<NewHotel>;

type HotelFormDefaults = Pick<NewHotel, 'id'>;

type HotelFormGroupContent = {
  id: FormControl<IHotel['id'] | NewHotel['id']>;
  nom: FormControl<IHotel['nom']>;
  address: FormControl<IHotel['address']>;
  numTel: FormControl<IHotel['numTel']>;
  pays: FormControl<IHotel['pays']>;
  ville: FormControl<IHotel['ville']>;
  vueS: FormControl<IHotel['vueS']>;
  capacity: FormControl<IHotel['capacity']>;
  notation: FormControl<IHotel['notation']>;
  lienUnique: FormControl<IHotel['lienUnique']>;
  hotelAdministrateur: FormControl<IHotel['hotelAdministrateur']>;
};

export type HotelFormGroup = FormGroup<HotelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HotelFormService {
  createHotelFormGroup(hotel: HotelFormGroupInput = { id: null }): HotelFormGroup {
    const hotelRawValue = {
      ...this.getFormDefaults(),
      ...hotel,
    };
    return new FormGroup<HotelFormGroupContent>({
      id: new FormControl(
        { value: hotelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(hotelRawValue.nom),
      address: new FormControl(hotelRawValue.address),
      numTel: new FormControl(hotelRawValue.numTel),
      pays: new FormControl(hotelRawValue.pays),
      ville: new FormControl(hotelRawValue.ville),
      vueS: new FormControl(hotelRawValue.vueS),
      capacity: new FormControl(hotelRawValue.capacity),
      notation: new FormControl(hotelRawValue.notation),
      lienUnique: new FormControl(hotelRawValue.lienUnique),
      hotelAdministrateur: new FormControl(hotelRawValue.hotelAdministrateur),
    });
  }

  getHotel(form: HotelFormGroup): IHotel | NewHotel {
    return form.getRawValue() as IHotel | NewHotel;
  }

  resetForm(form: HotelFormGroup, hotel: HotelFormGroupInput): void {
    const hotelRawValue = { ...this.getFormDefaults(), ...hotel };
    form.reset(
      {
        ...hotelRawValue,
        id: { value: hotelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): HotelFormDefaults {
    return {
      id: null,
    };
  }
}
