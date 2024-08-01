import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { HotelAdministrateurDetailComponent } from './hotel-administrateur-detail.component';

describe('HotelAdministrateur Management Detail Component', () => {
  let comp: HotelAdministrateurDetailComponent;
  let fixture: ComponentFixture<HotelAdministrateurDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HotelAdministrateurDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: HotelAdministrateurDetailComponent,
              resolve: { hotelAdministrateur: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(HotelAdministrateurDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HotelAdministrateurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hotelAdministrateur on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', HotelAdministrateurDetailComponent);

      // THEN
      expect(instance.hotelAdministrateur()).toEqual(expect.objectContaining({ id: 123 }));
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
