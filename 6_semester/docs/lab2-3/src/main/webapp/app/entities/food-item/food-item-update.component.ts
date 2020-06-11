import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IFoodItem, FoodItem } from 'app/shared/model/food-item.model';
import { FoodItemService } from './food-item.service';
import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from 'app/entities/owner/owner.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

type SelectableEntity = IOwner | ICustomer;

@Component({
  selector: 'jhi-food-item-update',
  templateUrl: './food-item-update.component.html'
})
export class FoodItemUpdateComponent implements OnInit {
  isSaving = false;
  owners: IOwner[] = [];
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    owner: [],
    customer: []
  });

  constructor(
    protected foodItemService: FoodItemService,
    protected ownerService: OwnerService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foodItem }) => {
      this.updateForm(foodItem);

      this.ownerService
        .query({ 'foodItemId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IOwner[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOwner[]) => {
          if (!foodItem.owner || !foodItem.owner.id) {
            this.owners = resBody;
          } else {
            this.ownerService
              .find(foodItem.owner.id)
              .pipe(
                map((subRes: HttpResponse<IOwner>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOwner[]) => (this.owners = concatRes));
          }
        });

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(foodItem: IFoodItem): void {
    this.editForm.patchValue({
      id: foodItem.id,
      name: foodItem.name,
      owner: foodItem.owner,
      customer: foodItem.customer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const foodItem = this.createFromForm();
    if (foodItem.id !== undefined) {
      this.subscribeToSaveResponse(this.foodItemService.update(foodItem));
    } else {
      this.subscribeToSaveResponse(this.foodItemService.create(foodItem));
    }
  }

  private createFromForm(): IFoodItem {
    return {
      ...new FoodItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      owner: this.editForm.get(['owner'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoodItem>>): void {
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
