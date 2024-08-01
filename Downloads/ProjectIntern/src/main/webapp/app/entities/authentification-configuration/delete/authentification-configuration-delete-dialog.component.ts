import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAuthentificationConfiguration } from '../authentification-configuration.model';
import { AuthentificationConfigurationService } from '../service/authentification-configuration.service';

@Component({
  standalone: true,
  templateUrl: './authentification-configuration-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AuthentificationConfigurationDeleteDialogComponent {
  authentificationConfiguration?: IAuthentificationConfiguration;

  protected authentificationConfigurationService = inject(AuthentificationConfigurationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.authentificationConfigurationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
