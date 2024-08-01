import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IHotelAdministrateur } from '../hotel-administrateur.model';
import { HotelAdministrateurService } from '../service/hotel-administrateur.service';

import hotelAdministrateurResolve from './hotel-administrateur-routing-resolve.service';

describe('HotelAdministrateur routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: HotelAdministrateurService;
  let resultHotelAdministrateur: IHotelAdministrateur | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(HotelAdministrateurService);
    resultHotelAdministrateur = undefined;
  });

  describe('resolve', () => {
    it('should return IHotelAdministrateur returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        hotelAdministrateurResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHotelAdministrateur = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultHotelAdministrateur).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        hotelAdministrateurResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHotelAdministrateur = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultHotelAdministrateur).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IHotelAdministrateur>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        hotelAdministrateurResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHotelAdministrateur = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultHotelAdministrateur).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
