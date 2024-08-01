import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { HotelAdministrateurService } from '../service/hotel-administrateur.service';
import { IHotelAdministrateur } from '../hotel-administrateur.model';
import { HotelAdministrateurFormService } from './hotel-administrateur-form.service';

import { HotelAdministrateurUpdateComponent } from './hotel-administrateur-update.component';

describe('HotelAdministrateur Management Update Component', () => {
  let comp: HotelAdministrateurUpdateComponent;
  let fixture: ComponentFixture<HotelAdministrateurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hotelAdministrateurFormService: HotelAdministrateurFormService;
  let hotelAdministrateurService: HotelAdministrateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HotelAdministrateurUpdateComponent],
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
      .overrideTemplate(HotelAdministrateurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HotelAdministrateurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hotelAdministrateurFormService = TestBed.inject(HotelAdministrateurFormService);
    hotelAdministrateurService = TestBed.inject(HotelAdministrateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const hotelAdministrateur: IHotelAdministrateur = { id: 456 };

      activatedRoute.data = of({ hotelAdministrateur });
      comp.ngOnInit();

      expect(comp.hotelAdministrateur).toEqual(hotelAdministrateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelAdministrateur>>();
      const hotelAdministrateur = { id: 123 };
      jest.spyOn(hotelAdministrateurFormService, 'getHotelAdministrateur').mockReturnValue(hotelAdministrateur);
      jest.spyOn(hotelAdministrateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelAdministrateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotelAdministrateur }));
      saveSubject.complete();

      // THEN
      expect(hotelAdministrateurFormService.getHotelAdministrateur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hotelAdministrateurService.update).toHaveBeenCalledWith(expect.objectContaining(hotelAdministrateur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelAdministrateur>>();
      const hotelAdministrateur = { id: 123 };
      jest.spyOn(hotelAdministrateurFormService, 'getHotelAdministrateur').mockReturnValue({ id: null });
      jest.spyOn(hotelAdministrateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelAdministrateur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotelAdministrateur }));
      saveSubject.complete();

      // THEN
      expect(hotelAdministrateurFormService.getHotelAdministrateur).toHaveBeenCalled();
      expect(hotelAdministrateurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelAdministrateur>>();
      const hotelAdministrateur = { id: 123 };
      jest.spyOn(hotelAdministrateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelAdministrateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hotelAdministrateurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
