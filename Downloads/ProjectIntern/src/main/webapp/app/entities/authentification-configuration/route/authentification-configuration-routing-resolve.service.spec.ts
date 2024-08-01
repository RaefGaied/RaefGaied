import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAuthentificationConfiguration } from '../authentification-configuration.model';
import { AuthentificationConfigurationService } from '../service/authentification-configuration.service';

import authentificationConfigurationResolve from './authentification-configuration-routing-resolve.service';

describe('AuthentificationConfiguration routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AuthentificationConfigurationService;
  let resultAuthentificationConfiguration: IAuthentificationConfiguration | null | undefined;

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
    service = TestBed.inject(AuthentificationConfigurationService);
    resultAuthentificationConfiguration = undefined;
  });

  describe('resolve', () => {
    it('should return IAuthentificationConfiguration returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        authentificationConfigurationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAuthentificationConfiguration = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAuthentificationConfiguration).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        authentificationConfigurationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAuthentificationConfiguration = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAuthentificationConfiguration).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAuthentificationConfiguration>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        authentificationConfigurationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAuthentificationConfiguration = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAuthentificationConfiguration).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
