import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IHotel } from '../hotel.model';

@Component({
  standalone: true,
  selector: 'jhi-hotel-detail',
  templateUrl: './hotel-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class HotelDetailComponent {
  hotel = input<IHotel | null>(null);

  previousState(): void {
    window.history.back();
  }
}
