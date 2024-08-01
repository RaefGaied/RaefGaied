import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { AuthentificationConfigurationService } from '../service/authentification-configuration.service';
import { IAuthentificationConfiguration } from '../authentification-configuration.model';
import { AuthentificationConfigurationFormService } from './authentification-configuration-form.service';

import { AuthentificationConfigurationUpdateComponent } from './authentification-configuration-update.component';

describe('AuthentificationConfiguration Management Update Component', () => {
  let comp: AuthentificationConfigurationUpdateComponent;
  let fixture: ComponentFixture<AuthentificationConfigurationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let authentificationConfigurationFormService: AuthentificationConfigurationFormService;
  let authentificationConfigurationService: AuthentificationConfigurationService;
  let hotelService: HotelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AuthentificationConfigurationUpdateComponent],
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
      .overrideTemplate(AuthentificationConfigurationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuthentificationConfigurationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    authentificationConfigurationFormService = TestBed.inject(AuthentificationConfigurationFormService);
    authentificationConfigurationService = TestBed.inject(AuthentificationConfigurationService);
    hotelService = TestBed.inject(HotelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Hotel query and add missing value', () => {
      const authentificationConfiguration: IAuthentificationConfiguration = { id: 456 };
      const hotel: IHotel = { id: 834 };
      authentificationConfiguration.hotel = hotel;

      const hotelCollection: IHotel[] = [{ id: 4771 }];
      jest.spyOn(hotelService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelCollection })));
      const additionalHotels = [hotel];
      const expectedCollection: IHotel[] = [...additionalHotels, ...hotelCollection];
      jest.spyOn(hotelService, 'addHotelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ authentificationConfiguration });
      comp.ngOnInit();

      expect(hotelService.query).toHaveBeenCalled();
      expect(hotelService.addHotelToCollectionIfMissing).toHaveBeenCalledWith(
        hotelCollection,
        ...additionalHotels.map(expect.objectContaining),
      );
      expect(comp.hotelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const authentificationConfiguration: IAuthentificationConfiguration = { id: 456 };
      const hotel: IHotel = { id: 19667 };
      authentificationConfiguration.hotel = hotel;

      activatedRoute.data = of({ authentificationConfiguration });
      comp.ngOnInit();

      expect(comp.hotelsSharedCollection).toContain(hotel);
      expect(comp.authentificationConfiguration).toEqual(authentificationConfiguration);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthentificationConfiguration>>();
      const authentificationConfiguration = { id: 123 };
      jest
        .spyOn(authentificationConfigurationFormService, 'getAuthentificationConfiguration')
        .mockReturnValue(authentificationConfiguration);
      jest.spyOn(authentificationConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authentificationConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authentificationConfiguration }));
      saveSubject.complete();

      // THEN
      expect(authentificationConfigurationFormService.getAuthentificationConfiguration).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(authentificationConfigurationService.update).toHaveBeenCalledWith(expect.objectContaining(authentificationConfiguration));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthentificationConfiguration>>();
      const authentificationConfiguration = { id: 123 };
      jest.spyOn(authentificationConfigurationFormService, 'getAuthentificationConfiguration').mockReturnValue({ id: null });
      jest.spyOn(authentificationConfigurationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authentificationConfiguration: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authentificationConfiguration }));
      saveSubject.complete();

      // THEN
      expect(authentificationConfigurationFormService.getAuthentificationConfiguration).toHaveBeenCalled();
      expect(authentificationConfigurationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthentificationConfiguration>>();
      const authentificationConfiguration = { id: 123 };
      jest.spyOn(authentificationConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authentificationConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(authentificationConfigurationService.update).toHaveBeenCalled();
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
