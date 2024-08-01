import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IHotelAdministrateur } from 'app/entities/hotel-administrateur/hotel-administrateur.model';
import { HotelAdministrateurService } from 'app/entities/hotel-administrateur/service/hotel-administrateur.service';
import { HotelService } from '../service/hotel.service';
import { IHotel } from '../hotel.model';
import { HotelFormService } from './hotel-form.service';

import { HotelUpdateComponent } from './hotel-update.component';

describe('Hotel Management Update Component', () => {
  let comp: HotelUpdateComponent;
  let fixture: ComponentFixture<HotelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hotelFormService: HotelFormService;
  let hotelService: HotelService;
  let hotelAdministrateurService: HotelAdministrateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HotelUpdateComponent],
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
      .overrideTemplate(HotelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HotelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hotelFormService = TestBed.inject(HotelFormService);
    hotelService = TestBed.inject(HotelService);
    hotelAdministrateurService = TestBed.inject(HotelAdministrateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call HotelAdministrateur query and add missing value', () => {
      const hotel: IHotel = { id: 456 };
      const hotelAdministrateur: IHotelAdministrateur = { id: 22916 };
      hotel.hotelAdministrateur = hotelAdministrateur;

      const hotelAdministrateurCollection: IHotelAdministrateur[] = [{ id: 6313 }];
      jest.spyOn(hotelAdministrateurService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelAdministrateurCollection })));
      const additionalHotelAdministrateurs = [hotelAdministrateur];
      const expectedCollection: IHotelAdministrateur[] = [...additionalHotelAdministrateurs, ...hotelAdministrateurCollection];
      jest.spyOn(hotelAdministrateurService, 'addHotelAdministrateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      expect(hotelAdministrateurService.query).toHaveBeenCalled();
      expect(hotelAdministrateurService.addHotelAdministrateurToCollectionIfMissing).toHaveBeenCalledWith(
        hotelAdministrateurCollection,
        ...additionalHotelAdministrateurs.map(expect.objectContaining),
      );
      expect(comp.hotelAdministrateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hotel: IHotel = { id: 456 };
      const hotelAdministrateur: IHotelAdministrateur = { id: 22898 };
      hotel.hotelAdministrateur = hotelAdministrateur;

      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      expect(comp.hotelAdministrateursSharedCollection).toContain(hotelAdministrateur);
      expect(comp.hotel).toEqual(hotel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotel>>();
      const hotel = { id: 123 };
      jest.spyOn(hotelFormService, 'getHotel').mockReturnValue(hotel);
      jest.spyOn(hotelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotel }));
      saveSubject.complete();

      // THEN
      expect(hotelFormService.getHotel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hotelService.update).toHaveBeenCalledWith(expect.objectContaining(hotel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotel>>();
      const hotel = { id: 123 };
      jest.spyOn(hotelFormService, 'getHotel').mockReturnValue({ id: null });
      jest.spyOn(hotelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotel }));
      saveSubject.complete();

      // THEN
      expect(hotelFormService.getHotel).toHaveBeenCalled();
      expect(hotelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotel>>();
      const hotel = { id: 123 };
      jest.spyOn(hotelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hotelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareHotelAdministrateur', () => {
      it('Should forward to hotelAdministrateurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(hotelAdministrateurService, 'compareHotelAdministrateur');
        comp.compareHotelAdministrateur(entity, entity2);
        expect(hotelAdministrateurService.compareHotelAdministrateur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
