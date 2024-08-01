import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { UIConfigurationService } from '../service/ui-configuration.service';
import { IUIConfiguration } from '../ui-configuration.model';
import { UIConfigurationFormService } from './ui-configuration-form.service';

import { UIConfigurationUpdateComponent } from './ui-configuration-update.component';

describe('UIConfiguration Management Update Component', () => {
  let comp: UIConfigurationUpdateComponent;
  let fixture: ComponentFixture<UIConfigurationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let uIConfigurationFormService: UIConfigurationFormService;
  let uIConfigurationService: UIConfigurationService;
  let hotelService: HotelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UIConfigurationUpdateComponent],
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
      .overrideTemplate(UIConfigurationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UIConfigurationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    uIConfigurationFormService = TestBed.inject(UIConfigurationFormService);
    uIConfigurationService = TestBed.inject(UIConfigurationService);
    hotelService = TestBed.inject(HotelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Hotel query and add missing value', () => {
      const uIConfiguration: IUIConfiguration = { id: 456 };
      const hotel: IHotel = { id: 3653 };
      uIConfiguration.hotel = hotel;

      const hotelCollection: IHotel[] = [{ id: 24461 }];
      jest.spyOn(hotelService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelCollection })));
      const additionalHotels = [hotel];
      const expectedCollection: IHotel[] = [...additionalHotels, ...hotelCollection];
      jest.spyOn(hotelService, 'addHotelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ uIConfiguration });
      comp.ngOnInit();

      expect(hotelService.query).toHaveBeenCalled();
      expect(hotelService.addHotelToCollectionIfMissing).toHaveBeenCalledWith(
        hotelCollection,
        ...additionalHotels.map(expect.objectContaining),
      );
      expect(comp.hotelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const uIConfiguration: IUIConfiguration = { id: 456 };
      const hotel: IHotel = { id: 32259 };
      uIConfiguration.hotel = hotel;

      activatedRoute.data = of({ uIConfiguration });
      comp.ngOnInit();

      expect(comp.hotelsSharedCollection).toContain(hotel);
      expect(comp.uIConfiguration).toEqual(uIConfiguration);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUIConfiguration>>();
      const uIConfiguration = { id: 123 };
      jest.spyOn(uIConfigurationFormService, 'getUIConfiguration').mockReturnValue(uIConfiguration);
      jest.spyOn(uIConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ uIConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: uIConfiguration }));
      saveSubject.complete();

      // THEN
      expect(uIConfigurationFormService.getUIConfiguration).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(uIConfigurationService.update).toHaveBeenCalledWith(expect.objectContaining(uIConfiguration));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUIConfiguration>>();
      const uIConfiguration = { id: 123 };
      jest.spyOn(uIConfigurationFormService, 'getUIConfiguration').mockReturnValue({ id: null });
      jest.spyOn(uIConfigurationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ uIConfiguration: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: uIConfiguration }));
      saveSubject.complete();

      // THEN
      expect(uIConfigurationFormService.getUIConfiguration).toHaveBeenCalled();
      expect(uIConfigurationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUIConfiguration>>();
      const uIConfiguration = { id: 123 };
      jest.spyOn(uIConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ uIConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(uIConfigurationService.update).toHaveBeenCalled();
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
