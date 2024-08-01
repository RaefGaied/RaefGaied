import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IHotelAdministrateur } from '../hotel-administrateur.model';

@Component({
  standalone: true,
  selector: 'jhi-hotel-administrateur-detail',
  templateUrl: './hotel-administrateur-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class HotelAdministrateurDetailComponent {
  hotelAdministrateur = input<IHotelAdministrateur | null>(null);

  previousState(): void {
    window.history.back();
  }
}
