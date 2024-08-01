import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAuthentificationConfiguration } from '../authentification-configuration.model';

@Component({
  standalone: true,
  selector: 'jhi-authentification-configuration-detail',
  templateUrl: './authentification-configuration-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AuthentificationConfigurationDetailComponent {
  authentificationConfiguration = input<IAuthentificationConfiguration | null>(null);

  previousState(): void {
    window.history.back();
  }
}
