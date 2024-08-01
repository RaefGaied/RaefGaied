import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../hotel.test-samples';

import { HotelFormService } from './hotel-form.service';

describe('Hotel Form Service', () => {
  let service: HotelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelFormService);
  });

  describe('Service methods', () => {
    describe('createHotelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHotelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            address: expect.any(Object),
            numTel: expect.any(Object),
            pays: expect.any(Object),
            ville: expect.any(Object),
            vueS: expect.any(Object),
            capacity: expect.any(Object),
            notation: expect.any(Object),
            lienUnique: expect.any(Object),
            hotelAdministrateur: expect.any(Object),
          }),
        );
      });

      it('passing IHotel should create a new form with FormGroup', () => {
        const formGroup = service.createHotelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            address: expect.any(Object),
            numTel: expect.any(Object),
            pays: expect.any(Object),
            ville: expect.any(Object),
            vueS: expect.any(Object),
            capacity: expect.any(Object),
            notation: expect.any(Object),
            lienUnique: expect.any(Object),
            hotelAdministrateur: expect.any(Object),
          }),
        );
      });
    });

    describe('getHotel', () => {
      it('should return NewHotel for default Hotel initial value', () => {
        const formGroup = service.createHotelFormGroup(sampleWithNewData);

        const hotel = service.getHotel(formGroup) as any;

        expect(hotel).toMatchObject(sampleWithNewData);
      });

      it('should return NewHotel for empty Hotel initial value', () => {
        const formGroup = service.createHotelFormGroup();

        const hotel = service.getHotel(formGroup) as any;

        expect(hotel).toMatchObject({});
      });

      it('should return IHotel', () => {
        const formGroup = service.createHotelFormGroup(sampleWithRequiredData);

        const hotel = service.getHotel(formGroup) as any;

        expect(hotel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHotel should not enable id FormControl', () => {
        const formGroup = service.createHotelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHotel should disable id FormControl', () => {
        const formGroup = service.createHotelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
