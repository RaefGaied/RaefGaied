import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAuthentificationConfiguration } from '../authentification-configuration.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../authentification-configuration.test-samples';

import { AuthentificationConfigurationService } from './authentification-configuration.service';

const requireRestSample: IAuthentificationConfiguration = {
  ...sampleWithRequiredData,
};

describe('AuthentificationConfiguration Service', () => {
  let service: AuthentificationConfigurationService;
  let httpMock: HttpTestingController;
  let expectedResult: IAuthentificationConfiguration | IAuthentificationConfiguration[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AuthentificationConfigurationService);
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

    it('should create a AuthentificationConfiguration', () => {
      const authentificationConfiguration = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(authentificationConfiguration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AuthentificationConfiguration', () => {
      const authentificationConfiguration = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(authentificationConfiguration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AuthentificationConfiguration', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AuthentificationConfiguration', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AuthentificationConfiguration', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAuthentificationConfigurationToCollectionIfMissing', () => {
      it('should add a AuthentificationConfiguration to an empty array', () => {
        const authentificationConfiguration: IAuthentificationConfiguration = sampleWithRequiredData;
        expectedResult = service.addAuthentificationConfigurationToCollectionIfMissing([], authentificationConfiguration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(authentificationConfiguration);
      });

      it('should not add a AuthentificationConfiguration to an array that contains it', () => {
        const authentificationConfiguration: IAuthentificationConfiguration = sampleWithRequiredData;
        const authentificationConfigurationCollection: IAuthentificationConfiguration[] = [
          {
            ...authentificationConfiguration,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAuthentificationConfigurationToCollectionIfMissing(
          authentificationConfigurationCollection,
          authentificationConfiguration,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AuthentificationConfiguration to an array that doesn't contain it", () => {
        const authentificationConfiguration: IAuthentificationConfiguration = sampleWithRequiredData;
        const authentificationConfigurationCollection: IAuthentificationConfiguration[] = [sampleWithPartialData];
        expectedResult = service.addAuthentificationConfigurationToCollectionIfMissing(
          authentificationConfigurationCollection,
          authentificationConfiguration,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(authentificationConfiguration);
      });

      it('should add only unique AuthentificationConfiguration to an array', () => {
        const authentificationConfigurationArray: IAuthentificationConfiguration[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const authentificationConfigurationCollection: IAuthentificationConfiguration[] = [sampleWithRequiredData];
        expectedResult = service.addAuthentificationConfigurationToCollectionIfMissing(
          authentificationConfigurationCollection,
          ...authentificationConfigurationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const authentificationConfiguration: IAuthentificationConfiguration = sampleWithRequiredData;
        const authentificationConfiguration2: IAuthentificationConfiguration = sampleWithPartialData;
        expectedResult = service.addAuthentificationConfigurationToCollectionIfMissing(
          [],
          authentificationConfiguration,
          authentificationConfiguration2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(authentificationConfiguration);
        expect(expectedResult).toContain(authentificationConfiguration2);
      });

      it('should accept null and undefined values', () => {
        const authentificationConfiguration: IAuthentificationConfiguration = sampleWithRequiredData;
        expectedResult = service.addAuthentificationConfigurationToCollectionIfMissing([], null, authentificationConfiguration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(authentificationConfiguration);
      });

      it('should return initial array if no AuthentificationConfiguration is added', () => {
        const authentificationConfigurationCollection: IAuthentificationConfiguration[] = [sampleWithRequiredData];
        expectedResult = service.addAuthentificationConfigurationToCollectionIfMissing(
          authentificationConfigurationCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(authentificationConfigurationCollection);
      });
    });

    describe('compareAuthentificationConfiguration', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAuthentificationConfiguration(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAuthentificationConfiguration(entity1, entity2);
        const compareResult2 = service.compareAuthentificationConfiguration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAuthentificationConfiguration(entity1, entity2);
        const compareResult2 = service.compareAuthentificationConfiguration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAuthentificationConfiguration(entity1, entity2);
        const compareResult2 = service.compareAuthentificationConfiguration(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
