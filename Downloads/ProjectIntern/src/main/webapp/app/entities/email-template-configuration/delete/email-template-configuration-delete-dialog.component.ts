import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEmailTemplateConfiguration } from '../email-template-configuration.model';
import { EmailTemplateConfigurationService } from '../service/email-template-configuration.service';

@Component({
  standalone: true,
  templateUrl: './email-template-configuration-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EmailTemplateConfigurationDeleteDialogComponent {
  emailTemplateConfiguration?: IEmailTemplateConfiguration;

  protected emailTemplateConfigurationService = inject(EmailTemplateConfigurationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emailTemplateConfigurationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
