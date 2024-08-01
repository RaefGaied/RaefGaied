import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IService } from '../service.model';
import { ServiceService } from '../service/service.service';

@Component({
  standalone: true,
  templateUrl: './service-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ServiceDeleteDialogComponent {
  service?: IService;

  protected serviceService = inject(ServiceService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
