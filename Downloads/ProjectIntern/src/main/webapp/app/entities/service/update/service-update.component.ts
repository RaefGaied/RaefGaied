import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPartenaire } from 'app/entities/partenaire/partenaire.model';
import { PartenaireService } from 'app/entities/partenaire/service/partenaire.service';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { IReservation } from 'app/entities/reservation/reservation.model';
import { ReservationService } from 'app/entities/reservation/service/reservation.service';
import { ServiceService } from '../service/service.service';
import { IService } from '../service.model';
import { ServiceFormService, ServiceFormGroup } from './service-form.service';

@Component({
  standalone: true,
  selector: 'jhi-service-update',
  templateUrl: './service-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ServiceUpdateComponent implements OnInit {
  isSaving = false;
  service: IService | null = null;

  partenairesSharedCollection: IPartenaire[] = [];
  hotelsSharedCollection: IHotel[] = [];
  reservationsSharedCollection: IReservation[] = [];

  protected serviceService = inject(ServiceService);
  protected serviceFormService = inject(ServiceFormService);
  protected partenaireService = inject(PartenaireService);
  protected hotelService = inject(HotelService);
  protected reservationService = inject(ReservationService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ServiceFormGroup = this.serviceFormService.createServiceFormGroup();

  comparePartenaire = (o1: IPartenaire | null, o2: IPartenaire | null): boolean => this.partenaireService.comparePartenaire(o1, o2);

  compareHotel = (o1: IHotel | null, o2: IHotel | null): boolean => this.hotelService.compareHotel(o1, o2);

  compareReservation = (o1: IReservation | null, o2: IReservation | null): boolean => this.reservationService.compareReservation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ service }) => {
      this.service = service;
      if (service) {
        this.updateForm(service);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const service = this.serviceFormService.getService(this.editForm);
    if (service.id !== null) {
      this.subscribeToSaveResponse(this.serviceService.update(service));
    } else {
      this.subscribeToSaveResponse(this.serviceService.create(service));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IService>>): void {
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

  protected updateForm(service: IService): void {
    this.service = service;
    this.serviceFormService.resetForm(this.editForm, service);

    this.partenairesSharedCollection = this.partenaireService.addPartenaireToCollectionIfMissing<IPartenaire>(
      this.partenairesSharedCollection,
      service.partenaire,
    );
    this.hotelsSharedCollection = this.hotelService.addHotelToCollectionIfMissing<IHotel>(this.hotelsSharedCollection, service.hotel);
    this.reservationsSharedCollection = this.reservationService.addReservationToCollectionIfMissing<IReservation>(
      this.reservationsSharedCollection,
      service.reservation,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.partenaireService
      .query()
      .pipe(map((res: HttpResponse<IPartenaire[]>) => res.body ?? []))
      .pipe(
        map((partenaires: IPartenaire[]) =>
          this.partenaireService.addPartenaireToCollectionIfMissing<IPartenaire>(partenaires, this.service?.partenaire),
        ),
      )
      .subscribe((partenaires: IPartenaire[]) => (this.partenairesSharedCollection = partenaires));

    this.hotelService
      .query()
      .pipe(map((res: HttpResponse<IHotel[]>) => res.body ?? []))
      .pipe(map((hotels: IHotel[]) => this.hotelService.addHotelToCollectionIfMissing<IHotel>(hotels, this.service?.hotel)))
      .subscribe((hotels: IHotel[]) => (this.hotelsSharedCollection = hotels));

    this.reservationService
      .query()
      .pipe(map((res: HttpResponse<IReservation[]>) => res.body ?? []))
      .pipe(
        map((reservations: IReservation[]) =>
          this.reservationService.addReservationToCollectionIfMissing<IReservation>(reservations, this.service?.reservation),
        ),
      )
      .subscribe((reservations: IReservation[]) => (this.reservationsSharedCollection = reservations));
  }
}
