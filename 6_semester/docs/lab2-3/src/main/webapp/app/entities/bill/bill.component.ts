import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBill } from 'app/shared/model/bill.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BillService } from './bill.service';
import { BillDeleteDialogComponent } from './bill-delete-dialog.component';

@Component({
  selector: 'jhi-bill',
  templateUrl: './bill.component.html'
})
export class BillComponent implements OnInit, OnDestroy {
  bills: IBill[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected billService: BillService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.bills = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.billService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IBill[]>) => this.paginateBills(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.bills = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBills();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBill): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBills(): void {
    this.eventSubscriber = this.eventManager.subscribe('billListModification', () => this.reset());
  }

  delete(bill: IBill): void {
    const modalRef = this.modalService.open(BillDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bill = bill;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateBills(data: IBill[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.bills.push(data[i]);
      }
    }
  }
}
