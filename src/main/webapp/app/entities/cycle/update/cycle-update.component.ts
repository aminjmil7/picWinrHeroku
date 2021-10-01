import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICycle, Cycle } from '../cycle.model';
import { CycleService } from '../service/cycle.service';
import { IOpcode } from 'app/entities/opcode/opcode.model';
import { OpcodeService } from 'app/entities/opcode/service/opcode.service';

@Component({
  selector: 'jhi-cycle-update',
  templateUrl: './cycle-update.component.html',
})
export class CycleUpdateComponent implements OnInit {
  isSaving = false;

  opcodesSharedCollection: IOpcode[] = [];

  editForm = this.fb.group({
    id: [],
    runDate: [null, [Validators.required]],
    email: [null, [Validators.required]],
    winners: [],
    alternatives: [],
    opcode: [],
  });

  constructor(
    protected cycleService: CycleService,
    protected opcodeService: OpcodeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cycle }) => {
      if (cycle.id === undefined) {
        const today = dayjs().startOf('day');
        cycle.runDate = today;
      }

      this.updateForm(cycle);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cycle = this.createFromForm();
    if (cycle.id !== undefined) {
      this.subscribeToSaveResponse(this.cycleService.update(cycle));
    } else {
      this.subscribeToSaveResponse(this.cycleService.create(cycle));
    }
  }

  trackOpcodeById(index: number, item: IOpcode): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICycle>>): void {
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

  protected updateForm(cycle: ICycle): void {
    this.editForm.patchValue({
      id: cycle.id,
      runDate: cycle.runDate ? cycle.runDate.format(DATE_TIME_FORMAT) : null,
      email: cycle.email,
      winners: cycle.winners,
      alternatives: cycle.alternatives,
      opcode: cycle.opcode,
    });

    this.opcodesSharedCollection = this.opcodeService.addOpcodeToCollectionIfMissing(this.opcodesSharedCollection, cycle.opcode);
  }

  protected loadRelationshipsOptions(): void {
    this.opcodeService
      .query()
      .pipe(map((res: HttpResponse<IOpcode[]>) => res.body ?? []))
      .pipe(map((opcodes: IOpcode[]) => this.opcodeService.addOpcodeToCollectionIfMissing(opcodes, this.editForm.get('opcode')!.value)))
      .subscribe((opcodes: IOpcode[]) => (this.opcodesSharedCollection = opcodes));
  }

  protected createFromForm(): ICycle {
    return {
      ...new Cycle(),
      id: this.editForm.get(['id'])!.value,
      runDate: this.editForm.get(['runDate'])!.value ? dayjs(this.editForm.get(['runDate'])!.value, DATE_TIME_FORMAT) : undefined,
      email: this.editForm.get(['email'])!.value,
      winners: this.editForm.get(['winners'])!.value,
      alternatives: this.editForm.get(['alternatives'])!.value,
      opcode: this.editForm.get(['opcode'])!.value,
    };
  }
}
