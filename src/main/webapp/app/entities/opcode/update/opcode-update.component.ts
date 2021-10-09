import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOpcode, Opcode } from '../opcode.model';
import { OpcodeService } from '../service/opcode.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-opcode-update',
  templateUrl: './opcode-update.component.html',
})
export class OpcodeUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    opirationCode: [null, [Validators.required]],
    count: [null, [Validators.required]],
    ceationDated: [null, [Validators.required]],
    expirationDate: [],
    user: [],
  });

  constructor(
    protected opcodeService: OpcodeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ opcode }) => {
      if (opcode.id === undefined) {
        const today = dayjs().startOf('day');
        opcode.ceationDated = today;
        opcode.expirationDate = today;
      }

      this.updateForm(opcode);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const opcode = this.createFromForm();
    if (opcode.id !== undefined) {
      this.subscribeToSaveResponse(this.opcodeService.update(opcode));
    } else {
      this.subscribeToSaveResponse(this.opcodeService.create(opcode));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpcode>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(opcode: IOpcode): void {
    this.editForm.patchValue({
      id: opcode.id,
      opirationCode: opcode.opirationCode,
      count: opcode.count,
      ceationDated: opcode.ceationDated ? opcode.ceationDated.format(DATE_TIME_FORMAT) : null,
      expirationDate: opcode.expirationDate ? opcode.expirationDate.format(DATE_TIME_FORMAT) : null,
      user: opcode.user,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, opcode.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IOpcode {
    return {
      ...new Opcode(),
      id: this.editForm.get(['id'])!.value,
      opirationCode: this.editForm.get(['opirationCode'])!.value,
      count: this.editForm.get(['count'])!.value,
      ceationDated: this.editForm.get(['ceationDated'])!.value
        ? dayjs(this.editForm.get(['ceationDated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      expirationDate: this.editForm.get(['expirationDate'])!.value
        ? dayjs(this.editForm.get(['expirationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
