import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { ReceptionistUpdateComponent } from 'app/entities/receptionist/receptionist-update.component';
import { ReceptionistService } from 'app/entities/receptionist/receptionist.service';
import { Receptionist } from 'app/shared/model/receptionist.model';

describe('Component Tests', () => {
  describe('Receptionist Management Update Component', () => {
    let comp: ReceptionistUpdateComponent;
    let fixture: ComponentFixture<ReceptionistUpdateComponent>;
    let service: ReceptionistService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingTestModule],
        declarations: [ReceptionistUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReceptionistUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReceptionistUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReceptionistService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Receptionist(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Receptionist();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
