import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IManager, Manager } from 'app/shared/model/manager.model';
import { ManagerService } from './manager.service';
import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from 'app/entities/owner/owner.service';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory/inventory.service';

type SelectableEntity = IOwner | IInventory;

@Component({
  selector: 'jhi-manager-update',
  templateUrl: './manager-update.component.html'
})
export class ManagerUpdateComponent implements OnInit {
  isSaving = false;
  owners: IOwner[] = [];
  inventories: IInventory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    phone: [null, [Validators.pattern('^(\\+\\d{1,3}[- ]?)?\\d{10}$')]],
    owner: [],
    inventory: []
  });

  constructor(
    protected managerService: ManagerService,
    protected ownerService: OwnerService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ manager }) => {
      this.updateForm(manager);

      this.ownerService
        .query({ 'managerId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IOwner[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOwner[]) => {
          if (!manager.owner || !manager.owner.id) {
            this.owners = resBody;
          } else {
            this.ownerService
              .find(manager.owner.id)
              .pipe(
                map((subRes: HttpResponse<IOwner>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOwner[]) => (this.owners = concatRes));
          }
        });

      this.inventoryService.query().subscribe((res: HttpResponse<IInventory[]>) => (this.inventories = res.body || []));
    });
  }

  updateForm(manager: IManager): void {
    this.editForm.patchValue({
      id: manager.id,
      name: manager.name,
      phone: manager.phone,
      owner: manager.owner,
      inventory: manager.inventory
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const manager = this.createFromForm();
    if (manager.id !== undefined) {
      this.subscribeToSaveResponse(this.managerService.update(manager));
    } else {
      this.subscribeToSaveResponse(this.managerService.create(manager));
    }
  }

  private createFromForm(): IManager {
    return {
      ...new Manager(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      owner: this.editForm.get(['owner'])!.value,
      inventory: this.editForm.get(['inventory'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IManager>>): void {
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
