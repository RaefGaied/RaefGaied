import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IReservation, NewReservation } from '../reservation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReservation for edit and NewReservationFormGroupInput for create.
 */
type ReservationFormGroupInput = IReservation | PartialWithRequiredKeyOf<NewReservation>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IReservation | NewReservation> = Omit<T, 'dateDebut' | 'dateFin'> & {
  dateDebut?: string | null;
  dateFin?: string | null;
};

type ReservationFormRawValue = FormValueOf<IReservation>;

type NewReservationFormRawValue = FormValueOf<NewReservation>;

type ReservationFormDefaults = Pick<NewReservation, 'id' | 'dateDebut' | 'dateFin'>;

type ReservationFormGroupContent = {
  id: FormControl<ReservationFormRawValue['id'] | NewReservation['id']>;
  dateDebut: FormControl<ReservationFormRawValue['dateDebut']>;
  dateFin: FormControl<ReservationFormRawValue['dateFin']>;
  totalPrix: FormControl<ReservationFormRawValue['totalPrix']>;
  statutPaiement: FormControl<ReservationFormRawValue['statutPaiement']>;
  hotel: FormControl<ReservationFormRawValue['hotel']>;
  user: FormControl<ReservationFormRawValue['user']>;
};

export type ReservationFormGroup = FormGroup<ReservationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReservationFormService {
  createReservationFormGroup(reservation: ReservationFormGroupInput = { id: null }): ReservationFormGroup {
    const reservationRawValue = this.convertReservationToReservationRawValue({
      ...this.getFormDefaults(),
      ...reservation,
    });
    return new FormGroup<ReservationFormGroupContent>({
      id: new FormControl(
        { value: reservationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dateDebut: new FormControl(reservationRawValue.dateDebut),
      dateFin: new FormControl(reservationRawValue.dateFin),
      totalPrix: new FormControl(reservationRawValue.totalPrix),
      statutPaiement: new FormControl(reservationRawValue.statutPaiement),
      hotel: new FormControl(reservationRawValue.hotel),
      user: new FormControl(reservationRawValue.user),
    });
  }

  getReservation(form: ReservationFormGroup): IReservation | NewReservation {
    return this.convertReservationRawValueToReservation(form.getRawValue() as ReservationFormRawValue | NewReservationFormRawValue);
  }

  resetForm(form: ReservationFormGroup, reservation: ReservationFormGroupInput): void {
    const reservationRawValue = this.convertReservationToReservationRawValue({ ...this.getFormDefaults(), ...reservation });
    form.reset(
      {
        ...reservationRawValue,
        id: { value: reservationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ReservationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateDebut: currentTime,
      dateFin: currentTime,
    };
  }

  private convertReservationRawValueToReservation(
    rawReservation: ReservationFormRawValue | NewReservationFormRawValue,
  ): IReservation | NewReservation {
    return {
      ...rawReservation,
      dateDebut: dayjs(rawReservation.dateDebut, DATE_TIME_FORMAT),
      dateFin: dayjs(rawReservation.dateFin, DATE_TIME_FORMAT),
    };
  }

  private convertReservationToReservationRawValue(
    reservation: IReservation | (Partial<NewReservation> & ReservationFormDefaults),
  ): ReservationFormRawValue | PartialWithRequiredKeyOf<NewReservationFormRawValue> {
    return {
      ...reservation,
      dateDebut: reservation.dateDebut ? reservation.dateDebut.format(DATE_TIME_FORMAT) : undefined,
      dateFin: reservation.dateFin ? reservation.dateFin.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
