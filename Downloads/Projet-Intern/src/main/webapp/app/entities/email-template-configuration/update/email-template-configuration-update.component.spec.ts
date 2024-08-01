import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { EmailTemplateConfigurationService } from '../service/email-template-configuration.service';
import { IEmailTemplateConfiguration } from '../email-template-configuration.model';
import { EmailTemplateConfigurationFormService } from './email-template-configuration-form.service';

import { EmailTemplateConfigurationUpdateComponent } from './email-template-configuration-update.component';

describe('EmailTemplateConfiguration Management Update Component', () => {
  let comp: EmailTemplateConfigurationUpdateComponent;
  let fixture: ComponentFixture<EmailTemplateConfigurationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emailTemplateConfigurationFormService: EmailTemplateConfigurationFormService;
  let emailTemplateConfigurationService: EmailTemplateConfigurationService;
  let hotelService: HotelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmailTemplateConfigurationUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EmailTemplateConfigurationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailTemplateConfigurationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emailTemplateConfigurationFormService = TestBed.inject(EmailTemplateConfigurationFormService);
    emailTemplateConfigurationService = TestBed.inject(EmailTemplateConfigurationService);
    hotelService = TestBed.inject(HotelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Hotel query and add missing value', () => {
      const emailTemplateConfiguration: IEmailTemplateConfiguration = { id: 456 };
      const hotel: IHotel = { id: 12880 };
      emailTemplateConfiguration.hotel = hotel;

      const hotelCollection: IHotel[] = [{ id: 31052 }];
      jest.spyOn(hotelService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelCollection })));
      const additionalHotels = [hotel];
      const expectedCollection: IHotel[] = [...additionalHotels, ...hotelCollection];
      jest.spyOn(hotelService, 'addHotelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ emailTemplateConfiguration });
      comp.ngOnInit();

      expect(hotelService.query).toHaveBeenCalled();
      expect(hotelService.addHotelToCollectionIfMissing).toHaveBeenCalledWith(
        hotelCollection,
        ...additionalHotels.map(expect.objectContaining),
      );
      expect(comp.hotelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const emailTemplateConfiguration: IEmailTemplateConfiguration = { id: 456 };
      const hotel: IHotel = { id: 4149 };
      emailTemplateConfiguration.hotel = hotel;

      activatedRoute.data = of({ emailTemplateConfiguration });
      comp.ngOnInit();

      expect(comp.hotelsSharedCollection).toContain(hotel);
      expect(comp.emailTemplateConfiguration).toEqual(emailTemplateConfiguration);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailTemplateConfiguration>>();
      const emailTemplateConfiguration = { id: 123 };
      jest.spyOn(emailTemplateConfigurationFormService, 'getEmailTemplateConfiguration').mockReturnValue(emailTemplateConfiguration);
      jest.spyOn(emailTemplateConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailTemplateConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailTemplateConfiguration }));
      saveSubject.complete();

      // THEN
      expect(emailTemplateConfigurationFormService.getEmailTemplateConfiguration).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(emailTemplateConfigurationService.update).toHaveBeenCalledWith(expect.objectContaining(emailTemplateConfiguration));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailTemplateConfiguration>>();
      const emailTemplateConfiguration = { id: 123 };
      jest.spyOn(emailTemplateConfigurationFormService, 'getEmailTemplateConfiguration').mockReturnValue({ id: null });
      jest.spyOn(emailTemplateConfigurationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailTemplateConfiguration: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailTemplateConfiguration }));
      saveSubject.complete();

      // THEN
      expect(emailTemplateConfigurationFormService.getEmailTemplateConfiguration).toHaveBeenCalled();
      expect(emailTemplateConfigurationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailTemplateConfiguration>>();
      const emailTemplateConfiguration = { id: 123 };
      jest.spyOn(emailTemplateConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailTemplateConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emailTemplateConfigurationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareHotel', () => {
      it('Should forward to hotelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(hotelService, 'compareHotel');
        comp.compareHotel(entity, entity2);
        expect(hotelService.compareHotel).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
