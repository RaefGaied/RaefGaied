import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPaiement } from '../paiement.model';
import { PaiementService } from '../service/paiement.service';
import { PaiementFormService, PaiementFormGroup } from './paiement-form.service';

@Component({
  standalone: true,
  selector: 'jhi-paiement-update',
  templateUrl: './paiement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PaiementUpdateComponent implements OnInit {
  isSaving = false;
  paiement: IPaiement | null = null;

  protected paiementService = inject(PaiementService);
  protected paiementFormService = inject(PaiementFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PaiementFormGroup = this.paiementFormService.createPaiementFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiement }) => {
      this.paiement = paiement;
      if (paiement) {
        this.updateForm(paiement);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paiement = this.paiementFormService.getPaiement(this.editForm);
    if (paiement.id !== null) {
      this.subscribeToSaveResponse(this.paiementService.update(paiement));
    } else {
      this.subscribeToSaveResponse(this.paiementService.create(paiement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaiement>>): void {
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

  protected updateForm(paiement: IPaiement): void {
    this.paiement = paiement;
    this.paiementFormService.resetForm(this.editForm, paiement);
  }
}
