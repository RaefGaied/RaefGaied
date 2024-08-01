import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PaiementService } from '../service/paiement.service';
import { IPaiement } from '../paiement.model';
import { PaiementFormService } from './paiement-form.service';

import { PaiementUpdateComponent } from './paiement-update.component';

describe('Paiement Management Update Component', () => {
  let comp: PaiementUpdateComponent;
  let fixture: ComponentFixture<PaiementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paiementFormService: PaiementFormService;
  let paiementService: PaiementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PaiementUpdateComponent],
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
      .overrideTemplate(PaiementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaiementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paiementFormService = TestBed.inject(PaiementFormService);
    paiementService = TestBed.inject(PaiementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paiement: IPaiement = { id: 456 };

      activatedRoute.data = of({ paiement });
      comp.ngOnInit();

      expect(comp.paiement).toEqual(paiement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaiement>>();
      const paiement = { id: 123 };
      jest.spyOn(paiementFormService, 'getPaiement').mockReturnValue(paiement);
      jest.spyOn(paiementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paiement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paiement }));
      saveSubject.complete();

      // THEN
      expect(paiementFormService.getPaiement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paiementService.update).toHaveBeenCalledWith(expect.objectContaining(paiement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaiement>>();
      const paiement = { id: 123 };
      jest.spyOn(paiementFormService, 'getPaiement').mockReturnValue({ id: null });
      jest.spyOn(paiementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paiement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paiement }));
      saveSubject.complete();

      // THEN
      expect(paiementFormService.getPaiement).toHaveBeenCalled();
      expect(paiementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaiement>>();
      const paiement = { id: 123 };
      jest.spyOn(paiementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paiement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paiementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
