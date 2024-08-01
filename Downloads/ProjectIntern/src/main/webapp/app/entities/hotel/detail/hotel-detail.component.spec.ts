import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { HotelDetailComponent } from './hotel-detail.component';

describe('Hotel Management Detail Component', () => {
  let comp: HotelDetailComponent;
  let fixture: ComponentFixture<HotelDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HotelDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: HotelDetailComponent,
              resolve: { hotel: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(HotelDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HotelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hotel on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', HotelDetailComponent);

      // THEN
      expect(instance.hotel()).toEqual(expect.objectContaining({ id: 123 }));
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
