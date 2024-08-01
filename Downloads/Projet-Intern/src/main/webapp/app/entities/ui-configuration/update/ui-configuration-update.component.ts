import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { IUIConfiguration } from '../ui-configuration.model';
import { UIConfigurationService } from '../service/ui-configuration.service';
import { UIConfigurationFormService, UIConfigurationFormGroup } from './ui-configuration-form.service';

@Component({
  standalone: true,
  selector: 'jhi-ui-configuration-update',
  templateUrl: './ui-configuration-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UIConfigurationUpdateComponent implements OnInit {
  isSaving = false;
  uIConfiguration: IUIConfiguration | null = null;

  hotelsSharedCollection: IHotel[] = [];

  protected uIConfigurationService = inject(UIConfigurationService);
  protected uIConfigurationFormService = inject(UIConfigurationFormService);
  protected hotelService = inject(HotelService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UIConfigurationFormGroup = this.uIConfigurationFormService.createUIConfigurationFormGroup();

  compareHotel = (o1: IHotel | null, o2: IHotel | null): boolean => this.hotelService.compareHotel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uIConfiguration }) => {
      this.uIConfiguration = uIConfiguration;
      if (uIConfiguration) {
        this.updateForm(uIConfiguration);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const uIConfiguration = this.uIConfigurationFormService.getUIConfiguration(this.editForm);
    if (uIConfiguration.id !== null) {
      this.subscribeToSaveResponse(this.uIConfigurationService.update(uIConfiguration));
    } else {
      this.subscribeToSaveResponse(this.uIConfigurationService.create(uIConfiguration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUIConfiguration>>): void {
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

  protected updateForm(uIConfiguration: IUIConfiguration): void {
    this.uIConfiguration = uIConfiguration;
    this.uIConfigurationFormService.resetForm(this.editForm, uIConfiguration);

    this.hotelsSharedCollection = this.hotelService.addHotelToCollectionIfMissing<IHotel>(
      this.hotelsSharedCollection,
      uIConfiguration.hotel,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.hotelService
      .query()
      .pipe(map((res: HttpResponse<IHotel[]>) => res.body ?? []))
      .pipe(map((hotels: IHotel[]) => this.hotelService.addHotelToCollectionIfMissing<IHotel>(hotels, this.uIConfiguration?.hotel)))
      .subscribe((hotels: IHotel[]) => (this.hotelsSharedCollection = hotels));
  }
}
