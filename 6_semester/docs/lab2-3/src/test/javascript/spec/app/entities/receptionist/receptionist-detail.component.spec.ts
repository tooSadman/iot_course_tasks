import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { ReceptionistDetailComponent } from 'app/entities/receptionist/receptionist-detail.component';
import { Receptionist } from 'app/shared/model/receptionist.model';

describe('Component Tests', () => {
  describe('Receptionist Management Detail Component', () => {
    let comp: ReceptionistDetailComponent;
    let fixture: ComponentFixture<ReceptionistDetailComponent>;
    const route = ({ data: of({ receptionist: new Receptionist(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingTestModule],
        declarations: [ReceptionistDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReceptionistDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReceptionistDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load receptionist on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.receptionist).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
