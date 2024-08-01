import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ReservationDetailComponent } from './reservation-detail.component';

describe('Reservation Management Detail Component', () => {
  let comp: ReservationDetailComponent;
  let fixture: ComponentFixture<ReservationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ReservationDetailComponent,
              resolve: { reservation: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ReservationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load reservation on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ReservationDetailComponent);

      // THEN
      expect(instance.reservation()).toEqual(expect.objectContaining({ id: 123 }));
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
