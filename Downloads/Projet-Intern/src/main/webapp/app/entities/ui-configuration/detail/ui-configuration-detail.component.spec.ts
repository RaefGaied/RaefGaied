import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { UIConfigurationDetailComponent } from './ui-configuration-detail.component';

describe('UIConfiguration Management Detail Component', () => {
  let comp: UIConfigurationDetailComponent;
  let fixture: ComponentFixture<UIConfigurationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UIConfigurationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: UIConfigurationDetailComponent,
              resolve: { uIConfiguration: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(UIConfigurationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UIConfigurationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load uIConfiguration on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', UIConfigurationDetailComponent);

      // THEN
      expect(instance.uIConfiguration()).toEqual(expect.objectContaining({ id: 123 }));
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
