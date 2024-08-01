import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmailTemplateConfigurationDetailComponent } from './email-template-configuration-detail.component';

describe('EmailTemplateConfiguration Management Detail Component', () => {
  let comp: EmailTemplateConfigurationDetailComponent;
  let fixture: ComponentFixture<EmailTemplateConfigurationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmailTemplateConfigurationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EmailTemplateConfigurationDetailComponent,
              resolve: { emailTemplateConfiguration: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EmailTemplateConfigurationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailTemplateConfigurationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load emailTemplateConfiguration on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmailTemplateConfigurationDetailComponent);

      // THEN
      expect(instance.emailTemplateConfiguration()).toEqual(expect.objectContaining({ id: 123 }));
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
