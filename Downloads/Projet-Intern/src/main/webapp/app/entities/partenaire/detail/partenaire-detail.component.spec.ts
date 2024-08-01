import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PartenaireDetailComponent } from './partenaire-detail.component';

describe('Partenaire Management Detail Component', () => {
  let comp: PartenaireDetailComponent;
  let fixture: ComponentFixture<PartenaireDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PartenaireDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PartenaireDetailComponent,
              resolve: { partenaire: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PartenaireDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PartenaireDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partenaire on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PartenaireDetailComponent);

      // THEN
      expect(instance.partenaire()).toEqual(expect.objectContaining({ id: 123 }));
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
