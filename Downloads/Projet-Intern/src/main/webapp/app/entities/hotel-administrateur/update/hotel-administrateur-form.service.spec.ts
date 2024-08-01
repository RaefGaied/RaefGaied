import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../hotel-administrateur.test-samples';

import { HotelAdministrateurFormService } from './hotel-administrateur-form.service';

describe('HotelAdministrateur Form Service', () => {
  let service: HotelAdministrateurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelAdministrateurFormService);
  });

  describe('Service methods', () => {
    describe('createHotelAdministrateurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHotelAdministrateurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            email: expect.any(Object),
            motDePasse: expect.any(Object),
          }),
        );
      });

      it('passing IHotelAdministrateur should create a new form with FormGroup', () => {
        const formGroup = service.createHotelAdministrateurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            email: expect.any(Object),
            motDePasse: expect.any(Object),
          }),
        );
      });
    });

    describe('getHotelAdministrateur', () => {
      it('should return NewHotelAdministrateur for default HotelAdministrateur initial value', () => {
        const formGroup = service.createHotelAdministrateurFormGroup(sampleWithNewData);

        const hotelAdministrateur = service.getHotelAdministrateur(formGroup) as any;

        expect(hotelAdministrateur).toMatchObject(sampleWithNewData);
      });

      it('should return NewHotelAdministrateur for empty HotelAdministrateur initial value', () => {
        const formGroup = service.createHotelAdministrateurFormGroup();

        const hotelAdministrateur = service.getHotelAdministrateur(formGroup) as any;

        expect(hotelAdministrateur).toMatchObject({});
      });

      it('should return IHotelAdministrateur', () => {
        const formGroup = service.createHotelAdministrateurFormGroup(sampleWithRequiredData);

        const hotelAdministrateur = service.getHotelAdministrateur(formGroup) as any;

        expect(hotelAdministrateur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHotelAdministrateur should not enable id FormControl', () => {
        const formGroup = service.createHotelAdministrateurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHotelAdministrateur should disable id FormControl', () => {
        const formGroup = service.createHotelAdministrateurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
