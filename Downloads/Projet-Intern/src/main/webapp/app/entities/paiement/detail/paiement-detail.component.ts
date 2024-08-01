import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPaiement } from '../paiement.model';

@Component({
  standalone: true,
  selector: 'jhi-paiement-detail',
  templateUrl: './paiement-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PaiementDetailComponent {
  paiement = input<IPaiement | null>(null);

  previousState(): void {
    window.history.back();
  }
}
