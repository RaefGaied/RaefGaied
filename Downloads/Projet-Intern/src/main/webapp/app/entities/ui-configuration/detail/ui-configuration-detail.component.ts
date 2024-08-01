import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IUIConfiguration } from '../ui-configuration.model';

@Component({
  standalone: true,
  selector: 'jhi-ui-configuration-detail',
  templateUrl: './ui-configuration-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class UIConfigurationDetailComponent {
  uIConfiguration = input<IUIConfiguration | null>(null);

  previousState(): void {
    window.history.back();
  }
}
