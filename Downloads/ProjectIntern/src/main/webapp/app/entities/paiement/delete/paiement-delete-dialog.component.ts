import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPaiement } from '../paiement.model';
import { PaiementService } from '../service/paiement.service';

@Component({
  standalone: true,
  templateUrl: './paiement-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PaiementDeleteDialogComponent {
  paiement?: IPaiement;

  protected paiementService = inject(PaiementService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paiementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
