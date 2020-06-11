import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFoodItem } from 'app/shared/model/food-item.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FoodItemService } from './food-item.service';
import { FoodItemDeleteDialogComponent } from './food-item-delete-dialog.component';

@Component({
  selector: 'jhi-food-item',
  templateUrl: './food-item.component.html'
})
export class FoodItemComponent implements OnInit, OnDestroy {
  foodItems: IFoodItem[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected foodItemService: FoodItemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.foodItems = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.foodItemService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFoodItem[]>) => this.paginateFoodItems(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.foodItems = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFoodItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFoodItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFoodItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('foodItemListModification', () => this.reset());
  }

  delete(foodItem: IFoodItem): void {
    const modalRef = this.modalService.open(FoodItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.foodItem = foodItem;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFoodItems(data: IFoodItem[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.foodItems.push(data[i]);
      }
    }
  }
}
