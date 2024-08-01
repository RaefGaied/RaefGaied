import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPartenaire } from '../partenaire.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../partenaire.test-samples';

import { PartenaireService } from './partenaire.service';

const requireRestSample: IPartenaire = {
  ...sampleWithRequiredData,
};

describe('Partenaire Service', () => {
  let service: PartenaireService;
  let httpMock: HttpTestingController;
  let expectedResult: IPartenaire | IPartenaire[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PartenaireService);
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

    it('should create a Partenaire', () => {
      const partenaire = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(partenaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Partenaire', () => {
      const partenaire = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(partenaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Partenaire', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Partenaire', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Partenaire', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPartenaireToCollectionIfMissing', () => {
      it('should add a Partenaire to an empty array', () => {
        const partenaire: IPartenaire = sampleWithRequiredData;
        expectedResult = service.addPartenaireToCollectionIfMissing([], partenaire);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partenaire);
      });

      it('should not add a Partenaire to an array that contains it', () => {
        const partenaire: IPartenaire = sampleWithRequiredData;
        const partenaireCollection: IPartenaire[] = [
          {
            ...partenaire,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPartenaireToCollectionIfMissing(partenaireCollection, partenaire);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Partenaire to an array that doesn't contain it", () => {
        const partenaire: IPartenaire = sampleWithRequiredData;
        const partenaireCollection: IPartenaire[] = [sampleWithPartialData];
        expectedResult = service.addPartenaireToCollectionIfMissing(partenaireCollection, partenaire);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partenaire);
      });

      it('should add only unique Partenaire to an array', () => {
        const partenaireArray: IPartenaire[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const partenaireCollection: IPartenaire[] = [sampleWithRequiredData];
        expectedResult = service.addPartenaireToCollectionIfMissing(partenaireCollection, ...partenaireArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partenaire: IPartenaire = sampleWithRequiredData;
        const partenaire2: IPartenaire = sampleWithPartialData;
        expectedResult = service.addPartenaireToCollectionIfMissing([], partenaire, partenaire2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partenaire);
        expect(expectedResult).toContain(partenaire2);
      });

      it('should accept null and undefined values', () => {
        const partenaire: IPartenaire = sampleWithRequiredData;
        expectedResult = service.addPartenaireToCollectionIfMissing([], null, partenaire, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partenaire);
      });

      it('should return initial array if no Partenaire is added', () => {
        const partenaireCollection: IPartenaire[] = [sampleWithRequiredData];
        expectedResult = service.addPartenaireToCollectionIfMissing(partenaireCollection, undefined, null);
        expect(expectedResult).toEqual(partenaireCollection);
      });
    });

    describe('comparePartenaire', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePartenaire(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePartenaire(entity1, entity2);
        const compareResult2 = service.comparePartenaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePartenaire(entity1, entity2);
        const compareResult2 = service.comparePartenaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePartenaire(entity1, entity2);
        const compareResult2 = service.comparePartenaire(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
