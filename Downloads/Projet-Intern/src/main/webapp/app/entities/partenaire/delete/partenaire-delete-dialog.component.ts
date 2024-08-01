import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPartenaire } from '../partenaire.model';
import { PartenaireService } from '../service/partenaire.service';

@Component({
  standalone: true,
  templateUrl: './partenaire-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PartenaireDeleteDialogComponent {
  partenaire?: IPartenaire;

  protected partenaireService = inject(PartenaireService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partenaireService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
