import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IUIConfiguration } from '../ui-configuration.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ui-configuration.test-samples';

import { UIConfigurationService, RestUIConfiguration } from './ui-configuration.service';

const requireRestSample: RestUIConfiguration = {
  ...sampleWithRequiredData,
  dateCreation: sampleWithRequiredData.dateCreation?.toJSON(),
  dateModify: sampleWithRequiredData.dateModify?.toJSON(),
};

describe('UIConfiguration Service', () => {
  let service: UIConfigurationService;
  let httpMock: HttpTestingController;
  let expectedResult: IUIConfiguration | IUIConfiguration[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(UIConfigurationService);
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

    it('should create a UIConfiguration', () => {
      const uIConfiguration = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(uIConfiguration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UIConfiguration', () => {
      const uIConfiguration = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(uIConfiguration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UIConfiguration', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UIConfiguration', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UIConfiguration', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUIConfigurationToCollectionIfMissing', () => {
      it('should add a UIConfiguration to an empty array', () => {
        const uIConfiguration: IUIConfiguration = sampleWithRequiredData;
        expectedResult = service.addUIConfigurationToCollectionIfMissing([], uIConfiguration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(uIConfiguration);
      });

      it('should not add a UIConfiguration to an array that contains it', () => {
        const uIConfiguration: IUIConfiguration = sampleWithRequiredData;
        const uIConfigurationCollection: IUIConfiguration[] = [
          {
            ...uIConfiguration,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUIConfigurationToCollectionIfMissing(uIConfigurationCollection, uIConfiguration);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UIConfiguration to an array that doesn't contain it", () => {
        const uIConfiguration: IUIConfiguration = sampleWithRequiredData;
        const uIConfigurationCollection: IUIConfiguration[] = [sampleWithPartialData];
        expectedResult = service.addUIConfigurationToCollectionIfMissing(uIConfigurationCollection, uIConfiguration);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(uIConfiguration);
      });

      it('should add only unique UIConfiguration to an array', () => {
        const uIConfigurationArray: IUIConfiguration[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const uIConfigurationCollection: IUIConfiguration[] = [sampleWithRequiredData];
        expectedResult = service.addUIConfigurationToCollectionIfMissing(uIConfigurationCollection, ...uIConfigurationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const uIConfiguration: IUIConfiguration = sampleWithRequiredData;
        const uIConfiguration2: IUIConfiguration = sampleWithPartialData;
        expectedResult = service.addUIConfigurationToCollectionIfMissing([], uIConfiguration, uIConfiguration2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(uIConfiguration);
        expect(expectedResult).toContain(uIConfiguration2);
      });

      it('should accept null and undefined values', () => {
        const uIConfiguration: IUIConfiguration = sampleWithRequiredData;
        expectedResult = service.addUIConfigurationToCollectionIfMissing([], null, uIConfiguration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(uIConfiguration);
      });

      it('should return initial array if no UIConfiguration is added', () => {
        const uIConfigurationCollection: IUIConfiguration[] = [sampleWithRequiredData];
        expectedResult = service.addUIConfigurationToCollectionIfMissing(uIConfigurationCollection, undefined, null);
        expect(expectedResult).toEqual(uIConfigurationCollection);
      });
    });

    describe('compareUIConfiguration', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUIConfiguration(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUIConfiguration(entity1, entity2);
        const compareResult2 = service.compareUIConfiguration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUIConfiguration(entity1, entity2);
        const compareResult2 = service.compareUIConfiguration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUIConfiguration(entity1, entity2);
        const compareResult2 = service.compareUIConfiguration(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
