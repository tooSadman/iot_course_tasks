import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IRoom, Room } from 'app/shared/model/room.model';
import { RoomService } from './room.service';
import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from 'app/entities/owner/owner.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IReceptionist } from 'app/shared/model/receptionist.model';
import { ReceptionistService } from 'app/entities/receptionist/receptionist.service';

type SelectableEntity = IOwner | ICustomer | IReceptionist;

@Component({
  selector: 'jhi-room-update',
  templateUrl: './room-update.component.html'
})
export class RoomUpdateComponent implements OnInit {
  isSaving = false;
  owners: IOwner[] = [];
  customers: ICustomer[] = [];
  receptionists: IReceptionist[] = [];

  editForm = this.fb.group({
    id: [],
    number: [],
    location: [],
    owner: [],
    customer: [],
    receptionist: []
  });

  constructor(
    protected roomService: RoomService,
    protected ownerService: OwnerService,
    protected customerService: CustomerService,
    protected receptionistService: ReceptionistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ room }) => {
      this.updateForm(room);

      this.ownerService
        .query({ 'roomId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IOwner[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOwner[]) => {
          if (!room.owner || !room.owner.id) {
            this.owners = resBody;
          } else {
            this.ownerService
              .find(room.owner.id)
              .pipe(
                map((subRes: HttpResponse<IOwner>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOwner[]) => (this.owners = concatRes));
          }
        });

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));

      this.receptionistService.query().subscribe((res: HttpResponse<IReceptionist[]>) => (this.receptionists = res.body || []));
    });
  }

  updateForm(room: IRoom): void {
    this.editForm.patchValue({
      id: room.id,
      number: room.number,
      location: room.location,
      owner: room.owner,
      customer: room.customer,
      receptionist: room.receptionist
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const room = this.createFromForm();
    if (room.id !== undefined) {
      this.subscribeToSaveResponse(this.roomService.update(room));
    } else {
      this.subscribeToSaveResponse(this.roomService.create(room));
    }
  }

  private createFromForm(): IRoom {
    return {
      ...new Room(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      location: this.editForm.get(['location'])!.value,
      owner: this.editForm.get(['owner'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      receptionist: this.editForm.get(['receptionist'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoom>>): void {
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
