import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPartenaire } from 'app/entities/partenaire/partenaire.model';
import { PartenaireService } from 'app/entities/partenaire/service/partenaire.service';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { IReservation } from 'app/entities/reservation/reservation.model';
import { ReservationService } from 'app/entities/reservation/service/reservation.service';
import { IService } from '../service.model';
import { ServiceService } from '../service/service.service';
import { ServiceFormService } from './service-form.service';

import { ServiceUpdateComponent } from './service-update.component';

describe('Service Management Update Component', () => {
  let comp: ServiceUpdateComponent;
  let fixture: ComponentFixture<ServiceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let serviceFormService: ServiceFormService;
  let serviceService: ServiceService;
  let partenaireService: PartenaireService;
  let hotelService: HotelService;
  let reservationService: ReservationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServiceUpdateComponent],
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
      .overrideTemplate(ServiceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServiceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    serviceFormService = TestBed.inject(ServiceFormService);
    serviceService = TestBed.inject(ServiceService);
    partenaireService = TestBed.inject(PartenaireService);
    hotelService = TestBed.inject(HotelService);
    reservationService = TestBed.inject(ReservationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Partenaire query and add missing value', () => {
      const service: IService = { id: 456 };
      const partenaire: IPartenaire = { id: 6413 };
      service.partenaire = partenaire;

      const partenaireCollection: IPartenaire[] = [{ id: 14954 }];
      jest.spyOn(partenaireService, 'query').mockReturnValue(of(new HttpResponse({ body: partenaireCollection })));
      const additionalPartenaires = [partenaire];
      const expectedCollection: IPartenaire[] = [...additionalPartenaires, ...partenaireCollection];
      jest.spyOn(partenaireService, 'addPartenaireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ service });
      comp.ngOnInit();

      expect(partenaireService.query).toHaveBeenCalled();
      expect(partenaireService.addPartenaireToCollectionIfMissing).toHaveBeenCalledWith(
        partenaireCollection,
        ...additionalPartenaires.map(expect.objectContaining),
      );
      expect(comp.partenairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Hotel query and add missing value', () => {
      const service: IService = { id: 456 };
      const hotel: IHotel = { id: 5834 };
      service.hotel = hotel;

      const hotelCollection: IHotel[] = [{ id: 5843 }];
      jest.spyOn(hotelService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelCollection })));
      const additionalHotels = [hotel];
      const expectedCollection: IHotel[] = [...additionalHotels, ...hotelCollection];
      jest.spyOn(hotelService, 'addHotelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ service });
      comp.ngOnInit();

      expect(hotelService.query).toHaveBeenCalled();
      expect(hotelService.addHotelToCollectionIfMissing).toHaveBeenCalledWith(
        hotelCollection,
        ...additionalHotels.map(expect.objectContaining),
      );
      expect(comp.hotelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Reservation query and add missing value', () => {
      const service: IService = { id: 456 };
      const reservation: IReservation = { id: 20281 };
      service.reservation = reservation;

      const reservationCollection: IReservation[] = [{ id: 22818 }];
      jest.spyOn(reservationService, 'query').mockReturnValue(of(new HttpResponse({ body: reservationCollection })));
      const additionalReservations = [reservation];
      const expectedCollection: IReservation[] = [...additionalReservations, ...reservationCollection];
      jest.spyOn(reservationService, 'addReservationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ service });
      comp.ngOnInit();

      expect(reservationService.query).toHaveBeenCalled();
      expect(reservationService.addReservationToCollectionIfMissing).toHaveBeenCalledWith(
        reservationCollection,
        ...additionalReservations.map(expect.objectContaining),
      );
      expect(comp.reservationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const service: IService = { id: 456 };
      const partenaire: IPartenaire = { id: 17160 };
      service.partenaire = partenaire;
      const hotel: IHotel = { id: 1825 };
      service.hotel = hotel;
      const reservation: IReservation = { id: 2164 };
      service.reservation = reservation;

      activatedRoute.data = of({ service });
      comp.ngOnInit();

      expect(comp.partenairesSharedCollection).toContain(partenaire);
      expect(comp.hotelsSharedCollection).toContain(hotel);
      expect(comp.reservationsSharedCollection).toContain(reservation);
      expect(comp.service).toEqual(service);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IService>>();
      const service = { id: 123 };
      jest.spyOn(serviceFormService, 'getService').mockReturnValue(service);
      jest.spyOn(serviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ service });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: service }));
      saveSubject.complete();

      // THEN
      expect(serviceFormService.getService).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(serviceService.update).toHaveBeenCalledWith(expect.objectContaining(service));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IService>>();
      const service = { id: 123 };
      jest.spyOn(serviceFormService, 'getService').mockReturnValue({ id: null });
      jest.spyOn(serviceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ service: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: service }));
      saveSubject.complete();

      // THEN
      expect(serviceFormService.getService).toHaveBeenCalled();
      expect(serviceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IService>>();
      const service = { id: 123 };
      jest.spyOn(serviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ service });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(serviceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePartenaire', () => {
      it('Should forward to partenaireService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(partenaireService, 'comparePartenaire');
        comp.comparePartenaire(entity, entity2);
        expect(partenaireService.comparePartenaire).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareHotel', () => {
      it('Should forward to hotelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(hotelService, 'compareHotel');
        comp.compareHotel(entity, entity2);
        expect(hotelService.compareHotel).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareReservation', () => {
      it('Should forward to reservationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(reservationService, 'compareReservation');
        comp.compareReservation(entity, entity2);
        expect(reservationService.compareReservation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
