import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IManager } from 'app/shared/model/manager.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ManagerService } from './manager.service';
import { ManagerDeleteDialogComponent } from './manager-delete-dialog.component';

@Component({
  selector: 'jhi-manager',
  templateUrl: './manager.component.html'
})
export class ManagerComponent implements OnInit, OnDestroy {
  managers: IManager[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected managerService: ManagerService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.managers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.managerService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IManager[]>) => this.paginateManagers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.managers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInManagers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IManager): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInManagers(): void {
    this.eventSubscriber = this.eventManager.subscribe('managerListModification', () => this.reset());
  }

  delete(manager: IManager): void {
    const modalRef = this.modalService.open(ManagerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.manager = manager;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateManagers(data: IManager[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.managers.push(data[i]);
      }
    }
  }
}
