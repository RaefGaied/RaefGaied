import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPartenaire } from '../partenaire.model';

@Component({
  standalone: true,
  selector: 'jhi-partenaire-detail',
  templateUrl: './partenaire-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PartenaireDetailComponent {
  partenaire = input<IPartenaire | null>(null);

  previousState(): void {
    window.history.back();
  }
}
