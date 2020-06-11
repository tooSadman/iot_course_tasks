import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBill, Bill } from 'app/shared/model/bill.model';
import { BillService } from './bill.service';
import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from 'app/entities/owner/owner.service';

@Component({
  selector: 'jhi-bill-update',
  templateUrl: './bill-update.component.html'
})
export class BillUpdateComponent implements OnInit {
  isSaving = false;
  owners: IOwner[] = [];

  editForm = this.fb.group({
    id: [],
    number: [],
    owner: []
  });

  constructor(
    protected billService: BillService,
    protected ownerService: OwnerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bill }) => {
      this.updateForm(bill);

      this.ownerService
        .query({ 'billId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IOwner[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOwner[]) => {
          if (!bill.owner || !bill.owner.id) {
            this.owners = resBody;
          } else {
            this.ownerService
              .find(bill.owner.id)
              .pipe(
                map((subRes: HttpResponse<IOwner>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOwner[]) => (this.owners = concatRes));
          }
        });
    });
  }

  updateForm(bill: IBill): void {
    this.editForm.patchValue({
      id: bill.id,
      number: bill.number,
      owner: bill.owner
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bill = this.createFromForm();
    if (bill.id !== undefined) {
      this.subscribeToSaveResponse(this.billService.update(bill));
    } else {
      this.subscribeToSaveResponse(this.billService.create(bill));
    }
  }

  private createFromForm(): IBill {
    return {
      ...new Bill(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      owner: this.editForm.get(['owner'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBill>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IOwner): any {
    return item.id;
  }
}
