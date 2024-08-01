import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IService } from 'app/entities/service/service.model';
import { ServiceService } from 'app/entities/service/service/service.service';
import { PartenaireService } from '../service/partenaire.service';
import { IPartenaire } from '../partenaire.model';
import { PartenaireFormService } from './partenaire-form.service';

import { PartenaireUpdateComponent } from './partenaire-update.component';

describe('Partenaire Management Update Component', () => {
  let comp: PartenaireUpdateComponent;
  let fixture: ComponentFixture<PartenaireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partenaireFormService: PartenaireFormService;
  let partenaireService: PartenaireService;
  let serviceService: ServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PartenaireUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PartenaireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartenaireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partenaireFormService = TestBed.inject(PartenaireFormService);
    partenaireService = TestBed.inject(PartenaireService);
    serviceService = TestBed.inject(ServiceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Service query and add missing value', () => {
      const partenaire: IPartenaire = { id: 456 };
      const service: IService = { id: 17539 };
      partenaire.service = service;

      const serviceCollection: IService[] = [{ id: 21787 }];
      jest.spyOn(serviceService, 'query').mockReturnValue(of(new HttpResponse({ body: serviceCollection })));
      const additionalServices = [service];
      const expectedCollection: IService[] = [...additionalServices, ...serviceCollection];
      jest.spyOn(serviceService, 'addServiceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partenaire });
      comp.ngOnInit();

      expect(serviceService.query).toHaveBeenCalled();
      expect(serviceService.addServiceToCollectionIfMissing).toHaveBeenCalledWith(
        serviceCollection,
        ...additionalServices.map(expect.objectContaining),
      );
      expect(comp.servicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const partenaire: IPartenaire = { id: 456 };
      const service: IService = { id: 28250 };
      partenaire.service = service;

      activatedRoute.data = of({ partenaire });
      comp.ngOnInit();

      expect(comp.servicesSharedCollection).toContain(service);
      expect(comp.partenaire).toEqual(partenaire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartenaire>>();
      const partenaire = { id: 123 };
      jest.spyOn(partenaireFormService, 'getPartenaire').mockReturnValue(partenaire);
      jest.spyOn(partenaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partenaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partenaire }));
      saveSubject.complete();

      // THEN
      expect(partenaireFormService.getPartenaire).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(partenaireService.update).toHaveBeenCalledWith(expect.objectContaining(partenaire));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartenaire>>();
      const partenaire = { id: 123 };
      jest.spyOn(partenaireFormService, 'getPartenaire').mockReturnValue({ id: null });
      jest.spyOn(partenaireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partenaire: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partenaire }));
      saveSubject.complete();

      // THEN
      expect(partenaireFormService.getPartenaire).toHaveBeenCalled();
      expect(partenaireService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartenaire>>();
      const partenaire = { id: 123 };
      jest.spyOn(partenaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partenaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partenaireService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareService', () => {
      it('Should forward to serviceService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(serviceService, 'compareService');
        comp.compareService(entity, entity2);
        expect(serviceService.compareService).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
