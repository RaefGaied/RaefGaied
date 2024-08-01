import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ui-configuration.test-samples';

import { UIConfigurationFormService } from './ui-configuration-form.service';

describe('UIConfiguration Form Service', () => {
  let service: UIConfigurationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UIConfigurationFormService);
  });

  describe('Service methods', () => {
    describe('createUIConfigurationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUIConfigurationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            colorSchema: expect.any(Object),
            logo: expect.any(Object),
            banner: expect.any(Object),
            dateCreation: expect.any(Object),
            dateModify: expect.any(Object),
            hotel: expect.any(Object),
          }),
        );
      });

      it('passing IUIConfiguration should create a new form with FormGroup', () => {
        const formGroup = service.createUIConfigurationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            colorSchema: expect.any(Object),
            logo: expect.any(Object),
            banner: expect.any(Object),
            dateCreation: expect.any(Object),
            dateModify: expect.any(Object),
            hotel: expect.any(Object),
          }),
        );
      });
    });

    describe('getUIConfiguration', () => {
      it('should return NewUIConfiguration for default UIConfiguration initial value', () => {
        const formGroup = service.createUIConfigurationFormGroup(sampleWithNewData);

        const uIConfiguration = service.getUIConfiguration(formGroup) as any;

        expect(uIConfiguration).toMatchObject(sampleWithNewData);
      });

      it('should return NewUIConfiguration for empty UIConfiguration initial value', () => {
        const formGroup = service.createUIConfigurationFormGroup();

        const uIConfiguration = service.getUIConfiguration(formGroup) as any;

        expect(uIConfiguration).toMatchObject({});
      });

      it('should return IUIConfiguration', () => {
        const formGroup = service.createUIConfigurationFormGroup(sampleWithRequiredData);

        const uIConfiguration = service.getUIConfiguration(formGroup) as any;

        expect(uIConfiguration).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUIConfiguration should not enable id FormControl', () => {
        const formGroup = service.createUIConfigurationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUIConfiguration should disable id FormControl', () => {
        const formGroup = service.createUIConfigurationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
