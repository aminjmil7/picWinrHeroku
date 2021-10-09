jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OpcodeService } from '../service/opcode.service';
import { IOpcode, Opcode } from '../opcode.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { OpcodeUpdateComponent } from './opcode-update.component';

describe('Component Tests', () => {
  describe('Opcode Management Update Component', () => {
    let comp: OpcodeUpdateComponent;
    let fixture: ComponentFixture<OpcodeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let opcodeService: OpcodeService;
    let userService: UserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OpcodeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OpcodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OpcodeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      opcodeService = TestBed.inject(OpcodeService);
      userService = TestBed.inject(UserService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call User query and add missing value', () => {
        const opcode: IOpcode = { id: 456 };
        const user: IUser = { id: 30194 };
        opcode.user = user;

        const userCollection: IUser[] = [{ id: 85205 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [user];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ opcode });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const opcode: IOpcode = { id: 456 };
        const user: IUser = { id: 81142 };
        opcode.user = user;

        activatedRoute.data = of({ opcode });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(opcode));
        expect(comp.usersSharedCollection).toContain(user);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Opcode>>();
        const opcode = { id: 123 };
        jest.spyOn(opcodeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ opcode });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: opcode }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(opcodeService.update).toHaveBeenCalledWith(opcode);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Opcode>>();
        const opcode = new Opcode();
        jest.spyOn(opcodeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ opcode });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: opcode }));
        saveSubject.complete();

        // THEN
        expect(opcodeService.create).toHaveBeenCalledWith(opcode);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Opcode>>();
        const opcode = { id: 123 };
        jest.spyOn(opcodeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ opcode });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(opcodeService.update).toHaveBeenCalledWith(opcode);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserById', () => {
        it('Should return tracked User primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
