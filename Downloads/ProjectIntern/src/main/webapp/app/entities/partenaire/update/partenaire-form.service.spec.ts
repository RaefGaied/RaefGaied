import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../partenaire.test-samples';

import { PartenaireFormService } from './partenaire-form.service';

describe('Partenaire Form Service', () => {
  let service: PartenaireFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PartenaireFormService);
  });

  describe('Service methods', () => {
    describe('createPartenaireFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPartenaireFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            nom: expect.any(Object),
            contact: expect.any(Object),
            adresse: expect.any(Object),
            typePartenaire: expect.any(Object),
            service: expect.any(Object),
          }),
        );
      });

      it('passing IPartenaire should create a new form with FormGroup', () => {
        const formGroup = service.createPartenaireFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            nom: expect.any(Object),
            contact: expect.any(Object),
            adresse: expect.any(Object),
            typePartenaire: expect.any(Object),
            service: expect.any(Object),
          }),
        );
      });
    });

    describe('getPartenaire', () => {
      it('should return NewPartenaire for default Partenaire initial value', () => {
        const formGroup = service.createPartenaireFormGroup(sampleWithNewData);

        const partenaire = service.getPartenaire(formGroup) as any;

        expect(partenaire).toMatchObject(sampleWithNewData);
      });

      it('should return NewPartenaire for empty Partenaire initial value', () => {
        const formGroup = service.createPartenaireFormGroup();

        const partenaire = service.getPartenaire(formGroup) as any;

        expect(partenaire).toMatchObject({});
      });

      it('should return IPartenaire', () => {
        const formGroup = service.createPartenaireFormGroup(sampleWithRequiredData);

        const partenaire = service.getPartenaire(formGroup) as any;

        expect(partenaire).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPartenaire should not enable id FormControl', () => {
        const formGroup = service.createPartenaireFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPartenaire should disable id FormControl', () => {
        const formGroup = service.createPartenaireFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
