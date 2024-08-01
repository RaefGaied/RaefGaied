import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEmailTemplateConfiguration } from '../email-template-configuration.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../email-template-configuration.test-samples';

import { EmailTemplateConfigurationService, RestEmailTemplateConfiguration } from './email-template-configuration.service';

const requireRestSample: RestEmailTemplateConfiguration = {
  ...sampleWithRequiredData,
  dateCreation: sampleWithRequiredData.dateCreation?.toJSON(),
  dateModify: sampleWithRequiredData.dateModify?.toJSON(),
};

describe('EmailTemplateConfiguration Service', () => {
  let service: EmailTemplateConfigurationService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmailTemplateConfiguration | IEmailTemplateConfiguration[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EmailTemplateConfigurationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a EmailTemplateConfiguration', () => {
      const emailTemplateConfiguration = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(emailTemplateConfiguration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmailTemplateConfiguration', () => {
      const emailTemplateConfiguration = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(emailTemplateConfiguration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmailTemplateConfiguration', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmailTemplateConfiguration', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmailTemplateConfiguration', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmailTemplateConfigurationToCollectionIfMissing', () => {
      it('should add a EmailTemplateConfiguration to an empty array', () => {
        const emailTemplateConfiguration: IEmailTemplateConfiguration = sampleWithRequiredData;
        expectedResult = service.addEmailTemplateConfigurationToCollectionIfMissing([], emailTemplateConfiguration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailTemplateConfiguration);
      });

      it('should not add a EmailTemplateConfiguration to an array that contains it', () => {
        const emailTemplateConfiguration: IEmailTemplateConfiguration = sampleWithRequiredData;
        const emailTemplateConfigurationCollection: IEmailTemplateConfiguration[] = [
          {
            ...emailTemplateConfiguration,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmailTemplateConfigurationToCollectionIfMissing(
          emailTemplateConfigurationCollection,
          emailTemplateConfiguration,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmailTemplateConfiguration to an array that doesn't contain it", () => {
        const emailTemplateConfiguration: IEmailTemplateConfiguration = sampleWithRequiredData;
        const emailTemplateConfigurationCollection: IEmailTemplateConfiguration[] = [sampleWithPartialData];
        expectedResult = service.addEmailTemplateConfigurationToCollectionIfMissing(
          emailTemplateConfigurationCollection,
          emailTemplateConfiguration,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailTemplateConfiguration);
      });

      it('should add only unique EmailTemplateConfiguration to an array', () => {
        const emailTemplateConfigurationArray: IEmailTemplateConfiguration[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const emailTemplateConfigurationCollection: IEmailTemplateConfiguration[] = [sampleWithRequiredData];
        expectedResult = service.addEmailTemplateConfigurationToCollectionIfMissing(
          emailTemplateConfigurationCollection,
          ...emailTemplateConfigurationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const emailTemplateConfiguration: IEmailTemplateConfiguration = sampleWithRequiredData;
        const emailTemplateConfiguration2: IEmailTemplateConfiguration = sampleWithPartialData;
        expectedResult = service.addEmailTemplateConfigurationToCollectionIfMissing(
          [],
          emailTemplateConfiguration,
          emailTemplateConfiguration2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailTemplateConfiguration);
        expect(expectedResult).toContain(emailTemplateConfiguration2);
      });

      it('should accept null and undefined values', () => {
        const emailTemplateConfiguration: IEmailTemplateConfiguration = sampleWithRequiredData;
        expectedResult = service.addEmailTemplateConfigurationToCollectionIfMissing([], null, emailTemplateConfiguration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailTemplateConfiguration);
      });

      it('should return initial array if no EmailTemplateConfiguration is added', () => {
        const emailTemplateConfigurationCollection: IEmailTemplateConfiguration[] = [sampleWithRequiredData];
        expectedResult = service.addEmailTemplateConfigurationToCollectionIfMissing(emailTemplateConfigurationCollection, undefined, null);
        expect(expectedResult).toEqual(emailTemplateConfigurationCollection);
      });
    });

    describe('compareEmailTemplateConfiguration', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmailTemplateConfiguration(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmailTemplateConfiguration(entity1, entity2);
        const compareResult2 = service.compareEmailTemplateConfiguration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmailTemplateConfiguration(entity1, entity2);
        const compareResult2 = service.compareEmailTemplateConfiguration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmailTemplateConfiguration(entity1, entity2);
        const compareResult2 = service.compareEmailTemplateConfiguration(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
