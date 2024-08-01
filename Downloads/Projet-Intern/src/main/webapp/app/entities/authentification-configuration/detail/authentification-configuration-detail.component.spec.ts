import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AuthentificationConfigurationDetailComponent } from './authentification-configuration-detail.component';

describe('AuthentificationConfiguration Management Detail Component', () => {
  let comp: AuthentificationConfigurationDetailComponent;
  let fixture: ComponentFixture<AuthentificationConfigurationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthentificationConfigurationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AuthentificationConfigurationDetailComponent,
              resolve: { authentificationConfiguration: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AuthentificationConfigurationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthentificationConfigurationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load authentificationConfiguration on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AuthentificationConfigurationDetailComponent);

      // THEN
      expect(instance.authentificationConfiguration()).toEqual(expect.objectContaining({ id: 123 }));
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
