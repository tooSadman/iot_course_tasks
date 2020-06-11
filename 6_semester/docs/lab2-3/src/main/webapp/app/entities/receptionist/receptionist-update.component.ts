import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IReceptionist, Receptionist } from 'app/shared/model/receptionist.model';
import { ReceptionistService } from './receptionist.service';
import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from 'app/entities/owner/owner.service';
import { IBill } from 'app/shared/model/bill.model';
import { BillService } from 'app/entities/bill/bill.service';

type SelectableEntity = IOwner | IBill;

@Component({
  selector: 'jhi-receptionist-update',
  templateUrl: './receptionist-update.component.html'
})
export class ReceptionistUpdateComponent implements OnInit {
  isSaving = false;
  owners: IOwner[] = [];
  bills: IBill[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    phone: [null, [Validators.pattern('^(\\+\\d{1,3}[- ]?)?\\d{10}$')]],
    address: [null, [Validators.maxLength(100)]],
    owner: [],
    bill: []
  });

  constructor(
    protected receptionistService: ReceptionistService,
    protected ownerService: OwnerService,
    protected billService: BillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receptionist }) => {
      this.updateForm(receptionist);

      this.ownerService
        .query({ 'receptionistId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IOwner[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOwner[]) => {
          if (!receptionist.owner || !receptionist.owner.id) {
            this.owners = resBody;
          } else {
            this.ownerService
              .find(receptionist.owner.id)
              .pipe(
                map((subRes: HttpResponse<IOwner>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOwner[]) => (this.owners = concatRes));
          }
        });

      this.billService.query().subscribe((res: HttpResponse<IBill[]>) => (this.bills = res.body || []));
    });
  }

  updateForm(receptionist: IReceptionist): void {
    this.editForm.patchValue({
      id: receptionist.id,
      name: receptionist.name,
      phone: receptionist.phone,
      address: receptionist.address,
      owner: receptionist.owner,
      bill: receptionist.bill
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const receptionist = this.createFromForm();
    if (receptionist.id !== undefined) {
      this.subscribeToSaveResponse(this.receptionistService.update(receptionist));
    } else {
      this.subscribeToSaveResponse(this.receptionistService.create(receptionist));
    }
  }

  private createFromForm(): IReceptionist {
    return {
      ...new Receptionist(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      address: this.editForm.get(['address'])!.value,
      owner: this.editForm.get(['owner'])!.value,
      bill: this.editForm.get(['bill'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceptionist>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
