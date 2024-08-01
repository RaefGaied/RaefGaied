import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHotelAdministrateur } from '../hotel-administrateur.model';
import { HotelAdministrateurService } from '../service/hotel-administrateur.service';
import { HotelAdministrateurFormService, HotelAdministrateurFormGroup } from './hotel-administrateur-form.service';

@Component({
  standalone: true,
  selector: 'jhi-hotel-administrateur-update',
  templateUrl: './hotel-administrateur-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class HotelAdministrateurUpdateComponent implements OnInit {
  isSaving = false;
  hotelAdministrateur: IHotelAdministrateur | null = null;

  protected hotelAdministrateurService = inject(HotelAdministrateurService);
  protected hotelAdministrateurFormService = inject(HotelAdministrateurFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: HotelAdministrateurFormGroup = this.hotelAdministrateurFormService.createHotelAdministrateurFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelAdministrateur }) => {
      this.hotelAdministrateur = hotelAdministrateur;
      if (hotelAdministrateur) {
        this.updateForm(hotelAdministrateur);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotelAdministrateur = this.hotelAdministrateurFormService.getHotelAdministrateur(this.editForm);
    if (hotelAdministrateur.id !== null) {
      this.subscribeToSaveResponse(this.hotelAdministrateurService.update(hotelAdministrateur));
    } else {
      this.subscribeToSaveResponse(this.hotelAdministrateurService.create(hotelAdministrateur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotelAdministrateur>>): void {
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

  protected updateForm(hotelAdministrateur: IHotelAdministrateur): void {
    this.hotelAdministrateur = hotelAdministrateur;
    this.hotelAdministrateurFormService.resetForm(this.editForm, hotelAdministrateur);
  }
}
