import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IEmailTemplateConfiguration } from '../email-template-configuration.model';
import { EmailTemplateConfigurationService } from '../service/email-template-configuration.service';

import emailTemplateConfigurationResolve from './email-template-configuration-routing-resolve.service';

describe('EmailTemplateConfiguration routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: EmailTemplateConfigurationService;
  let resultEmailTemplateConfiguration: IEmailTemplateConfiguration | null | undefined;

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
    service = TestBed.inject(EmailTemplateConfigurationService);
    resultEmailTemplateConfiguration = undefined;
  });

  describe('resolve', () => {
    it('should return IEmailTemplateConfiguration returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        emailTemplateConfigurationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEmailTemplateConfiguration = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEmailTemplateConfiguration).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        emailTemplateConfigurationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEmailTemplateConfiguration = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEmailTemplateConfiguration).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEmailTemplateConfiguration>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        emailTemplateConfigurationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEmailTemplateConfiguration = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEmailTemplateConfiguration).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
