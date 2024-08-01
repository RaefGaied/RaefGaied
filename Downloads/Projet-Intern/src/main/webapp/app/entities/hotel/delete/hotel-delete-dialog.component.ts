import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IHotel } from '../hotel.model';
import { HotelService } from '../service/hotel.service';

@Component({
  standalone: true,
  templateUrl: './hotel-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class HotelDeleteDialogComponent {
  hotel?: IHotel;

  protected hotelService = inject(HotelService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
