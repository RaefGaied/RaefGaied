jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EmailTemplateConfigurationService } from '../service/email-template-configuration.service';

import { EmailTemplateConfigurationDeleteDialogComponent } from './email-template-configuration-delete-dialog.component';

describe('EmailTemplateConfiguration Management Delete Component', () => {
  let comp: EmailTemplateConfigurationDeleteDialogComponent;
  let fixture: ComponentFixture<EmailTemplateConfigurationDeleteDialogComponent>;
  let service: EmailTemplateConfigurationService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmailTemplateConfigurationDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(EmailTemplateConfigurationDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmailTemplateConfigurationDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EmailTemplateConfigurationService);
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
