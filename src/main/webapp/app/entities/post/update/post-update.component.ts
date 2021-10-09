import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPost, Post } from '../post.model';
import { PostService } from '../service/post.service';
import { ICycle } from 'app/entities/cycle/cycle.model';
import { CycleService } from 'app/entities/cycle/service/cycle.service';

@Component({
  selector: 'jhi-post-update',
  templateUrl: './post-update.component.html',
})
export class PostUpdateComponent implements OnInit {
  isSaving = false;

  cyclesCollection: ICycle[] = [];

  editForm = this.fb.group({
    id: [],
    link: [null, [Validators.required]],
    commentCount: [null, [Validators.required]],
    content: [null, [Validators.required]],
    cycle: [],
  });

  constructor(
    protected postService: PostService,
    protected cycleService: CycleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ post }) => {
      this.updateForm(post);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const post = this.createFromForm();
    if (post.id !== undefined) {
      this.subscribeToSaveResponse(this.postService.update(post));
    } else {
      this.subscribeToSaveResponse(this.postService.create(post));
    }
  }

  trackCycleById(index: number, item: ICycle): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPost>>): void {
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

  protected updateForm(post: IPost): void {
    this.editForm.patchValue({
      id: post.id,
      link: post.link,
      commentCount: post.commentCount,
      content: post.content,
      cycle: post.cycle,
    });

    this.cyclesCollection = this.cycleService.addCycleToCollectionIfMissing(this.cyclesCollection, post.cycle);
  }

  protected loadRelationshipsOptions(): void {
    this.cycleService
      .query({ 'postId.specified': 'false' })
      .pipe(map((res: HttpResponse<ICycle[]>) => res.body ?? []))
      .pipe(map((cycles: ICycle[]) => this.cycleService.addCycleToCollectionIfMissing(cycles, this.editForm.get('cycle')!.value)))
      .subscribe((cycles: ICycle[]) => (this.cyclesCollection = cycles));
  }

  protected createFromForm(): IPost {
    return {
      ...new Post(),
      id: this.editForm.get(['id'])!.value,
      link: this.editForm.get(['link'])!.value,
      commentCount: this.editForm.get(['commentCount'])!.value,
      content: this.editForm.get(['content'])!.value,
      cycle: this.editForm.get(['cycle'])!.value,
    };
  }
}
