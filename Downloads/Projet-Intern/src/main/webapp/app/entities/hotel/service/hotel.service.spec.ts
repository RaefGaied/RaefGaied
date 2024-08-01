import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IHotel } from '../hotel.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../hotel.test-samples';

import { HotelService } from './hotel.service';

const requireRestSample: IHotel = {
  ...sampleWithRequiredData,
};

describe('Hotel Service', () => {
  let service: HotelService;
  let httpMock: HttpTestingController;
  let expectedResult: IHotel | IHotel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(HotelService);
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

    it('should create a Hotel', () => {
      const hotel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(hotel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Hotel', () => {
      const hotel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(hotel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Hotel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Hotel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Hotel', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHotelToCollectionIfMissing', () => {
      it('should add a Hotel to an empty array', () => {
        const hotel: IHotel = sampleWithRequiredData;
        expectedResult = service.addHotelToCollectionIfMissing([], hotel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hotel);
      });

      it('should not add a Hotel to an array that contains it', () => {
        const hotel: IHotel = sampleWithRequiredData;
        const hotelCollection: IHotel[] = [
          {
            ...hotel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHotelToCollectionIfMissing(hotelCollection, hotel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Hotel to an array that doesn't contain it", () => {
        const hotel: IHotel = sampleWithRequiredData;
        const hotelCollection: IHotel[] = [sampleWithPartialData];
        expectedResult = service.addHotelToCollectionIfMissing(hotelCollection, hotel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hotel);
      });

      it('should add only unique Hotel to an array', () => {
        const hotelArray: IHotel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const hotelCollection: IHotel[] = [sampleWithRequiredData];
        expectedResult = service.addHotelToCollectionIfMissing(hotelCollection, ...hotelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hotel: IHotel = sampleWithRequiredData;
        const hotel2: IHotel = sampleWithPartialData;
        expectedResult = service.addHotelToCollectionIfMissing([], hotel, hotel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hotel);
        expect(expectedResult).toContain(hotel2);
      });

      it('should accept null and undefined values', () => {
        const hotel: IHotel = sampleWithRequiredData;
        expectedResult = service.addHotelToCollectionIfMissing([], null, hotel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hotel);
      });

      it('should return initial array if no Hotel is added', () => {
        const hotelCollection: IHotel[] = [sampleWithRequiredData];
        expectedResult = service.addHotelToCollectionIfMissing(hotelCollection, undefined, null);
        expect(expectedResult).toEqual(hotelCollection);
      });
    });

    describe('compareHotel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHotel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareHotel(entity1, entity2);
        const compareResult2 = service.compareHotel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareHotel(entity1, entity2);
        const compareResult2 = service.compareHotel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareHotel(entity1, entity2);
        const compareResult2 = service.compareHotel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
