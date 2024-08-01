import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IHotelAdministrateur } from '../hotel-administrateur.model';
import { HotelAdministrateurService } from '../service/hotel-administrateur.service';

@Component({
  standalone: true,
  templateUrl: './hotel-administrateur-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class HotelAdministrateurDeleteDialogComponent {
  hotelAdministrateur?: IHotelAdministrateur;

  protected hotelAdministrateurService = inject(HotelAdministrateurService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelAdministrateurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
