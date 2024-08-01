jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { HotelAdministrateurService } from '../service/hotel-administrateur.service';

import { HotelAdministrateurDeleteDialogComponent } from './hotel-administrateur-delete-dialog.component';

describe('HotelAdministrateur Management Delete Component', () => {
  let comp: HotelAdministrateurDeleteDialogComponent;
  let fixture: ComponentFixture<HotelAdministrateurDeleteDialogComponent>;
  let service: HotelAdministrateurService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HotelAdministrateurDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(HotelAdministrateurDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HotelAdministrateurDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(HotelAdministrateurService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
