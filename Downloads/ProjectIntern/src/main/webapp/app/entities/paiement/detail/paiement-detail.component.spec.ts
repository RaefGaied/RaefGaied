import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PaiementDetailComponent } from './paiement-detail.component';

describe('Paiement Management Detail Component', () => {
  let comp: PaiementDetailComponent;
  let fixture: ComponentFixture<PaiementDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaiementDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PaiementDetailComponent,
              resolve: { paiement: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PaiementDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaiementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paiement on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PaiementDetailComponent);

      // THEN
      expect(instance.paiement()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
