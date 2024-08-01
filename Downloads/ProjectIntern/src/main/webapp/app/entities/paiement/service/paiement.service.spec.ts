import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPaiement } from '../paiement.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../paiement.test-samples';

import { PaiementService, RestPaiement } from './paiement.service';

const requireRestSample: RestPaiement = {
  ...sampleWithRequiredData,
  datePaiement: sampleWithRequiredData.datePaiement?.toJSON(),
};

describe('Paiement Service', () => {
  let service: PaiementService;
  let httpMock: HttpTestingController;
  let expectedResult: IPaiement | IPaiement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PaiementService);
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

    it('should create a Paiement', () => {
      const paiement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(paiement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Paiement', () => {
      const paiement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(paiement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Paiement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Paiement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Paiement', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPaiementToCollectionIfMissing', () => {
      it('should add a Paiement to an empty array', () => {
        const paiement: IPaiement = sampleWithRequiredData;
        expectedResult = service.addPaiementToCollectionIfMissing([], paiement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paiement);
      });

      it('should not add a Paiement to an array that contains it', () => {
        const paiement: IPaiement = sampleWithRequiredData;
        const paiementCollection: IPaiement[] = [
          {
            ...paiement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPaiementToCollectionIfMissing(paiementCollection, paiement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Paiement to an array that doesn't contain it", () => {
        const paiement: IPaiement = sampleWithRequiredData;
        const paiementCollection: IPaiement[] = [sampleWithPartialData];
        expectedResult = service.addPaiementToCollectionIfMissing(paiementCollection, paiement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paiement);
      });

      it('should add only unique Paiement to an array', () => {
        const paiementArray: IPaiement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const paiementCollection: IPaiement[] = [sampleWithRequiredData];
        expectedResult = service.addPaiementToCollectionIfMissing(paiementCollection, ...paiementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paiement: IPaiement = sampleWithRequiredData;
        const paiement2: IPaiement = sampleWithPartialData;
        expectedResult = service.addPaiementToCollectionIfMissing([], paiement, paiement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paiement);
        expect(expectedResult).toContain(paiement2);
      });

      it('should accept null and undefined values', () => {
        const paiement: IPaiement = sampleWithRequiredData;
        expectedResult = service.addPaiementToCollectionIfMissing([], null, paiement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paiement);
      });

      it('should return initial array if no Paiement is added', () => {
        const paiementCollection: IPaiement[] = [sampleWithRequiredData];
        expectedResult = service.addPaiementToCollectionIfMissing(paiementCollection, undefined, null);
        expect(expectedResult).toEqual(paiementCollection);
      });
    });

    describe('comparePaiement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePaiement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePaiement(entity1, entity2);
        const compareResult2 = service.comparePaiement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePaiement(entity1, entity2);
        const compareResult2 = service.comparePaiement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePaiement(entity1, entity2);
        const compareResult2 = service.comparePaiement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
