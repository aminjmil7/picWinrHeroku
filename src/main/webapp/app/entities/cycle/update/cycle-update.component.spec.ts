jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CycleService } from '../service/cycle.service';
import { ICycle, Cycle } from '../cycle.model';
import { IOpcode } from 'app/entities/opcode/opcode.model';
import { OpcodeService } from 'app/entities/opcode/service/opcode.service';

import { CycleUpdateComponent } from './cycle-update.component';

describe('Component Tests', () => {
  describe('Cycle Management Update Component', () => {
    let comp: CycleUpdateComponent;
    let fixture: ComponentFixture<CycleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cycleService: CycleService;
    let opcodeService: OpcodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CycleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CycleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CycleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cycleService = TestBed.inject(CycleService);
      opcodeService = TestBed.inject(OpcodeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Opcode query and add missing value', () => {
        const cycle: ICycle = { id: 456 };
        const opcode: IOpcode = { id: 20483 };
        cycle.opcode = opcode;

        const opcodeCollection: IOpcode[] = [{ id: 49237 }];
        jest.spyOn(opcodeService, 'query').mockReturnValue(of(new HttpResponse({ body: opcodeCollection })));
        const additionalOpcodes = [opcode];
        const expectedCollection: IOpcode[] = [...additionalOpcodes, ...opcodeCollection];
        jest.spyOn(opcodeService, 'addOpcodeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ cycle });
        comp.ngOnInit();

        expect(opcodeService.query).toHaveBeenCalled();
        expect(opcodeService.addOpcodeToCollectionIfMissing).toHaveBeenCalledWith(opcodeCollection, ...additionalOpcodes);
        expect(comp.opcodesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const cycle: ICycle = { id: 456 };
        const opcode: IOpcode = { id: 86369 };
        cycle.opcode = opcode;

        activatedRoute.data = of({ cycle });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cycle));
        expect(comp.opcodesSharedCollection).toContain(opcode);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Cycle>>();
        const cycle = { id: 123 };
        jest.spyOn(cycleService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cycle });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cycle }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cycleService.update).toHaveBeenCalledWith(cycle);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Cycle>>();
        const cycle = new Cycle();
        jest.spyOn(cycleService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cycle });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cycle }));
        saveSubject.complete();

        // THEN
        expect(cycleService.create).toHaveBeenCalledWith(cycle);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Cycle>>();
        const cycle = { id: 123 };
        jest.spyOn(cycleService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cycle });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cycleService.update).toHaveBeenCalledWith(cycle);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackOpcodeById', () => {
        it('Should return tracked Opcode primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackOpcodeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
