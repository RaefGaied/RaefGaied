import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { Statut } from 'app/entities/enumerations/statut.model';
import { ReservationService } from '../service/reservation.service';
import { IReservation } from '../reservation.model';
import { ReservationFormService, ReservationFormGroup } from './reservation-form.service';

@Component({
  standalone: true,
  selector: 'jhi-reservation-update',
  templateUrl: './reservation-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ReservationUpdateComponent implements OnInit {
  isSaving = false;
  reservation: IReservation | null = null;
  statutValues = Object.keys(Statut);

  hotelsSharedCollection: IHotel[] = [];
  usersSharedCollection: IUser[] = [];

  protected reservationService = inject(ReservationService);
  protected reservationFormService = inject(ReservationFormService);
  protected hotelService = inject(HotelService);
  protected userService = inject(UserService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ReservationFormGroup = this.reservationFormService.createReservationFormGroup();

  compareHotel = (o1: IHotel | null, o2: IHotel | null): boolean => this.hotelService.compareHotel(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservation }) => {
      this.reservation = reservation;
      if (reservation) {
        this.updateForm(reservation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reservation = this.reservationFormService.getReservation(this.editForm);
    if (reservation.id !== null) {
      this.subscribeToSaveResponse(this.reservationService.update(reservation));
    } else {
      this.subscribeToSaveResponse(this.reservationService.create(reservation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservation>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(reservation: IReservation): void {
    this.reservation = reservation;
    this.reservationFormService.resetForm(this.editForm, reservation);

    this.hotelsSharedCollection = this.hotelService.addHotelToCollectionIfMissing<IHotel>(this.hotelsSharedCollection, reservation.hotel);
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, reservation.user);
  }

  protected loadRelationshipsOptions(): void {
    this.hotelService
      .query()
      .pipe(map((res: HttpResponse<IHotel[]>) => res.body ?? []))
      .pipe(map((hotels: IHotel[]) => this.hotelService.addHotelToCollectionIfMissing<IHotel>(hotels, this.reservation?.hotel)))
      .subscribe((hotels: IHotel[]) => (this.hotelsSharedCollection = hotels));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.reservation?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
