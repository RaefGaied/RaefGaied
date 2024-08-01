import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHotelAdministrateur } from 'app/entities/hotel-administrateur/hotel-administrateur.model';
import { HotelAdministrateurService } from 'app/entities/hotel-administrateur/service/hotel-administrateur.service';
import { IHotel } from '../hotel.model';
import { HotelService } from '../service/hotel.service';
import { HotelFormService, HotelFormGroup } from './hotel-form.service';

@Component({
  standalone: true,
  selector: 'jhi-hotel-update',
  templateUrl: './hotel-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class HotelUpdateComponent implements OnInit {
  isSaving = false;
  hotel: IHotel | null = null;

  hotelAdministrateursSharedCollection: IHotelAdministrateur[] = [];

  protected hotelService = inject(HotelService);
  protected hotelFormService = inject(HotelFormService);
  protected hotelAdministrateurService = inject(HotelAdministrateurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: HotelFormGroup = this.hotelFormService.createHotelFormGroup();

  compareHotelAdministrateur = (o1: IHotelAdministrateur | null, o2: IHotelAdministrateur | null): boolean =>
    this.hotelAdministrateurService.compareHotelAdministrateur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotel }) => {
      this.hotel = hotel;
      if (hotel) {
        this.updateForm(hotel);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotel = this.hotelFormService.getHotel(this.editForm);
    if (hotel.id !== null) {
      this.subscribeToSaveResponse(this.hotelService.update(hotel));
    } else {
      this.subscribeToSaveResponse(this.hotelService.create(hotel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotel>>): void {
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

  protected updateForm(hotel: IHotel): void {
    this.hotel = hotel;
    this.hotelFormService.resetForm(this.editForm, hotel);

    this.hotelAdministrateursSharedCollection =
      this.hotelAdministrateurService.addHotelAdministrateurToCollectionIfMissing<IHotelAdministrateur>(
        this.hotelAdministrateursSharedCollection,
        hotel.hotelAdministrateur,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.hotelAdministrateurService
      .query()
      .pipe(map((res: HttpResponse<IHotelAdministrateur[]>) => res.body ?? []))
      .pipe(
        map((hotelAdministrateurs: IHotelAdministrateur[]) =>
          this.hotelAdministrateurService.addHotelAdministrateurToCollectionIfMissing<IHotelAdministrateur>(
            hotelAdministrateurs,
            this.hotel?.hotelAdministrateur,
          ),
        ),
      )
      .subscribe((hotelAdministrateurs: IHotelAdministrateur[]) => (this.hotelAdministrateursSharedCollection = hotelAdministrateurs));
  }
}
