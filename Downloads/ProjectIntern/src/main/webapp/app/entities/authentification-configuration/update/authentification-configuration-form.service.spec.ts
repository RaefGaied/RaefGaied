import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../authentification-configuration.test-samples';

import { AuthentificationConfigurationFormService } from './authentification-configuration-form.service';

describe('AuthentificationConfiguration Form Service', () => {
  let service: AuthentificationConfigurationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthentificationConfigurationFormService);
  });

  describe('Service methods', () => {
    describe('createAuthentificationConfigurationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAuthentificationConfigurationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            twoFactorEnabled: expect.any(Object),
            loginPageCustomization: expect.any(Object),
            hotel: expect.any(Object),
          }),
        );
      });

      it('passing IAuthentificationConfiguration should create a new form with FormGroup', () => {
        const formGroup = service.createAuthentificationConfigurationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            twoFactorEnabled: expect.any(Object),
            loginPageCustomization: expect.any(Object),
            hotel: expect.any(Object),
          }),
        );
      });
    });

    describe('getAuthentificationConfiguration', () => {
      it('should return NewAuthentificationConfiguration for default AuthentificationConfiguration initial value', () => {
        const formGroup = service.createAuthentificationConfigurationFormGroup(sampleWithNewData);

        const authentificationConfiguration = service.getAuthentificationConfiguration(formGroup) as any;

        expect(authentificationConfiguration).toMatchObject(sampleWithNewData);
      });

      it('should return NewAuthentificationConfiguration for empty AuthentificationConfiguration initial value', () => {
        const formGroup = service.createAuthentificationConfigurationFormGroup();

        const authentificationConfiguration = service.getAuthentificationConfiguration(formGroup) as any;

        expect(authentificationConfiguration).toMatchObject({});
      });

      it('should return IAuthentificationConfiguration', () => {
        const formGroup = service.createAuthentificationConfigurationFormGroup(sampleWithRequiredData);

        const authentificationConfiguration = service.getAuthentificationConfiguration(formGroup) as any;

        expect(authentificationConfiguration).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAuthentificationConfiguration should not enable id FormControl', () => {
        const formGroup = service.createAuthentificationConfigurationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAuthentificationConfiguration should disable id FormControl', () => {
        const formGroup = service.createAuthentificationConfigurationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
