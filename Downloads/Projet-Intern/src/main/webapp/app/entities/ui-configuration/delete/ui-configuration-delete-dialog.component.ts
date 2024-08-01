import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IUIConfiguration } from '../ui-configuration.model';
import { UIConfigurationService } from '../service/ui-configuration.service';

@Component({
  standalone: true,
  templateUrl: './ui-configuration-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class UIConfigurationDeleteDialogComponent {
  uIConfiguration?: IUIConfiguration;

  protected uIConfigurationService = inject(UIConfigurationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uIConfigurationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
