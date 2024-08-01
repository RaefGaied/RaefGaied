import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../email-template-configuration.test-samples';

import { EmailTemplateConfigurationFormService } from './email-template-configuration-form.service';

describe('EmailTemplateConfiguration Form Service', () => {
  let service: EmailTemplateConfigurationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmailTemplateConfigurationFormService);
  });

  describe('Service methods', () => {
    describe('createEmailTemplateConfigurationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmailTemplateConfigurationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomTemplate: expect.any(Object),
            corps: expect.any(Object),
            dateCreation: expect.any(Object),
            dateModify: expect.any(Object),
            activeStatus: expect.any(Object),
            hotel: expect.any(Object),
          }),
        );
      });

      it('passing IEmailTemplateConfiguration should create a new form with FormGroup', () => {
        const formGroup = service.createEmailTemplateConfigurationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomTemplate: expect.any(Object),
            corps: expect.any(Object),
            dateCreation: expect.any(Object),
            dateModify: expect.any(Object),
            activeStatus: expect.any(Object),
            hotel: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmailTemplateConfiguration', () => {
      it('should return NewEmailTemplateConfiguration for default EmailTemplateConfiguration initial value', () => {
        const formGroup = service.createEmailTemplateConfigurationFormGroup(sampleWithNewData);

        const emailTemplateConfiguration = service.getEmailTemplateConfiguration(formGroup) as any;

        expect(emailTemplateConfiguration).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmailTemplateConfiguration for empty EmailTemplateConfiguration initial value', () => {
        const formGroup = service.createEmailTemplateConfigurationFormGroup();

        const emailTemplateConfiguration = service.getEmailTemplateConfiguration(formGroup) as any;

        expect(emailTemplateConfiguration).toMatchObject({});
      });

      it('should return IEmailTemplateConfiguration', () => {
        const formGroup = service.createEmailTemplateConfigurationFormGroup(sampleWithRequiredData);

        const emailTemplateConfiguration = service.getEmailTemplateConfiguration(formGroup) as any;

        expect(emailTemplateConfiguration).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmailTemplateConfiguration should not enable id FormControl', () => {
        const formGroup = service.createEmailTemplateConfigurationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmailTemplateConfiguration should disable id FormControl', () => {
        const formGroup = service.createEmailTemplateConfigurationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
